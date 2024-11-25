package com.codeWithGanesh.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String phone;
    private String email;
    private Double amount;
    private String orderStatus;
    private String razorpayOrderId;
}
