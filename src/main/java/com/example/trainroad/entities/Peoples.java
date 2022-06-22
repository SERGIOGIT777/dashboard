package com.example.trainroad.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "peoples")
public class Peoples {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(min=2, message = "Не меньше 2 знаков")
    private String login;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(min=2, message = "Не меньше 2 знаков")
    private String password;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(min=2, message = "Не меньше 2 знаков")
    @Transient
    private String confirm_password;

    private String authority;

    @NotEmpty(message = "Поле должно быть заполнено")
    private String name;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Email
    private String email;

    @NotNull(message = "Поле должно быть заполнено")
    @Max(value = 100, message = "Не более 100")
    private int age;

    public Long getId() {
        return id;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "confirm_password")
    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
