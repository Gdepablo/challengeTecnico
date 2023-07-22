package Backend.services;

import Backend.Configuration.BCryptHelper;
import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.Security.UserPrincipal;
import Backend.component.*;
import Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class UserServiceImpl implements UserService , UserDetailsService { //El UserDetailService es una clase de
    // Spring Security, necesaria para el metodo loadUserByUsername que basicamente te 'trae' el usuario y lo usa
    //para hacer cosas de autenticacion.

    private final UserRepository userRepository;
    private final NoteServiceImpl noteService;
    private final BCryptPasswordEncoder passwordEncoder = BCryptHelper.passwordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,NoteServiceImpl noteService) {
        this.userRepository = userRepository;
        this.noteService = noteService;
    }

    @Override
    public void save(UserDTO user) {
        Client aClient = this.convertToUser(user);
        aClient.setPassword(passwordEncoder.encode(aClient.getPassword())); //hashea la contrasenia.
        this.userRepository.save(aClient);
    }

    @Override
    public void deleteUser (UserDTO user) {
        Client aClient = this.convertToUser(user);
        this.userRepository.delete(aClient);
    }

    @Override
    public void deleteById(int id) {
        Optional<Client> aUser = userRepository.findById(id);

        if (aUser.isPresent()) {
            userRepository.delete(aUser.get());
            return;
        }
        throw new NotFoundException("User not found");
    } //OK.

    @Override
    public void updateUser (int id,UserDTO user) {
        Optional<Client> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
        Client aClient = this.convertToUser(user);
        Client persistedClient = optionalUser.get();
        persistedClient.setUsername(aClient.getUsername());
        persistedClient.setAllNotes(aClient.getNotes());
        this.userRepository.save(persistedClient);
        return;}
        throw new NotFoundException("User not found"); //OK.

    }
    @Override
    public void addUser(int id, NoteDTO note) { //Asocia una nota nueva al usuario.
        Optional<Client> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Client client = optionalUser.get();
            noteService.save(note);
            Note aNote = MHelpers.modelMapper().map(note,Note.class);
            client.getNotes().add(aNote); //Primero persisto la nota y luego al final persisto el usuario.
            this.userRepository.save(optionalUser.get());
            return;}
        throw new NotFoundException("User not found");
    }
    @Override
    public void removeUser(int id,int noteId) {
        Optional<Client> optionalUser = userRepository.findById(id);
        NoteDTO note = noteService.getNotesById(noteId);
        if (optionalUser.isPresent()) {
            Client client = optionalUser.get();
            Note aNote = MHelpers.modelMapper().map(note,Note.class);
            client.getNotes().add(aNote);
            this.userRepository.delete(optionalUser.get());
            return;}
        throw new NotFoundException("User not found");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(int id) {
        Optional<Client> users = userRepository.findById(id);
        Optional<UserDTO> user = users.map(this::convertToUserDTO); //Mapeo para convertir de Notes a NotesDTO

        if(user.isPresent()) {
            return user.get();
        } else {throw new NotFoundException("User not found");}
    }

    public UserDTO getUserByUsername(String username) {
        Client client = userRepository.findByUsername(username);
        System.out.println("El cliente es" + client + "Y el username que llego " + username);

        if(client == null) {
            throw new NotFoundException("User not found");
        }
        return convertToUserDTO(client);
    }
    public Client convertToUser(UserDTO user) {
        return MHelpers.modelMapper().map(user, Client.class);
    }

    public UserDTO convertToUserDTO(Client client) {
        return MHelpers.modelMapper().map(client, UserDTO.class);
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
            Iterable<Client> users = userRepository.findAll();
            List<Client> iterableToList = new ArrayList<>();
            users.forEach(iterableToList::add);
            return iterableToList.stream().map(this::convertToUserDTO).toList();

    }
    @Override
    public void addNoteToUser(int id, NoteDTO aNote) { //Logicamente hablando, lo que mas tiene sentido es guardar y borrar de
        //uno en uno.
        UserDTO user = this.getUserById(id);
        Client anClient = this.convertToUser(user);
        Note note = MHelpers.modelMapper().map(aNote,Note.class);
        anClient.getNotes().add(note);
        save(convertToUserDTO(anClient));
    }

    @Override
    public void deleteNoteFromUser(int id, int noteId) {
        UserDTO user = this.getUserById(id);
        Client anClient = this.convertToUser(user);
        List<Note> notes = user.getNotes();  //Lista de notas.
        Stream<Note> filteredNote = notes.stream().filter(note -> note.getId() == noteId); //Consigo la nota pa borrar.
        Note noteToRemove = filteredNote.toList().get(0); //Siempre es unico porque el id es unico entonces aca
        // consigo la nota para borrar. el ToList es pa limpiarme el stream y el get pa obtener la nota.
        notes.remove(noteToRemove);
        this.save(convertToUserDTO(anClient));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserDTO user = this.getUserByUsername(username);
        Client anClient = convertToUser(user);
        return  MHelpers.modelMapper().map(anClient, UserPrincipal.class);
        }
    }

