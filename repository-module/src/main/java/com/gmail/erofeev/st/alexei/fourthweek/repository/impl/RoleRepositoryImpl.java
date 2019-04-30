package com.gmail.erofeev.st.alexei.fourthweek.repository.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.RoleRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.exception.DataBaseException;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Order
    public Role findByName(Connection connection, String name) {
        String query = "SELECT * FROM roles WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return getRole(rs);
            }
        } catch (SQLException e) {
            String message = "Can't find role by name:" + name + " " + e.getMessage();
            logger.error(message, e);
            throw new DataBaseException(message, e);
        }
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        return new Role(id, name);
    }
}
