package com.lycuscoder.kollektor

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FormAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var cardEmptyState: CardView
    private lateinit var tvDataCount: TextView
    private val formList = mutableListOf<FormEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_list)
        initViews()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        cardEmptyState = findViewById(R.id.cardEmptyState)
        tvDataCount = findViewById(R.id.tvDataCount)
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        
        dbHelper = DatabaseHelper(this)
        adapter = FormAdapter(formList) { form -> showDeleteConfirmation(form) }
        recyclerView.adapter = adapter
        
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Data Tersimpan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadData() {
        try {
            formList.clear()
            val forms = dbHelper.getAllForms()
            formList.addAll(forms)
            adapter.notifyDataSetChanged()
            updateUI()
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val count = formList.size
        tvDataCount.text = if (count > 0) "$count data" else "Tidak ada data"
        
        if (formList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            cardEmptyState.visibility = android.view.View.VISIBLE
            cardEmptyState.alpha = 0f
            cardEmptyState.animate().alpha(1f).setDuration(300).start()
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            cardEmptyState.visibility = android.view.View.GONE
            recyclerView.alpha = 0f
            recyclerView.animate().alpha(1f).setDuration(300).start()
        }
    }

    private fun showDeleteConfirmation(form: FormEntry) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Data")
            .setMessage("Apakah Anda yakin ingin menghapus data:\n\nNama: ${form.name}\nEmail: ${form.email}")
            .setPositiveButton("Hapus") { _, _ -> deleteForm(form) }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteForm(form: FormEntry) {
        try {
            val result = dbHelper.deleteForm(form.id)
            if (result > 0) {
                Toast.makeText(this, "Data berhasil dihapus ✅", Toast.LENGTH_SHORT).show()
                loadData()
            } else {
                Toast.makeText(this, "Gagal menghapus data ❌", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}