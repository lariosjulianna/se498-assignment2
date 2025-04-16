import org.junit.jupiter.api.Test;

import com.se498.EmailVerificationException;
import com.se498.EmailVerificationRule;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationRuleTest {

    private final List<String> existingEmails = List.of("existinguser@example.com");
    private final EmailVerificationRule emailRule = new EmailVerificationRule(existingEmails);
    private final EmailVerificationRule validator = new EmailVerificationRule();

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

    @Test
    void testValidate_nullEmail_throwsException() {
        EmailVerificationException exception = assertThrows(
                EmailVerificationException.class,
                () -> validator.validate(null)
        );
        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    void testValidate_emptyEmail_throwsException() {
        EmailVerificationException exception = assertThrows(
                EmailVerificationException.class,
                () -> validator.validate("")
        );
        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    void testValidate_blankEmail_throwsException() {
        EmailVerificationException exception = assertThrows(
                EmailVerificationException.class,
                () -> validator.validate("   ")
        );
        assertEquals("Email cannot be empty", exception.getMessage());
    }
}