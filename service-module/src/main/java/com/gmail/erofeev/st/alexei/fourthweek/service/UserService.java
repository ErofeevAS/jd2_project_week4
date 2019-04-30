package com.gmail.erofeev.st.alexei.fourthweek.service;

import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUserByName(String name);

    List<UserDTO> getUsers(int page, int amount);

    void add(UserDTO user);

    Integer getAmountOfPages(int amountOfDisplayedUsers);
}
