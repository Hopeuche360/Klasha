package com.klasha.controllers;

import com.klasha.dtos.DeliveryDto;
import com.klasha.dtos.LocationDto;
import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.models.Delivery;
import com.klasha.responses.DeliveryResponse;
import com.klasha.responses.HttpResponse;
import com.klasha.services.DeliveryService;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DeliveryService deliveryService;

    protected static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpResponse> signup(@RequestBody SignupDto signupDto) {
        return response(CREATED, userService.register(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginDto loginDto, HttpSession httpSession) {
        return response(OK, userService.login(loginDto, httpSession));
    }

    @PostMapping("/add-location")
    public ResponseEntity<HttpResponse> addLocation(@RequestParam String address, HttpSession httpSession) {
        return response(CREATED, deliveryService.addDeliveryLocation(address, httpSession));
    }

    @PutMapping("/update-location/{deliveryId}")
    public ResponseEntity<HttpResponse> updateLocation(@PathVariable long deliveryId,
                                                       @RequestParam String address, HttpSession httpSession) {
        return response(OK, deliveryService.updateDeliveryLocation(deliveryId, address, httpSession));
    }

    @DeleteMapping("/remove-location/{deliveryId}")
    public ResponseEntity<HttpResponse> removeLocation(@PathVariable long deliveryId,
                                                       HttpSession httpSession) {
        return response(OK, deliveryService.removeLocation(deliveryId, httpSession));
    }

    @GetMapping("/view-locations")
    public List<Delivery> viewLocations() {
        return deliveryService.viewLocations();
    }

    @PostMapping("/route-and-cost")
    public DeliveryResponse optimalRouteAndDeliveryCost(@RequestBody LocationDto locationDto) {
        return deliveryService.optimalRouteAndDeliveryCost(locationDto);
    }
}
