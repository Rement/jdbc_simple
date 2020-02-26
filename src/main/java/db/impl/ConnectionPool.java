package db.impl;

import db.IConnectionPool;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static config.ApplicationConfiguration.INSTANCE;

@Slf4j
public class ConnectionPool implements IConnectionPool {

	public static final ConnectionPool CONNECTION_POOL_INSTANCE = new ConnectionPool();

	private List<Connection> availableConnections = new ArrayList<>(INSTANCE.getInitPoolSize());
	private List<Connection> takenConnections = new ArrayList<>();

	ConnectionPool() {
		initConnectionPool();
	}

	private void initConnectionPool() {
		createConnections(INSTANCE.getInitPoolSize());
		log.info("Connection pool has been created.");
		log.info("Connection pool capacity: {}", availableConnections.size());
	}

	@Override
	public Connection retrieveConnection() {
		log.info("Trying to retrieve connection");
		Connection connection = null;
		if (availableConnections.size() != 0) {
			connection = availableConnections.get(0);
		} else if (availableConnections.size() + takenConnections.size() < INSTANCE.getMaxPoolSize()) {
			createConnections(INSTANCE.getPoolIncreaseStep());
		}
		if (Objects.nonNull(connection)) {
			availableConnections.remove(0);
			takenConnections.add(connection);
		}
		return connection;
	}

	@Override
	public boolean releaseConnection(Connection connection) {
		log.info("Trying to release connection");
		try {
			takenConnections.remove(connection);
			availableConnections.add(connection);
		} catch (Exception e) {
			log.error("Sonthing went wrong with releasing connection", e);
			return false;
		}
		return true;
	}

	private void createConnections(final int connectionsCount) {
		for (int i = 0; i < connectionsCount; i++) {
			log.info("Creating new connection for connection pool #{}", i);
			try {
				Connection$Proxy connection$Proxy = new Connection$Proxy(DriverManager.getConnection(INSTANCE.getDbUrl(),
						INSTANCE.getDbUser(),
						INSTANCE.getDbPassword()));
				availableConnections.add(connection$Proxy);
			} catch (SQLException e) {
				log.error("Connection could not be created", e);
			}
		}
	}
}
