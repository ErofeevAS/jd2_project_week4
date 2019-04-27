package com.gmail.erofeev.st.alexei.fourthweek.repository.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.UserRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.exception.DataBaseException;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User findUserByName(Connection connection, String username) {
        String sql =
                "SELECT users.id as user_id, username,password,deleted, roles.id as role_id,roles.name as role_name " +
                        "FROM users " +
                        "JOIN roles ON users.role_id = roles.id   where users.username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Can't get user with name: {} ", username, e);
            throw new DataBaseException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll(Connection connection, int offset, int amount) {
        String sql =
                "SELECT users.id as user_id, username,password,deleted, roles.id as role_id,roles.name as role_name " +
                        "FROM users " +
                        "JOIN roles ON users.role_id = roles.id LIMIT ?,?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, amount);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(getUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            String message = "Can't get users: " + e.getMessage();
            logger.error(message, e);
            throw new DataBaseException(message, e);
        }
    }

    @Override
    public Integer getAmount(Connection connection) {
        String sql = "SELECT COUNT(*) FROM users WHERE deleted = false";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Can't get amount of users: " + e.getMessage();
            logger.error(message, e);
            throw new DataBaseException(message, e);
        }
    }

    @Override
    public User add(Connection connection, User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Long roleId = user.getRole().getId();
        String sql = "INSERT INTO users (username,password,role_id) values(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setLong(3, roleId);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Database exception during saving a user with username: {} ." + e.getMessage(), username, e);
            throw new DataBaseException("Database exception during saving a user with username: " + username, e);
        }
        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong(1);
        String username = resultSet.getString(2);
        String password = resultSet.getString(3);
        Boolean isDeleted = resultSet.getBoolean(4);
        Long roleId = resultSet.getLong(5);
        String roleName = resultSet.getString(6);
        Role role = new Role(roleId, roleName);
        User user = new User(userId, username, password, role, isDeleted);
        return user;
    }
}
