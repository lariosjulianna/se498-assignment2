import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequiredFieldVerificationRuleTest {
    private final RequiredFieldVerificationRule rule = new RequiredFieldVerificationRule();

    @Test
    public void testAllFieldsPresent() throws RequiredFieldException {
        assertTrue(rule.validate("Jane Doe", "jane@example.com", "Password123", "janedoe", "ABC123"));
    }

    @Test
    public void testMissingFullName() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("", "jane@example.com", "Password123", "janedoe", "ABC123")
        );
        assertEquals("Full Name is required.", exception.getMessage());
    }

    @Test
    public void testMissingEmail() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("Jane Doe", "", "Password123", "janedoe", "ABC123")
        );
        assertEquals("Email is required.", exception.getMessage());
    }

    @Test
    public void testMissingPassword() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("Jane Doe", "jane@example.com", "", "janedoe", "ABC123")
        );
        assertEquals("Password is required.", exception.getMessage());
    }

    @Test
    public void testMissingUsername() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("Jane Doe", "jane@example.com", "Password123", "", "ABC123")
        );
        assertEquals("Username is required.", exception.getMessage());
    }

    @Test
    public void testMissingClassCode() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("Jane Doe", "jane@example.com", "Password123", "janedoe", "")
        );
        assertEquals("Class Code is required.", exception.getMessage());
    }

    @Test
    public void testWhitespaceOnlyValues() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("   ", "   ", "   ", "   ", "   ")
        );
        assertEquals("Full Name is required.", exception.getMessage()); // Stops at the first error
    }

    @Test
    public void testIsBlankWithNullValue() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate(null, "jane@example.com", "Password123", "janedoe", "ABC123")
        );
        assertEquals("Full Name is required.", exception.getMessage());
    }

    @Test
    public void testIsBlankWithEmptyString() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("", "jane@example.com", "Password123", "janedoe", "ABC123")
        );
        assertEquals("Full Name is required.", exception.getMessage());
    }

    @Test
    public void testIsBlankWithWhitespaceString() {
        RequiredFieldException exception = assertThrows(
                RequiredFieldException.class,
                () -> rule.validate("   ", "jane@example.com", "Password123", "janedoe", "ABC123")
        );
        assertEquals("Full Name is required.", exception.getMessage());
    }
}