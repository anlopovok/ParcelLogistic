package ru.hofftech.parcellogistic.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckJsonNode {

    @JsonProperty("truck_type")
    private String truckType;

    @JsonProperty("parcels")
    private List<ParcelJsonNode> parcels;
}
