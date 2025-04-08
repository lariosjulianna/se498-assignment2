import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ClassCodeVerificationRuleTest {

    private final List<String> activeClassCodes = List.of("ABC123", "MATH2024", "SCIENCE789");
    private final ClassCodeVerificationRule classCodeRule = new ClassCodeVerificationRule(activeClassCodes);

    @Test
    public void testValidClassCodePasses() throws ClassCodeValidationException {
        assertTrue(classCodeRule.validate("ABC123"));
    }

    @Test
    public void testInvalidClassCodeFails() {
        ClassCodeValidationException exception = assertThrows(
                ClassCodeValidationException.class,
                () -> classCodeRule.validate("12345")
        );
        assertEquals("Invalid class code. Please check with your teacher for the correct code.", exception.getMessage());
    }

    @Test
    public void testEmptyClassCodeFails() {
        ClassCodeValidationException exception = assertThrows(
                ClassCodeValidationException.class,
                () -> classCodeRule.validate("")
        );
        assertEquals("Class Code is required.", exception.getMessage());
    }

    @Test
    public void testNullClassCodeFails() {
        ClassCodeValidationException exception = assertThrows(
                ClassCodeValidationException.class,
                () -> classCodeRule.validate(null)
        );
        assertEquals("Class Code is required.", exception.getMessage());
    }

    @Test
    public void testClassCodeWithExtraSpaces() throws ClassCodeValidationException {
        assertTrue(classCodeRule.validate("  MATH2024  ")); // Should still pass
    }
}
