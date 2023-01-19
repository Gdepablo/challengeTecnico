package Backend.services;

import Backend.Configuration.BCryptHelper;
import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.component.*;
import Backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        User aUser = this.convertToUser(user);
        aUser.setPassword(passwordEncoder.encode(aUser.getPassword())); //hashea la contrasenia.
        this.userRepository.save(aUser);
    }

    @Override
    public void deleteUser (UserDTO user) {
        User aUser = this.convertToUser(user);
        this.userRepository.delete(aUser);
    }

    @Override
    public void deleteById(int id) {
        Optional<User> aUser = userRepository.findById(id);

        if (aUser.isPresent()) {
            userRepository.delete(aUser.get());
            return;
        }
        throw new NotFoundException("User not found");
    } //OK.

    @Override
    public void updateUser (int id,UserDTO user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
        User aUser = this.convertToUser(user);
        User persistedUser = optionalUser.get();
        persistedUser.setUsername(aUser.getUsername());
        persistedUser.setAllNotes(aUser.getNotes());
        this.userRepository.save(persistedUser);
        return;}
        throw new NotFoundException("User not found"); //OK.

    }
    @Override
    public void addUser(int id, NoteDTO note) { //Asocia una nota nueva al usuario. TODO: Revisar. no me convence.
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            noteService.save(note);
            Note aNote = MHelpers.modelMapper().map(note,Note.class);
            user.getNotes().add(aNote); //Primero persisto la nota y luego al final persisto el usuario.
            this.userRepository.save(optionalUser.get());
            return;}
        throw new NotFoundException("User not found");
    }
    @Override
    public void removeUser(int id,int noteId) {
        Optional<User> optionalUser = userRepository.findById(id);
        NoteDTO note = noteService.getNotesById(noteId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Note aNote = MHelpers.modelMapper().map(note,Note.class);
            user.getNotes().add(aNote);
            this.userRepository.delete(optionalUser.get());
            return;}
        throw new NotFoundException("User not found");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(int id) {
        Optional<User> users = userRepository.findById(id);
        Optional<UserDTO> user = users.map(this::convertToUserDTO); //Mapeo para convertir de Notes a NotesDTO

        if(user.isPresent()) {
            return user.get();
        } else {throw new NotFoundException("User not found");}
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new NotFoundException("User not found");
        }
        return convertToUserDTO(user);
    }
    public User convertToUser(UserDTO user) {
        return MHelpers.modelMapper().map(user, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return MHelpers.modelMapper().map(user, UserDTO.class);
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
            Iterable<User> users = userRepository.findAll();
            List<User> iterableToList = new ArrayList<>();
            users.forEach(iterableToList::add);
            return iterableToList.stream().map(this::convertToUserDTO).toList();

    }
    @Override
    public void addNoteToUser(int id, NoteDTO aNote) { //Logicamente hablando, lo que mas tiene sentido es guardar y borrar de
        //uno en uno.
        UserDTO user = this.getUserById(id);
        User anUser = this.convertToUser(user);
        Note note = MHelpers.modelMapper().map(aNote,Note.class);
        anUser.getNotes().add(note);
        save(convertToUserDTO(anUser));
    }

    @Override
    public void deleteNoteFromUser(int id, int noteId) {
        UserDTO user = this.getUserById(id);
        User anUser = this.convertToUser(user);
        List<Note> notes = user.getNotes();  //Lista de notas.
        Stream<Note> filteredNote = notes.stream().filter(note -> note.getId() == noteId); //Consigo la nota pa borrar.
        Note noteToRemove = filteredNote.toList().get(0); //Siempre es unico porque el id es unico entonces aca
        // consigo la nota para borrar. el ToList es pa limpiarme el stream y el get pa obtener la nota.
        notes.remove(noteToRemove);
        this.save(convertToUserDTO(anUser));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //El problema evidentemente esta aca
       UserDTO user = this.getUserByUsername(username);
        User anUser = convertToUser(user);
        MHelpers.modelMapper().validate();
        if(this.checkMapping(anUser, user)) {
            UserPrincipal anotherUser = MHelpers.modelMapper().map(anUser,UserPrincipal.class);
            if(this.checkMapping(anUser, anotherUser)) {
                return  MHelpers.modelMapper().map(anUser,UserPrincipal.class);
            } else throw new UnsupportedOperationException("Incorrect mapping from UserDTO to User");
        }
        throw new UnsupportedOperationException("Incorrect mapping");
    }

         //Estos dos son validadores custom para chequear que el mapeo esté bien hecho.
        //Ahora que la app funciona los puedo sacar, pero los dejo acá por las dudas los precise en el futuro.
        //Habian mas como el de username etc etc pero me interesa únicamente el de la contrasenia.
        public boolean checkMapping(User user1, UserDTO user2) {
            return user1.getPassword().equals(user2.getPassword());
        }

    public boolean checkMapping(User user1, UserPrincipal user2) {
        return user1.getPassword().equals(user2.getPassword());
    }
    }

