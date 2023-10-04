package com.example.chocolatefactory.domain.responseDTOs.user;

import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;

import java.util.List;

public class UserDetailsDTO {
    private Long id;
    private String email;
    private String fullName;
    private String city;
    private String address;
    private String phone;
    private List<OrderDTO> orders;

    public Long getId() {
        return id;
    }

    public UserDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDetailsDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDetailsDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDetailsDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDetailsDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public UserDetailsDTO setOrders(List<OrderDTO> orders) {
        this.orders = orders;
        return this;
    }
}
