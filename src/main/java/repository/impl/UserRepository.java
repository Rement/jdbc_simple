package repository.impl;

import db.impl.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import model.User;
import repository.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepository implements IUserRepository {

	@Override
	public List<User> getAllUsers() {
		List<User> resultCollection = new ArrayList<>();
		final Connection connection = ConnectionPool.CONNECTION_POOL_INSTANCE.retrieveConnection();
		if (Objects.isNull(connection)) {
			throw new RuntimeException("No connection");
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user")) {
			final ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet == null) {
				return resultCollection;
			}
			while (resultSet.next()) {
				User receivedUser = new User(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						(short) resultSet.getInt(5),
						resultSet.getInt(6));
				resultCollection.add(receivedUser);
			}
		} catch (SQLException e) {
			log.error("", e);
		}
		return resultCollection;
	}

	@Override
	public Optional<User> findUserById(int id) {
		return Optional.empty();
	}

	@Override
	public int deleteUser(int id) {
		return 0;
	}

	@Override
	public User createUser(User user) {
		final Connection connection = ConnectionPool.CONNECTION_POOL_INSTANCE.retrieveConnection();
		if (Objects.isNull(connection)) {
			throw new RuntimeException("No connection");
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)")) {
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getLogin());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setInt(5, user.getAge());
			preparedStatement.setInt(6, user.getPhoneNumber());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {

		}
		return user;
	}
}
