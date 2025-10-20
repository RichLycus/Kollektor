package com.lycuscoder.kollektor

import android.util.Patterns

data class FormEntry(
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val message: String = "",
    val createdAt: String = ""
) {
    companion object {
        fun validate(name: String, email: String, phone: String, address: String): ValidationResult {
            val errors = mutableListOf<String>()
            
            // Validate name
            if (name.isEmpty()) {
                errors.add("Nama tidak boleh kosong")
            } else if (name.length < 2) {
                errors.add("Nama minimal 2 karakter")
            }
            
            // Validate email
            if (email.isEmpty()) {
                errors.add("Email tidak boleh kosong")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errors.add("Format email tidak valid")
            }
            
            // Validate phone
            val cleanPhone = phone.replace("[^0-9]".toRegex(), "")
            if (phone.isEmpty()) {
                errors.add("Nomor telepon tidak boleh kosong")
            } else if (cleanPhone.length < 10 || cleanPhone.length > 15) {
                errors.add("Nomor telepon harus 10-15 digit")
            }
            
            // Validate address
            if (address.isEmpty()) {
                errors.add("Alamat tidak boleh kosong")
            } else if (address.length < 5) {
                errors.add("Alamat minimal 5 karakter")
            }
            
            return ValidationResult(errors.isEmpty(), errors)
        }
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String>
)