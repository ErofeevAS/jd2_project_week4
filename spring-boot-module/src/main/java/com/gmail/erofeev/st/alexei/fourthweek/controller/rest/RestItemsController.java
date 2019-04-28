package com.gmail.erofeev.st.alexei.fourthweek.controller.rest;


import com.gmail.erofeev.st.alexei.fourthweek.service.ItemService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@Validated
public class RestItemsController {
    private final ItemService itemService;

    @Autowired
    public RestItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<List<ItemDTO>> getItems(
            @RequestParam(required = false, defaultValue = "1") @Positive Integer offset,
            @RequestParam(required = false, defaultValue = "10") @Positive Integer amount
    ) {
        List<ItemDTO> items = itemService.getItems(offset, amount);
        return new ResponseEntity(items, HttpStatus.OK);
    }
}
