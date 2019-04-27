package com.gmail.erofeev.st.alexei.fourthweek.controller;

import com.gmail.erofeev.st.alexei.fourthweek.service.UserService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import com.gmail.erofeev.st.alexei.fourthweek.controller.util.Paginator;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(defaultValue = "1", required = false) @Positive int page,
                           @RequestParam(defaultValue = "10", required = false) @Positive int size) {
        Integer maxPage = userService.getAmountOfPages(size);
        Paginator paginator = new Paginator(page, maxPage, size);
        List<UserDTO> users = userService.getUsers(page, size);
        model.addAttribute("users", users);
        model.addAttribute("paginator", paginator);
        return "users";
    }
}
