package com.klasha.services;

import com.klasha.dtos.DeliveryDto;
import com.klasha.models.Delivery;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DeliveryService {
    String addLocation(DeliveryDto deliveryDto, HttpSession httpSession);
    String updateLocation(long deliveryId, DeliveryDto deliveryDto, HttpSession httpSession);
    String removeLocation(long deliveryId, HttpSession httpSession);
    List<Delivery> viewLocations();
}
