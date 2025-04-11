package com.se498;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessRuleServiceTest {

    private static BusinessRuleService globalService;
    private BusinessRuleService testService;

    @Mock
    private BusinessRule mockRule;

    @Spy
    private EmailVerificationRule spyRule;

    // Fixture Pattern
    static class TestRuleBuilder {
        private boolean returnValue = false;
        private boolean throwsException = false;
        private String errorMessage = "Test exception";

        public TestRuleBuilder withReturnValue(boolean returnValue) {
            this.returnValue = returnValue;
            return this;
        }

        public TestRuleBuilder thatThrowsException(boolean throwsException) {
            this.throwsException = throwsException;
            return this;
        }

        public TestRuleBuilder withErrorMessage(String message) {
            this.errorMessage = message;
            return this;
        }

        public BusinessRule build() {
            return new BusinessRule() {
                @Override
                public boolean apply(Object objectToCheck) throws Exception {
                    if (throwsException) {
                        throw new Exception(errorMessage);
                    }
                    return returnValue;
                }
            };
        }
    }

    // Container Pattern
    static class RuleTestContainer implements AutoCloseable {
        private final BusinessRule rule;
        private final BusinessRuleService service;
        private final Object testInput;

        public RuleTestContainer(BusinessRule rule, Object testInput) {
            this.rule = rule;
            this.service = new BusinessRuleService();
            this.testInput = testInput;
        }

        public boolean runTest() {
            return service.applyBusinessRule(rule, testInput);
        }

        @Override
        public void close() {
            // Cleanup resources if needed
        }
    }

    // Lifecycle Methods
    @BeforeAll
    static void initAll() {
        globalService = new BusinessRuleService();
    }

    @BeforeEach
    void init() {
        testService = new BusinessRuleService();
    }

    @AfterEach
    void tearDown() {
        testService = null;
    }

    @AfterAll
    static void tearDownAll() {
        globalService = null;
    }

    // Basic Assertions
    @Test
    @DisplayName("Basic test for applyBusinessRule")
    void testApplyBusinessRule() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of("existing@example.com"));
        assertTrue(testService.applyBusinessRule(rule, "new@example.com"));
        assertFalse(testService.applyBusinessRule(rule, "existing@example.com"));
    }

    // Exception Testing
    @Test
    @DisplayName("Test exception handling in applyBusinessRule")
    void testApplyBusinessRuleException() {
        BusinessRule rule = new TestRuleBuilder()
            .thatThrowsException(true)
            .withErrorMessage("Invalid email format")
            .build();
        
        assertFalse(testService.applyBusinessRule(rule, "invalid-email"));
    }

    // Conditional Test
    @Test
    @EnabledOnOs(OS.MAC)
    @DisplayName("Test that only runs on Mac")
    void testOperatingSystemOnly() {
        // This test demonstrates OS-specific validation
        assertTrue(true, "This test only runs on Mac OS");
    }

    // Assumption Test
    @Test
    @DisplayName("Test with assumptions")
    void testWithAssumption() {
        String environment = System.getProperty("test.environment", "development");
        assumeTrue("production".equals(environment), "Skipping test: not in production");
        // Test production-specific validation rules
    }

    // Nested Test
    @Nested
    @DisplayName("Tests for Email Validation")
    class EmailValidationTests {

        @Test
        @DisplayName("Test valid email")
        void testValidEmail() {
            EmailVerificationRule rule = new EmailVerificationRule(List.of());
            assertTrue(testService.applyBusinessRule(rule, "test@example.com"));
        }

        @Test
        @DisplayName("Test invalid email")
        void testInvalidEmail() {
            EmailVerificationRule rule = new EmailVerificationRule(List.of());
            assertFalse(testService.applyBusinessRule(rule, "invalid-email"));
        }
    }

    // Dynamic Test
    @TestFactory
    @DisplayName("Dynamic tests for email validation")
    Stream<DynamicTest> dynamicEmailTests() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of("existing@example.com"));
        return Stream.of(
            dynamicTest("Valid new email", () -> 
                assertTrue(testService.applyBusinessRule(rule, "new@example.com"))),
            dynamicTest("Invalid email format", () -> 
                assertFalse(testService.applyBusinessRule(rule, "invalid-email"))),
            dynamicTest("Existing email", () -> 
                assertFalse(testService.applyBusinessRule(rule, "existing@example.com")))
        );
    }

    // Parametrized Test
    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "user@domain.com", "valid@email.org"})
    @DisplayName("Test valid emails with parameterized test")
    void testValidEmailParameterized(String email) {
        EmailVerificationRule rule = new EmailVerificationRule(List.of());
        assertTrue(testService.applyBusinessRule(rule, email));
    }

    @ParameterizedTest
    @CsvSource({
        "test@example.com,true",
        "invalid-email,false",
        "existing@example.com,false"
    })
    @DisplayName("Test emails with expected results")
    void testEmailWithExpectedResult(String email, boolean expected) {
        EmailVerificationRule rule = new EmailVerificationRule(List.of("existing@example.com"));
        assertEquals(expected, testService.applyBusinessRule(rule, email));
    }

    // Mock Test
    @Test
    @DisplayName("Test with mock rule")
    void testWithMockRule() throws Exception {
        when(mockRule.apply("valid@example.com")).thenReturn(true);
        when(mockRule.apply("invalid@example.com")).thenReturn(false);

        assertTrue(testService.applyBusinessRule(mockRule, "valid@example.com"));
        assertFalse(testService.applyBusinessRule(mockRule, "invalid@example.com"));
        
        verify(mockRule, times(2)).apply(any());
    }

    // Repeated Test
    @RepeatedTest(5)
    @DisplayName("Repeated email validation test")
    void repeatedTest() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of());
        assertTrue(testService.applyBusinessRule(rule, "test@example.com"));
    }

    // Builder Pattern
    @Test
    @DisplayName("Test with builder pattern")
    void testWithBuilder() {
        BusinessRule successRule = new TestRuleBuilder()
            .withReturnValue(true)
            .build();
        
        BusinessRule failureRule = new TestRuleBuilder()
            .withReturnValue(false)
            .build();

        assertTrue(testService.applyBusinessRule(successRule, "test@example.com"));
        assertFalse(testService.applyBusinessRule(failureRule, "test@example.com"));
    }

    // Container Test
    @Test
    @DisplayName("Test with container pattern")
    void testWithContainer() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of());
        try (RuleTestContainer container = new RuleTestContainer(rule, "test@example.com")) {
            assertTrue(container.runTest());
        }
    }

    // Fixture Pattern
    @Test
    @DisplayName("Test with fixture pattern")
    void testWithFixture() {
        List<String> validEmails = Arrays.asList(
            "test@example.com",
            "user@domain.com",
            "valid@email.org"
        );
        
        List<String> invalidEmails = Arrays.asList(
            "invalid-email",
            "missing@domain",
            "@incomplete.com"
        );

        EmailVerificationRule rule = new EmailVerificationRule(List.of());

        for (String email : validEmails) {
            assertTrue(testService.applyBusinessRule(rule, email));
        }

        for (String email : invalidEmails) {
            assertFalse(testService.applyBusinessRule(rule, email));
        }
    }

    // Exception Testing
    @Test
    @DisplayName("Test validation exception properties")
    void testExceptionProperties() {
        BusinessRule rule = new TestRuleBuilder()
            .thatThrowsException(true)
            .withErrorMessage("Invalid format")
            .build();

        Exception exception = assertThrows(Exception.class, () -> 
            rule.apply("invalid-input"));
        assertEquals("Invalid format", exception.getMessage());
    }

    // Exception Testing
    @Test
    @DisplayName("Test BusinessRuleService handling validation exception")
    void testExceptionHandling() {
        BusinessRule rule = new TestRuleBuilder()
            .thatThrowsException(true)
            .build();

        assertFalse(testService.applyBusinessRule(rule, "test-input"),
            "Service should handle exceptions and return false");
    }

    @Test
    @DisplayName("Test with spy rule")
    void testWithSpyRule() throws Exception {
        assertTrue(testService.applyBusinessRule(spyRule, "test@example.com"));
        
        verify(spyRule).apply("test@example.com");
        
        doReturn(false).when(spyRule).apply("invalid@example.com");
        assertFalse(testService.applyBusinessRule(spyRule, "invalid@example.com"));
    }

    @Test
    @DisplayName("Test non-string input")
    void testNonStringInput() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of());
        Integer nonStringInput = 123;
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            rule.apply(nonStringInput));
        assertEquals("Email must be a string", exception.getMessage());
    }

    @Test
    @DisplayName("Test null or empty email")
    void testNullOrEmptyEmail() {
        EmailVerificationRule rule = new EmailVerificationRule(List.of());
        
        // Test null email
        Exception nullException = assertThrows(Exception.class, () -> 
            rule.apply(null));
        assertEquals("Email cannot be empty", nullException.getMessage());
        
        // Test empty email
        Exception emptyException = assertThrows(Exception.class, () -> 
            rule.apply("   "));
        assertEquals("Email cannot be empty", emptyException.getMessage());
    }
}
