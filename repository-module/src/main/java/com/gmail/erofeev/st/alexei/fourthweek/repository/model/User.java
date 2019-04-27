package com.gmail.erofeev.st.alexei.fourthweek.repository.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private Boolean isDeleted;

    public User() {
    }

    public User(Long id, String username, String password, Role role, Boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
