package com.gmail.erofeev.st.alexei.fourthweek.service.converter;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.User;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;

import java.util.List;

public interface UserConverter {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO user);

    List<UserDTO> toListDTO(List<User> users);
}

