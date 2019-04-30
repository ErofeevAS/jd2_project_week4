package com.gmail.erofeev.st.alexei.fourthweek.controller;

import com.gmail.erofeev.st.alexei.fourthweek.controller.util.Paginator;
import com.gmail.erofeev.st.alexei.fourthweek.service.ItemService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Positive;
import java.util.List;

@Controller
@Validated
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(Model model,
                           @RequestParam(defaultValue = "1", required = false) @Positive int page,
                           @RequestParam(defaultValue = "10", required = false) @Positive int size) {
        Integer maxPage = itemService.getAmountOfPages(size);
        Paginator itemPaginator = new Paginator(page, maxPage, size);
        List<ItemDTO> items = itemService.getItems(page, size);
        model.addAttribute("items", items);
        model.addAttribute("paginator", itemPaginator);
        return "items";
    }
}
