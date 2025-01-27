package ru.hofftech.parcellogistic.strategy;

import ru.hofftech.parcellogistic.model.OutputResult;

import java.util.Map;

public interface CommandStrategy {

    OutputResult processCommand(Map<String, String> options);
}
