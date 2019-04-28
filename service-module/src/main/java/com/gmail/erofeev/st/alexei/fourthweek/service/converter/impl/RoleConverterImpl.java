package com.gmail.erofeev.st.alexei.fourthweek.service.converter.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.service.converter.RoleConverter;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {
    private final String ROLE_PREFIX = "ROLE_";

    public RoleDTO toRoleDTO(Role role) {
        Long roleId = role.getId();
        String roleName = ROLE_PREFIX + role.getName();
        return new RoleDTO(roleId, roleName);
    }

    public Role fromRoleDTO(RoleDTO role) {
        Long roleId = role.getId();
        String roleName = role.getName().split(ROLE_PREFIX)[1];
        return new Role(roleId, roleName);
    }
}
