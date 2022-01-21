package com.klasha.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryResponse {
    private double optimalRoute;
    private String costOfDelivery;
}
