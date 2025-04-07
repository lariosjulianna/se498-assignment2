public class UserVerificationBusinessRule {
    private UsernameVerificationRule usernameRule;
    private PasswordVerificationRule passwordRule;

    public UserVerificationBusinessRule(String[] bannedWords) {
        this.usernameRule = new UsernameVerificationRule(bannedWords);
        this.passwordRule = new PasswordVerificationRule();
    }

    public boolean validate(String username, String password) throws UserVerificationException {
        try {
            usernameRule.validate(username);
            passwordRule.validate(password);
            return true;
        } catch (UsernameVerificationException | PasswordVerificationException e) {
            throw new UserVerificationException(e.getMessage());
        }
    }
}
