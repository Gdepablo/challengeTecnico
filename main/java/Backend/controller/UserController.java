package Backend.controller;

import Backend.Exceptions.RequestBodyIsNullException;
import Backend.component.NoteDTO;
import Backend.component.UserDTO;
import Backend.services.UserServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Data
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service) {
        this.service= service;
    }

    @PostMapping("/new")
    public ResponseEntity<UserDTO> createUser(@RequestBody(required = false) UserDTO user) {
        if (user == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }
        service.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } //OK.

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } //OK.

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody(required = false) UserDTO aUser) {
        if (aUser == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }

        service.updateUser(id,aUser);
        return new ResponseEntity<>(aUser, HttpStatus.OK);} //OK.

    @PutMapping("{id}/notes/add")
    public ResponseEntity<UserDTO> addNotesToUser(@PathVariable int id, @RequestBody NoteDTO noteDTO){
        if (noteDTO == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }
        service.addNoteToUser(id,noteDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping ("{id}/notes/remove/{noteId}")
    public ResponseEntity<UserDTO> removeNotesFromUser(@PathVariable int id, @PathVariable int noteId){
        service.deleteNoteFromUser(id,noteId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.FOUND);
    }



}