package service;

import model.User;

import java.util.List;

public interface IUserService {

	List<User> findAllUsers();

	User saveUser(User user);
}
