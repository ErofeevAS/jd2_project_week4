package com.gmail.erofeev.st.alexei.fourthweek.repository;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    List<Item> getItems(Connection connection, int offset, int amount);

    Integer getAmount(Connection connection);
}

