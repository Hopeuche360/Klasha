package com.klasha.services;

import com.klasha.dtos.DeliveryDto;
import com.klasha.dtos.LocationDto;
import com.klasha.models.Delivery;
import com.klasha.models.Location;
import com.klasha.responses.DeliveryResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DeliveryService {
    String addLocation(DeliveryDto deliveryDto, HttpSession httpSession);
    String updateLocation(long deliveryId, DeliveryDto deliveryDto, HttpSession httpSession);
    String removeLocation(long deliveryId, HttpSession httpSession);
    List<Delivery> viewLocations();
    DeliveryResponse optimalRouteAndDeliveryCost(LocationDto locationDto);
}
