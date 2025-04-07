public class PasswordVerificationRule {

    public boolean validate(String password) throws PasswordVerificationException {
        if (password.length() < 8) {
            throw new PasswordVerificationException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new PasswordVerificationException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new PasswordVerificationException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new PasswordVerificationException("Password must contain at least one number.");
        }
        return true;
    }
}
