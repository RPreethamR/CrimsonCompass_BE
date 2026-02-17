package com.backend.CrimsonCompass.util;

import com.backend.CrimsonCompass.dto.PlaceImageResponseDTO;
import com.backend.CrimsonCompass.dto.PlaceResponseDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceMapper {

    public PlaceResponseDTO toResponseDTO(Place place, List<PlaceImage> images, List<ReviewResponseDTO> reviews) {
        List<PlaceImageResponseDTO> imageResponses = images.stream()
                .map(image -> new PlaceImageResponseDTO(image.getImageId(), image.getImageUrl()))
                .collect(Collectors.toList());

        return new PlaceResponseDTO(
                place.getPlaceId(),
                place.getName(),
                place.getDescription(),
                place.getLocation(),
                (double) place.getLatitude(),
                (double) place.getLongitude(),
                place.getCountry(),
                place.getState(),
                place.getCity(),
                place.getCategory().getName(),
                imageResponses,
                reviews
        );
    }
}
