import java.util.List;

public class EmailVerificationRule {

    private final List<String> existingEmails;

    public EmailVerificationRule(List<String> existingEmails) {
        this.existingEmails = existingEmails;
    }

    public boolean validate(String email) throws EmailVerificationException {
        // Pattern: basic username@domain.com validation
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new EmailVerificationException("Invalid email format. Please use the format username@domain.com.");
        }

        for (String existingEmail : existingEmails) {
            if (existingEmail.equalsIgnoreCase(email)) {
                throw new EmailVerificationException(
                        "This email address is already associated with an existing account. Please use a different email."
                );
            }
        }

        return true;
    }
}