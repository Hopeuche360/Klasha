package com.klasha.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Location extends BaseClass{
    private String name;
    private long x_coordinate;
    private long y_coordinate;
}
