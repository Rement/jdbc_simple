package service.impl;

import model.User;
import repository.IUserRepository;
import service.IUserService;

import java.util.List;

public class UserService implements IUserService {

	private IUserRepository iUserRepository;

	public UserService(IUserRepository iUserRepository) {
		this.iUserRepository = iUserRepository;
	}

	@Override
	public List<User> findAllUsers() {
		return iUserRepository.getAllUsers();
	}

	@Override
	public User saveUser(User user) {
		return iUserRepository.createUser(user);
	}
}
