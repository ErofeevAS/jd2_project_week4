package com.gmail.erofeev.st.alexei.fourthweek.service.converter;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Item;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;

import java.util.List;

public interface ItemConverter {
    ItemDTO toDTO(Item item);

    Item fromDTO(ItemDTO item);

    List<ItemDTO> toListDTO(List<Item> items);
}
