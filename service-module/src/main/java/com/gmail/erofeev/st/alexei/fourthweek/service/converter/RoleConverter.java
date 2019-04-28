package com.gmail.erofeev.st.alexei.fourthweek.service.converter;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.RoleDTO;

public interface RoleConverter {
    RoleDTO toRoleDTO(Role role);

    Role fromRoleDTO(RoleDTO role);
}
