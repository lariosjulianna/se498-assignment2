package com.se498;

public class ConversionStrategyException extends Exception {

    private final String locale;
    private final String reason;

    public ConversionStrategyException (String locale, String reason) {
        super("Conversion Strategy does not exist " + locale + ": " + reason);
        this.locale = locale;
        this.reason = reason;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getReason() {
        return this.reason;
    }

}
