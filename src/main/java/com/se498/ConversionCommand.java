package com.se498;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionCommand implements Command {

    //TODO: Implement method(s) for the command execution
    private final ConversionStrategy strategy;
    private final String date;

    public ConversionCommand(ConversionStrategy strategy, String date) {
        this.strategy = strategy;
        this.date = date;
    }

    @Override
    public Date execute() throws ParseException {
        return strategy.convert(date);
    }
}
