import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationRuleTest {

    private final List<String> existingEmails = List.of("existinguser@example.com");
    private final EmailVerificationRule emailRule = new EmailVerificationRule(existingEmails);

    @Test
    public void testUniqueEmailPasses() throws EmailVerificationException {
        assertTrue(emailRule.validate("newuser@example.com"));
    }

    @Test
    public void testDuplicateEmailFails() {
        EmailVerificationException exception = assertThrows(
                EmailVerificationException.class,
                () -> emailRule.validate("existinguser@example.com")
        );
        assertEquals("This email address is already associated with an existing account. Please use a different email.", exception.getMessage());
    }

    @Test
    public void testDuplicateEmailFailsCaseInsensitive() {
        assertThrows(EmailVerificationException.class, () -> emailRule.validate("ExistingUser@Example.com"));
    }

    @Test
    public void testInvalidEmailFormatFails() {
        EmailVerificationException exception = assertThrows(
                EmailVerificationException.class,
                () -> emailRule.validate("invalid-email")
        );
        assertEquals("Invalid email format. Please use the format username@domain.com.", exception.getMessage());
    }

    @Test
    public void testInvalidEmailMissingAtSymbol() {
        assertThrows(EmailVerificationException.class, () -> emailRule.validate("userexample.com"));
    }

    @Test
    public void testInvalidEmailMissingDomain() {
        assertThrows(EmailVerificationException.class, () -> emailRule.validate("user@"));
    }
}