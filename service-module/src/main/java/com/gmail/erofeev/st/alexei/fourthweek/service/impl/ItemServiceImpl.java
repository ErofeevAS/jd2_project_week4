package com.gmail.erofeev.st.alexei.fourthweek.service.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.ItemRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Item;
import com.gmail.erofeev.st.alexei.fourthweek.service.ItemService;
import com.gmail.erofeev.st.alexei.fourthweek.service.connection.ConnectionService;
import com.gmail.erofeev.st.alexei.fourthweek.service.exception.ServiceException;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.ItemConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final ConnectionService connectionService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter, ConnectionService connectionService) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.connectionService = connectionService;
    }

    @Override
    public List<ItemDTO> getItems(int page, int amount) {
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int offset = (page - 1) * amount;
                List<Item> items = itemRepository.getItems(connection, offset, amount);
                List<ItemDTO> itemsDTO = itemConverter.toListDTO(items);
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                connection.rollback();
                String message = "Can't get items from repository: " + e.getMessage();
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } catch (SQLException e) {
            String message = "Can't establish connection to database: " + e.getMessage();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    public Integer getAmountOfPages(int amountOfDisplayedItems) {
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer amount = itemRepository.getAmount(connection);
                amount = (Math.round(amount / amountOfDisplayedItems) + 1);
                connection.commit();
                return amount;
            } catch (SQLException e) {
                connection.rollback();
                String message = "Can't get amount of items from repository: " + e.getMessage();
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } catch (SQLException e) {
            String message = "Can't establish connection to database: " + e.getMessage();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }
}
