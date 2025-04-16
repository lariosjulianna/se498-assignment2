package com.se498;

import java.util.List;

public class BusinessRuleFactory {
    // Singleton Pattern with private constructor and static getInstance() method
    private static BusinessRuleFactory instance;
    
    private BusinessRuleFactory() {}
    
    public static synchronized BusinessRuleFactory getInstance() {
        if (instance == null) {
            instance = new BusinessRuleFactory();
        }
        return instance;
    }
    
    // Factory methods for different types of business rules
    public BusinessRule createEmailVerificationRule(List<String> existingEmails) {
        return new EmailVerificationRule(existingEmails);
    }
    
    public BusinessRule createEmailVerificationRule() {
        return new EmailVerificationRule();
    }
    
    public BusinessRule createCustomRule(boolean returnValue, boolean throwsException, String errorMessage) {
        return new BusinessRule() {
            @Override
            public boolean apply(Object objectToCheck) throws Exception {
                if (throwsException) {
                    throw new Exception(errorMessage);
                }
                return returnValue;
            }
        };
    }
} 