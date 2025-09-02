package co.com.bancolombia.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

class UserTest {

    @Test
    void shouldCreateUserWithBuilder() {
        User user = User.builder()
                .id(1)
                .name("John")
                .lastname("Doe")
                .email("john@email.com")
                .baseSalary(5000)
                .build();

        assertEquals(1, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastname());
        assertEquals("john@email.com", user.getEmail());
        assertEquals(5000, user.getBaseSalary());
    }

    @Test
    void shouldUpdateFieldsWithSetters() {
        User user = new User();
        user.setName("Alice");
        user.setLastname("Smith");

        assertEquals("Alice", user.getName());
        assertEquals("Smith", user.getLastname());
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        Date birthdate = Date.valueOf("2000-01-01");
        User user = new User(1, "John", "Doe", birthdate, "Address", "12345", "john@email.com", 4000);

        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastname());
        assertEquals("12345", user.getPhone());
    }

    @Test
    void shouldCloneAndModifyUserWithToBuilder() {
        User original = User.builder()
                .id(1)
                .name("John")
                .lastname("Doe")
                .email("john@email.com")
                .baseSalary(5000)
                .build();

        User modified = original.toBuilder()
                .name("Jane")
                .build();

        assertEquals("Jane", modified.getName());
        assertEquals("Doe", modified.getLastname());
        assertNotEquals(original.getName(), modified.getName());
    }

    @Test
    void shouldAllowNullValues() {
        User user = User.builder()
                .name(null)
                .lastname(null)
                .email(null)
                .build();

        assertNull(user.getName());
        assertNull(user.getLastname());
        assertNull(user.getEmail());
    }
}