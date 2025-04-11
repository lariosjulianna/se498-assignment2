package com.se498;

public class BusinessRuleService {

    public boolean applyBusinessRule(BusinessRule rule, Object objectToCheck) {
        try {
            return rule.apply(objectToCheck);
        } catch (Exception e) {
            return false;
        }
    }
}
