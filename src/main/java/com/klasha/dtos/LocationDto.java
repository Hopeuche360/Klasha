package com.klasha.dtos;

import com.klasha.models.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private Location origin;
    private Location destination;
}
