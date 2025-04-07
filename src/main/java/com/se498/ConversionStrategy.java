package com.se498;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ConversionStrategy {

    public Date convert(String date) throws ParseException;
}
