import java.util.List;
import java.util.Map;

public class RosterVerificationRule {

    private final Map<String, List<String>> rosterData;

    public RosterVerificationRule(Map<String, List<String>> rosterData) {
        this.rosterData = rosterData;
    }

    public List<String> getRosterForClassCode(String classCode) throws RosterValidationException {
        if (classCode == null || classCode.trim().isEmpty()) {
            throw new RosterValidationException("Class Code is required to view the roster.");
        }

        List<String> students = rosterData.get(classCode.trim());

        if (students == null || students.isEmpty()) {
            throw new RosterValidationException("No students found for this class code.");
        }

        return students;
    }
}
