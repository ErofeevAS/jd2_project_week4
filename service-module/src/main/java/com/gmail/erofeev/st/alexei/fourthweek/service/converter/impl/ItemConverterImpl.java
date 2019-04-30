package com.gmail.erofeev.st.alexei.fourthweek.service.converter.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Item;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.emuns.ItemStatusEnum;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.ItemConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.enums.ItemDTOStatusEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemConverterImpl implements ItemConverter {

    @Override
    public ItemDTO toDTO(Item item) {
        Long id = item.getId();
        String name = item.getName();
        ItemDTOStatusEnum status = toItemDTOStatus(item.getStatus());
        return new ItemDTO(id, name, status);
    }

    @Override
    public Item fromDTO(ItemDTO item) {
        Long id = item.getId();
        String name = item.getName();
        ItemStatusEnum status = toItemStatus(item.getStatus());
        return new Item(id, name, status);
    }

    @Override
    public List<ItemDTO> toListDTO(List<Item> items) {
        List<ItemDTO> itemsDTO = new ArrayList<>();
        for (Item item : items) {
            itemsDTO.add(toDTO(item));
        }
        return itemsDTO;
    }

    private ItemDTOStatusEnum toItemDTOStatus(ItemStatusEnum status) {
        return ItemDTOStatusEnum.valueOf(status.name());
    }

    private ItemStatusEnum toItemStatus(ItemDTOStatusEnum statusDTO) {
        return ItemStatusEnum.valueOf(statusDTO.name());
    }
}
