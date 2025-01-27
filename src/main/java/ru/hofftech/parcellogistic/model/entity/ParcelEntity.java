package ru.hofftech.parcellogistic.model.entity;

import lombok.Builder;

import java.util.List;

@Builder
public record ParcelEntity(String id, String name, List<String> content) {}
