package ru.hofftech.parcellogistic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrucksJsonNodeDto {

    @JsonProperty("trucks")
    private List<ParcelsJsonNodeDto> trucks;
}
