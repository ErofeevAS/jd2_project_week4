package com.gmail.erofeev.st.alexei.fourthweek.controller.rest;

import com.gmail.erofeev.st.alexei.fourthweek.service.UserService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class RestApiUserController {
    private final UserService userService;

    @Autowired
    public RestApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers(
            @RequestParam(required = false, defaultValue = "1") @Positive Integer offset,
            @RequestParam(required = false, defaultValue = "10") @Positive Integer amount
    ) {
        return userService.getUsers(offset, amount);
    }

    @PostMapping
    public void addUser(
            @RequestBody UserDTO userDTO
    ) {
        userService.add(userDTO);
    }
}
