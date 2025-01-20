package ru.hofftech.parcellogistic.model.dto;

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
public class ParcelJsonNodeDto {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("content")
    private List<String> content;
}
