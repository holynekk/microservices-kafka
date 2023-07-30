package com.holynekk.kafka.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    private int orderId;

    @Column
    private String orderNumber;

    @Column
    private String orderLocation;

    @Column
    private LocalDate orderDateTime;

    @Column
    private String creditCardNumber;

    @OneToMany
    private List<OrderItem> items;

    public Order(String orderNumber, String orderLocation, LocalDate orderDateTime, String creditCardNumber, List<OrderItem> items) {
        this.orderNumber = orderNumber;
        this.orderLocation = orderLocation;
        this.orderDateTime = orderDateTime;
        this.creditCardNumber = creditCardNumber;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public LocalDate getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDate orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", items=" + items +
                '}';
    }
}
