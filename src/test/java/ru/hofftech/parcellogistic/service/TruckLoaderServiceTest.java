package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.util.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckLoaderServiceTest {

    private TruckLoaderService truckLoaderService;


    @BeforeEach
    public void setUp() {
        this.truckLoaderService = new TruckLoaderService(
                new TrucksJsonFileParser(new JsonReader()),
                new TxtWriter()
        );
    }


    @Test
    void testFile_whenUnknownFileName_thenThrowsError() {
        assertThatThrownBy(
                () -> truckLoaderService.loadTrucksFromFile("test.json")
        ).isInstanceOf(IOException.class);
    }

    @Test
    void testTruckLoading_whenTrucksHasParcels_thenResultEqualToString() {
        LoadTrucksResult loadTrucksResult = truckLoaderService
                .loadTrucksFromFile("trucks.json");
        assertThat(loadTrucksResult.toString().equals("""
                999
                999
                999
                
                666
                666
                
                55555
                
                1
                
                1
                
                3
                
                999
                999
                999""")).isTrue();
    }
}