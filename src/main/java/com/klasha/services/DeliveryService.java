package com.klasha.services;

import com.klasha.dtos.LocationDto;
import com.klasha.models.Delivery;
import com.klasha.responses.DeliveryResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DeliveryService {
    String addDeliveryLocation(String address, HttpSession httpSession);
    String updateDeliveryLocation(long deliveryId, String address, HttpSession httpSession);
    String removeLocation(long deliveryId, HttpSession httpSession);
    List<Delivery> viewLocations();
    DeliveryResponse optimalRouteAndDeliveryCost(LocationDto locationDto);
}
