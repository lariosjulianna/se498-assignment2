import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordVerificationRuleTest {

    private final PasswordVerificationRule passwordRule = new PasswordVerificationRule();

    @Test
    public void testValidPassword() throws PasswordVerificationException {
        assertTrue(passwordRule.validate("Password123"));
    }

    @Test
    public void testPasswordTooShort() {
        assertThrows(PasswordVerificationException.class, () -> passwordRule.validate("Pass"));
    }

    @Test
    public void testPasswordWithoutUppercase() {
        assertThrows(PasswordVerificationException.class, () -> passwordRule.validate("password123"));
    }

    @Test
    public void testPasswordWithoutNumber() {
        assertThrows(PasswordVerificationException.class, () -> passwordRule.validate("Password"));
    }
}
