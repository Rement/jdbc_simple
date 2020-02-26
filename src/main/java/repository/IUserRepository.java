package repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

	List<User> getAllUsers();

	Optional<User> findUserById(int id);

	int deleteUser(int id);

	User createUser(User user);
}
