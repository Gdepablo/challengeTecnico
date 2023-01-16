package Backend.services;

import Backend.Configuration.BCryptHelper;
import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.component.*;
import Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService, UserDetailsService { //El USerDetailService es una clase de
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
    } //Crear usuarios y eso se re va del scope del enunciado. A lo mejor ni lo hago jaja.

    @Override
    public void delete (UserDTO user) {
        User aUser = this.convertToUser(user);
        this.userRepository.delete(aUser);
    }
    @Override
    public void deleteById(int id) {
        Optional<User> aUser = userRepository.findById(id);

        if (aUser.isPresent()) {
            userRepository.save(aUser.get());
            return;
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public void update (int id,UserDTO user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
        User aUser = this.convertToUser(user);
        User persistedUser = optionalUser.get();
        persistedUser.setUsername(aUser.getUsername());
        persistedUser.setPassword(persistedUser.getPassword());
        persistedUser.setPassword(passwordEncoder.encode(aUser.getPassword())); //La idea es actualizar
            // la contraseña del vago y ahi hashear la nueva. En este caso no concateno porque no me parece necesario o que sea lo correcto.
            persistedUser.setAllNotes(aUser.getNotes());
        this.userRepository.save(aUser);
        return;}
        throw new NotFoundException("User not found");

    }
    @Override
    public void addNote(int id, NoteDTO note) { //Asocia una nota nueva al usuario. TODO: Revisar. no me convence.
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
    public void removeNote(int id,int noteId) {
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
        return convertToUserDTO(user);
    }
    public User convertToUser(UserDTO user) {
        return MHelpers.modelMapper().map(user, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return MHelpers.modelMapper().map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = this.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("There is no user associated with the username" + username);
        }
        return MHelpers.modelMapper().map(user,UserPrincipal.class);
    }
    }

    //TODO: Controller de esta garcha, capaz. Aunque se va del scope.

