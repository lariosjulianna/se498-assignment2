import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserVerificationServiceTest {

    private final String[] bannedWords = {"badword", "offensive"};
    private final UserVerificationService service = new UserVerificationService(bannedWords);

    @Test
    public void testValidUser() {
        String result = service.verifyUser("validusername", "ValidPassword123");
        assertEquals("User verified successfully", result);
    }

    @Test
    public void testInvalidUsername() {
        String result = service.verifyUser("badwordUser", "ValidPassword123");
        assertEquals("Verification failed: Username contains inappropriate word.", result);
    }

    @Test
    public void testInvalidPassword() {
        String result = service.verifyUser("goodusername", "short");
        assertEquals("Verification failed: Password must be at least 8 characters long.", result);
    }
}
