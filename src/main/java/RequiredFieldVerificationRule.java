public class RequiredFieldVerificationRule {
    public boolean validate(String fullName, String email, String password, String username, String classCode)
            throws RequiredFieldException {

        if (isBlank(fullName)) {
            throw new RequiredFieldException("Full Name is required.");
        }
        if (isBlank(email)) {
            throw new RequiredFieldException("Email is required.");
        }
        if (isBlank(password)) {
            throw new RequiredFieldException("Password is required.");
        }
        if (isBlank(username)) {
            throw new RequiredFieldException("Username is required.");
        }
        if (isBlank(classCode)) {
            throw new RequiredFieldException("Class Code is required.");
        }

        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
