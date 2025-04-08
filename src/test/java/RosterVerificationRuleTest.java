import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class RosterVerificationRuleTest {

    private final Map<String, List<String>> mockRoster = Map.of(
            "XYZ789", List.of("student1@school.edu", "student2@school.edu"),
            "ABC123", List.of("alpha@school.edu")
    );

    private final RosterVerificationRule rule = new RosterVerificationRule(mockRoster);

    @Test
    public void testValidClassCodeReturnsRoster() throws RosterValidationException {
        List<String> roster = rule.getRosterForClassCode("XYZ789");
        assertEquals(2, roster.size());
        assertTrue(roster.contains("student1@school.edu"));
    }

    @Test
    public void testEmptyClassCodeThrowsException() {
        RosterValidationException exception = assertThrows(
                RosterValidationException.class,
                () -> rule.getRosterForClassCode("")
        );
        assertEquals("Class Code is required to view the roster.", exception.getMessage());
    }

    @Test
    public void testNonexistentClassCodeThrowsException() {
        RosterValidationException exception = assertThrows(
                RosterValidationException.class,
                () -> rule.getRosterForClassCode("INVALID999")
        );
        assertEquals("No students found for this class code.", exception.getMessage());
    }

    @Test
    public void testClassCodeWithSpaces() throws RosterValidationException {
        List<String> roster = rule.getRosterForClassCode("  XYZ789  ");
        assertTrue(roster.contains("student1@school.edu"));
    }

    @Test
    public void testClassWithSingleStudent() throws RosterValidationException {
        List<String> roster = rule.getRosterForClassCode("ABC123");
        assertEquals(1, roster.size());
        assertEquals("alpha@school.edu", roster.get(0));
    }

    @Test
    public void testNullClassCodeThrowsException() {
        RosterValidationException exception = assertThrows(
                RosterValidationException.class,
                () -> rule.getRosterForClassCode(null)
        );
        assertEquals("Class Code is required to view the roster.", exception.getMessage());
    }

    @Test
    public void testClassCodeWithEmptyRosterThrowsException() {
        Map<String, List<String>> modifiedRoster = Map.of(
                "XYZ789", List.of("student1@school.edu", "student2@school.edu"),
                "ABC123", List.of()
        );

        RosterVerificationRule modifiedRule = new RosterVerificationRule(modifiedRoster);

        RosterValidationException exception = assertThrows(
                RosterValidationException.class,
                () -> modifiedRule.getRosterForClassCode("ABC123")
        );
        assertEquals("No students found for this class code.", exception.getMessage());
    }
}