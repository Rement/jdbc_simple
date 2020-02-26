import model.User;
import repository.IUserRepository;
import repository.impl.UserRepository;
import service.IUserService;
import service.impl.UserService;

public class Main {

	public static void main(final String[] args) {
		IUserRepository iUserRepository = new UserRepository();
		IUserService userService = new UserService(iUserRepository);
		System.out.println(userService.findAllUsers());
		System.out.println("_________");
		User user = new User(1, "LOGIN", "PWD", "Name", (short)22, 37555555);
		userService.saveUser(user);
		System.out.println("__________");
		System.out.println(userService.findAllUsers());
	}
}
