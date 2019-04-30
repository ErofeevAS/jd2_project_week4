package com.gmail.erofeev.st.alexei.fourthweek.service.converter.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.User;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.RoleConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.RoleDTO;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public UserDTO toDTO(User user) {
        Long id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        Role role = user.getRole();
        RoleDTO roleDTO = roleConverter.toRoleDTO(role);
        Boolean isDeleted = user.getDeleted();
        return new UserDTO(id, username, password, roleDTO, isDeleted);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public User fromDTO(UserDTO user) {
        Long id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        RoleDTO roleDTO = user.getRole();
        Role role = roleConverter.fromRoleDTO(roleDTO);
        Boolean isDeleted = user.getDeleted();
        return new User(id, username, password, role, isDeleted);
    }

    @Override
    public List<UserDTO> toListDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(toDTO(user));
        }
        return usersDTO;
    }

}
