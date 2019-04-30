package com.gmail.erofeev.st.alexei.fourthweek.repository.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.ItemRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.exception.DataBaseException;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Item;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.emuns.ItemStatusEnum;
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
public class ItemRepositoryImpl implements ItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Override
    public List<Item> getItems(Connection connection, int offset, int amount) {
        String sql = "SELECT * FROM items LIMIT ?,?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, amount);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(getItem(resultSet));
                }
                return items;
            }
        } catch (SQLException e) {
            String message = "Can't get items: " + e.getMessage();
            logger.error(message, e);
            throw new DataBaseException(message, e);
        }
    }

    @Override
    public Integer getAmount(Connection connection) {
        String sql = "SELECT COUNT(*) FROM items WHERE deleted = false";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Can't get amount of items: " + e.getMessage();
            logger.error(message, e);
            throw new DataBaseException(message, e);
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        ItemStatusEnum status = ItemStatusEnum.valueOf(resultSet.getString(3));
        return new Item(id, name, status);
    }
}
