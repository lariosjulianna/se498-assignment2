import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsernameVerificationRuleTest {

    private final String[] bannedWords = {"badword", "offensive"}; // Later, we would actually implement words to avoid
    private final UsernameVerificationRule usernameRule = new UsernameVerificationRule(bannedWords);

    @Test
    public void testValidUsername() throws UsernameVerificationException {
        assertTrue(usernameRule.validate("goodusername"));
    }

    @Test
    public void testInvalidUsernameWithBannedWord() {
        assertThrows(UsernameVerificationException.class, () -> usernameRule.validate("badwordUser"));
    }

    @Test
    public void testUsernameTooShort() {
        assertThrows(UsernameVerificationException.class, () -> usernameRule.validate("use"));
    }
}
