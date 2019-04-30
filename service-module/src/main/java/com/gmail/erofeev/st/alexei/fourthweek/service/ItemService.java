package com.gmail.erofeev.st.alexei.fourthweek.service;

import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems(int page, int amount);

    Integer getAmountOfPages(int numberOfDisplayedItems);
}
