package Backend.services;

import Backend.component.NoteDTO;
import Backend.component.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void save (UserDTO user);
    void deleteUser (UserDTO user);
    void deleteById(int id);
    void updateUser (int id,UserDTO user);
    void addUser(int id,NoteDTO note);
    void removeUser(int id,int noteId);
    UserDTO getUserById(int id);
    void addNoteToUser(int id, NoteDTO aNote);
    void deleteNoteFromUser(int id, int noteId);
    List<UserDTO> findAll();

}
