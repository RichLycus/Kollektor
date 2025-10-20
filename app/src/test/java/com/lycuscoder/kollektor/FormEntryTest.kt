package com.lycuscoder.kollektor

import org.junit.Test
import org.junit.Assert.*

class FormEntryTest {

    @Test
    fun testValidFormEntry() {
        val result = FormEntry.validate(
            name = "John Doe",
            email = "john@example.com",
            phone = "08123456789",
            address = "Jl. Sudirman No. 123, Jakarta"
        )
        assertTrue(result.isValid)
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun testInvalidEmail() {
        val result = FormEntry.validate(
            name = "John Doe",
            email = "invalid-email",
            phone = "08123456789",
            address = "Jl. Sudirman No. 123, Jakarta"
        )
        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("email", ignoreCase = true) })
    }

    @Test
    fun testEmptyName() {
        val result = FormEntry.validate(
            name = "",
            email = "john@example.com",
            phone = "08123456789",
            address = "Jl. Sudirman No. 123, Jakarta"
        )
        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("nama", ignoreCase = true) })
    }

    @Test
    fun testInvalidPhoneNumber() {
        val result = FormEntry.validate(
            name = "John Doe",
            email = "john@example.com",
            phone = "123",
            address = "Jl. Sudirman No. 123, Jakarta"
        )
        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("telepon", ignoreCase = true) })
    }

    @Test
    fun testShortAddress() {
        val result = FormEntry.validate(
            name = "John Doe",
            email = "john@example.com",
            phone = "08123456789",
            address = "Jkt"
        )
        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("alamat", ignoreCase = true) })
    }
}
