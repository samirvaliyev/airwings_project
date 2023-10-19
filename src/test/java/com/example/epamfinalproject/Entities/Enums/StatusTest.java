package com.example.epamfinalproject.Entities.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void fromString() {
        assertEquals(Status.PAID, Status.fromString("PAID"));
        assertEquals(Status.PAID, Status.fromString("paid"));
        assertEquals(Status.PAID, Status.fromString("paID"));

        assertEquals(Status.PENDING, Status.fromString("PENDING"));
        assertEquals(Status.PENDING, Status.fromString("pending"));
        assertEquals(Status.PENDING, Status.fromString("pENDinG"));

        assertNull(Status.fromString("Status"));
        assertNull(Status.fromString("null"));
    }

    @Test
    void testToString() {
        assertEquals("paid", Status.PAID.toString());
        assertEquals("pending", Status.PENDING.toString());
    }
}