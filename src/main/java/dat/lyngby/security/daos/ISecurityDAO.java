package dat.lyngby.security.daos;

import dk.bugelhartmann.UserDTO;
import dat.lyngby.security.entities.User;
import dat.lyngby.security.exceptions.ValidationException;

import java.util.List;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    //User createUserOG(String username, String password);
    User getUserByID(int id);
    List<User> getAll();
}
