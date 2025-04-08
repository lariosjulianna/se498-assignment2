import java.util.List;

public class EmailVerificationRule {

    private final List<String> existingEmails;

    public EmailVerificationRule(List<String> existingEmails) {
        this.existingEmails = existingEmails;
    }

    public boolean validate(String email) throws EmailVerificationException {
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