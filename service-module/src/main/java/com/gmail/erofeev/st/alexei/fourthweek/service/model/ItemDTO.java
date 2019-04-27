package com.gmail.erofeev.st.alexei.fourthweek.service.model;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.emuns.ItemStatusEnum;

public class ItemDTO {
    private Long id;
    private String name;
    private ItemStatusEnum status;

    public ItemDTO(Long id, String name, ItemStatusEnum status) {
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

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }
}
