package com.se498;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmailVerificationRule implements BusinessRule {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final List<String> existingEmails;

    public EmailVerificationRule() {
        this.existingEmails = new ArrayList<>();
    }

    public EmailVerificationRule(List<String> existingEmails) {
        this.existingEmails = existingEmails;
    }

    @Override
    public boolean apply(Object objectToCheck) throws Exception {
        if (objectToCheck == null) {
            throw new Exception("Email cannot be empty");
        }

        if (!(objectToCheck instanceof String)) {
            throw new IllegalArgumentException("Email must be a string");
        }

        String email = (String) objectToCheck;
        return validate(email);
    }

    public boolean validate(String email) throws EmailVerificationException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailVerificationException("Email cannot be empty");
        }

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new EmailVerificationException("Invalid email format. Please use the format username@domain.com.");
        }

        if (existingEmails.contains(email.toLowerCase())) {
            throw new EmailVerificationException("This email address is already associated with an existing account. Please use a different email.");
        }

        return true;
    }
}