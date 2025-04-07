public class UserVerificationService {
    private UserVerificationBusinessRule businessRule;

    public UserVerificationService(String[] bannedWords) {
        this.businessRule = new UserVerificationBusinessRule(bannedWords);
    }

    public String verifyUser(String username, String password) {
        try {
            businessRule.validate(username, password);
            return "User verified successfully";
        } catch (UserVerificationException e) {
            return "Verification failed: " + e.getMessage();
        }
    }
}

