package com.gmail.erofeev.st.alexei.fourthweek.service.model;

import com.gmail.erofeev.st.alexei.fourthweek.service.model.enums.ItemDTOStatusEnum;

public class ItemDTO {
    private Long id;
    private String name;
    private ItemDTOStatusEnum status;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, ItemDTOStatusEnum status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemDTOStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemDTOStatusEnum status) {
        this.status = status;
    }
}
