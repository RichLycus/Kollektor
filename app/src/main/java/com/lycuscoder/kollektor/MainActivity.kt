package com.lycuscoder.kollektor

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etMessage: TextInputEditText
    private lateinit var btnSubmit: MaterialButton
    private lateinit var btnViewData: MaterialButton
    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilAddress: TextInputLayout
    private lateinit var tilMessage: TextInputLayout
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupListeners()
        dbHelper = DatabaseHelper(this)
        Toast.makeText(this, "Selamat datang di FormMaster! 👋", Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        etMessage = findViewById(R.id.etMessage)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnViewData = findViewById(R.id.btnViewData)
        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilAddress = findViewById(R.id.tilAddress)
        tilMessage = findViewById(R.id.tilMessage)
    }

    private fun setupListeners() {
        btnSubmit.setOnClickListener { submitForm() }
        btnViewData.setOnClickListener { openDataListActivity() }
        etName.addTextChangedListener(createTextWatcher(tilName))
        etEmail.addTextChangedListener(createTextWatcher(tilEmail))
        etPhone.addTextChangedListener(createTextWatcher(tilPhone))
        etAddress.addTextChangedListener(createTextWatcher(tilAddress))
    }

    private fun createTextWatcher(textInputLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun submitForm() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val message = etMessage.text.toString().trim()

        clearAllErrors()

        val validation = FormEntry.validate(name, email, phone, address)
        if (!validation.isValid) {
            showValidationErrors(validation.errors)
            Toast.makeText(this, "Mohon perbaiki kesalahan di form ⚠️", Toast.LENGTH_SHORT).show()
            return
        }

        showLoadingState(true)

        val form = FormEntry(name = name, email = email, phone = phone, address = address, message = message)
        val result = dbHelper.insertForm(form)

        if (result != -1L) {
            Toast.makeText(this, "Data berhasil disimpan! ✅", Toast.LENGTH_SHORT).show()
            clearForm()
            showSuccessAnimation()
        } else {
            Toast.makeText(this, "Gagal menyimpan data ❌", Toast.LENGTH_SHORT).show()
        }

        showLoadingState(false)
    }

    private fun clearAllErrors() {
        tilName.error = null
        tilEmail.error = null
        tilPhone.error = null
        tilAddress.error = null
    }

    private fun showValidationErrors(errors: List<String>) {
        for (error in errors) {
            when {
                error.contains("nama", ignoreCase = true) -> tilName.error = error
                error.contains("email", ignoreCase = true) -> tilEmail.error = error
                error.contains("telepon", ignoreCase = true) -> tilPhone.error = error
                error.contains("alamat", ignoreCase = true) -> tilAddress.error = error
            }
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        btnSubmit.isEnabled = !isLoading
        btnViewData.isEnabled = !isLoading
        btnSubmit.text = if (isLoading) "Menyimpan..." else "💾 Simpan Data"
    }

    private fun showSuccessAnimation() {
        btnSubmit.text = "✅ Tersimpan!"
        btnSubmit.postDelayed({
            btnSubmit.text = "💾 Simpan Data"
        }, 2000)
    }

    private fun clearForm() {
        etName.text?.clear()
        etEmail.text?.clear()
        etPhone.text?.clear()
        etAddress.text?.clear()
        etMessage.text?.clear()
        etName.clearFocus()
    }

    private fun openDataListActivity() {
        val intent = Intent(this, DataListActivity::class.java)
        startActivity(intent)
    }
}