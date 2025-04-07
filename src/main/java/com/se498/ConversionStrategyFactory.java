package com.se498;

public class ConversionStrategyFactory {

    //TODO: Implement method(s) for the determining conversion strategy
    private static class InstanceHolder {
        private static final ConversionStrategyFactory INSTANCE = null;
    }
    public static ConversionStrategyFactory getInstance() {
        return null;
    }
    public ConversionStrategy getStrategy(String locale) throws ConversionStrategyException {
        return null;
    }
}
