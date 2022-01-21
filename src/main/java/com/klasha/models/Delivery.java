package com.klasha.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Delivery extends BaseClass{
    private String address;
    @ManyToOne
    private User user;
}
