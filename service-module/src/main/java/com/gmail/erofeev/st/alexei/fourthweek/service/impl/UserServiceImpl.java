package com.gmail.erofeev.st.alexei.fourthweek.service.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.RoleRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.UserRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.User;
import com.gmail.erofeev.st.alexei.fourthweek.service.UserService;
import com.gmail.erofeev.st.alexei.fourthweek.service.connection.ConnectionService;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.exception.ServiceException;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConnectionService connectionService;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ConnectionService connectionService, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.connectionService = connectionService;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findUserByName(String name) {
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.findUserByName(connection, name);
                UserDTO userDTO = userConverter.toDTO(user);
                connection.commit();
                logger.info("user was found: " + user);
                return userDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Database error, changes were rollbacked: " + e.getMessage(), e);
                throw new ServiceException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error("Can't establish connection to database:" + e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserDTO> getUsers(int page, int amount) {
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int offset = (page - 1) * amount;
                List<User> users = userRepository.findAll(connection, offset, amount);
                List<UserDTO> usersDTO = userConverter.toListDTO(users);
                connection.commit();
                return usersDTO;
            } catch (SQLException e) {
                connection.rollback();
                String message = "Can't get users from repository: " + e.getMessage();
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } catch (SQLException e) {
            String message = "Can't establish connection to database: " + e.getMessage();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    public Integer getAmountOfPages(int amountOfDisplayedUsers) {
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer amount = userRepository.getAmount(connection);
                amount = (Math.round(amount / amountOfDisplayedUsers) + 1);
                connection.commit();
                return amount;
            } catch (SQLException e) {
                connection.rollback();
                String message = "Can't get amount of users from repository: " + e.getMessage();
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } catch (SQLException e) {
            String message = "Can't establish connection to database: " + e.getMessage();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                String roleName = userDTO.getRole().getName();
                Role role = roleRepository.findByName(connection, roleName);
                User user = userConverter.fromDTO(userDTO);
                user.setRole(role);
                userRepository.add(connection, user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Can't add user with id: {}", userDTO.getId());
                throw new ServiceException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error("Can't get connection to database:", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
