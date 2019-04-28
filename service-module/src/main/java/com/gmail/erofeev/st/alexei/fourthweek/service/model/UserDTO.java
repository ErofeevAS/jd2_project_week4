package com.gmail.erofeev.st.alexei.fourthweek.service.model;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private RoleDTO role;
    private Boolean isDeleted;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, RoleDTO role, Boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public UserDTO(String username, String password, RoleDTO role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
