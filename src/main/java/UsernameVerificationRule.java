public class UsernameVerificationRule {
    private String[] bannedWords;

    public UsernameVerificationRule(String[] bannedWords) {
        this.bannedWords = bannedWords;
    }

    public boolean validate(String username) throws UsernameVerificationException {
        for (String word : bannedWords) {
            if (username.toLowerCase().contains(word.toLowerCase())) {
                throw new UsernameVerificationException("Username contains inappropriate word.");
            }
        }
        if (username.length() < 5) {
            throw new UsernameVerificationException("Username must be at least 5 characters long.");
        }
        return true;
    }
}
