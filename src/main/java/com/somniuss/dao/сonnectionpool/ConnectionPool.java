package com.somniuss.dao.сonnectionpool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool implements AutoCloseable {

	private final BlockingQueue<PooledConnection> availableConnections;
	private final BlockingQueue<PooledConnection> usedConnections;

	// Конструктор для инициализации пула с размером poolSize
	public ConnectionPool(int poolSize, String url, String user, String password) throws ConnectionPoolException {
		availableConnections = new ArrayBlockingQueue<>(poolSize);
		usedConnections = new ArrayBlockingQueue<>(poolSize);

		try {
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection);
				availableConnections.offer(pooledConnection);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("Pool initialization error", e);
		}
	}

	// Метод для получения соединения из пула
	public PooledConnection takeConnection() throws ConnectionPoolException {
		try {
			PooledConnection connection = availableConnections.take(); // Ожидание соединения
			usedConnections.offer(connection); // Добавляем соединение в список использованных
			return connection; // Возвращаем соединение
		} catch (InterruptedException e) { // потому что take
			throw new ConnectionPoolException("No available connection at the moment", e);
		}
	}

	// Метод для возврата соединения в пул
	public void closeConnection(PooledConnection connection) throws ConnectionPoolException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection is not returned to the pool", e);
		}
	}

	@Override
	public void close() throws ConnectionPoolException {
		try {
			ArrayList<PooledConnection> allConnections = new ArrayList<>();
			PooledConnection connection;

			// Перемещаем соединения из usedConnections в временный список
			while ((connection = usedConnections.poll()) != null) {
				allConnections.add(connection);
			}

			// Перемещаем соединения из availableConnections в временный список
			while ((connection = availableConnections.poll()) != null) {
				allConnections.add(connection);
			}

			// Закрываем соединения, если их количество превышает 10
			int totalConnections = allConnections.size();
			for (int i = 0; i < totalConnections; i++) {
				PooledConnection conn = allConnections.get(i);

				if (!conn.getInnerConnection().getAutoCommit()) {
					conn.getInnerConnection().commit();
				}

				if (i < 10) {
					// Возвращаем первые 10 соединений обратно в пул
					if (!availableConnections.offer(conn)) {
						throw new SQLException("Error reinserting connection into pool");
					}
				} else {
					// Закрываем остальные соединения
					conn.getInnerConnection().close();
				}
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("Error closing connections", e);
		}
	}

	// Вложенный класс для обертки соединений
	class PooledConnection implements Connection {

		private final Connection innerConnection;

		public PooledConnection(Connection connection) throws SQLException {
			this.innerConnection = connection;
			this.innerConnection.setAutoCommit(true);
		}

		public Connection getInnerConnection() {
			return innerConnection;
		}

		@Override
		public void close() throws SQLException {
			if (innerConnection.isClosed()) {
				throw new SQLException("Attempting to close an already closed connection");
			}

			if (!usedConnections.remove(this)) {
				throw new SQLException("Error removing connection from used pool");
			}

			if (!availableConnections.offer(this)) {
				throw new SQLException("Error returning connection to the pool");
			}
		}

		@Override
		public void clearWarnings() throws SQLException {
			innerConnection.clearWarnings();
		}

		@Override
		public void commit() throws SQLException {
			innerConnection.commit();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return innerConnection.createStatement();
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return innerConnection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return innerConnection.getCatalog();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return innerConnection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return innerConnection.getTransactionIsolation();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return innerConnection.isClosed();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return innerConnection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return innerConnection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return innerConnection.prepareCall(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return innerConnection.prepareStatement(sql);
		}

		@Override
		public void rollback() throws SQLException {
			innerConnection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			innerConnection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			innerConnection.setCatalog(catalog);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return innerConnection.setSavepoint();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			innerConnection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return innerConnection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return innerConnection.unwrap(iface);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			innerConnection.abort(executor);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return innerConnection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return innerConnection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			innerConnection.releaseSavepoint(savepoint);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			innerConnection.rollback(savepoint);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			innerConnection.setClientInfo(properties);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			innerConnection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			innerConnection.setSchema(schema);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			innerConnection.setTypeMap(map);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isReadOnly() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			// TODO Auto-generated method stub

		}

		@Override
		public int getHoldability() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Clob createClob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Blob createBlob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NClob createNClob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			// TODO Auto-generated method stub

		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		// Остальные методы Connection могут быть добавлены аналогично
	}
}
