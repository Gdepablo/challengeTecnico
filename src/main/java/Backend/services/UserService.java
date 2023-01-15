package Backend.services;

import Backend.component.NoteDTO;
import Backend.component.UserDTO;
import Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void save (UserDTO user);
    void delete (UserDTO user);
    void deleteById(int id);
    void update (int id,UserDTO user);
    void addNote(int id,NoteDTO note);
    void removeNote(int id,int noteId);
    UserDTO getUserById(int id);


}
