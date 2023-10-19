package com.example.epamfinalproject.Entities.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void fromString() {
        assertEquals(UserRole.CLIENT, UserRole.fromString("CLIENT"));
        assertEquals(UserRole.CLIENT, UserRole.fromString("client"));
        assertEquals(UserRole.CLIENT, UserRole.fromString("cliEnT"));

        assertEquals(UserRole.ADMINISTRATOR, UserRole.fromString("ADMINISTRATOR"));
        assertEquals(UserRole.ADMINISTRATOR, UserRole.fromString("administrator"));
        assertEquals(UserRole.ADMINISTRATOR, UserRole.fromString("admINIstratOr"));

        assertNull(UserRole.fromString("ROLE"));
        assertNull(UserRole.fromString("admin"));
    }

    @Test
    void testToString() {
        assertEquals("client", UserRole.CLIENT.toString());
        assertEquals("administrator", UserRole.ADMINISTRATOR.toString());
    }
}