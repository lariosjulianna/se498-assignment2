import java.util.List;

public class ClassCodeVerificationRule {

    private final List<String> validClassCodes;

    public ClassCodeVerificationRule(List<String> validClassCodes) {
        this.validClassCodes = validClassCodes;
    }

    public boolean validate(String classCode) throws ClassCodeValidationException {
        if (classCode == null || classCode.trim().isEmpty()) {
            throw new ClassCodeValidationException("Class Code is required.");
        }

        if (!validClassCodes.contains(classCode.trim())) {
            throw new ClassCodeValidationException("Invalid class code. Please check with your teacher for the correct code.");
        }

        return true;
    }
}
