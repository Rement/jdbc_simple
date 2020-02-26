package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

	private int id;
	private String login;
	private String password;
	private String name;
	private short age;
	private int phoneNumber;
}
