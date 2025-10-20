package com.lycuscoder.kollektor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FormMaster.db"
        private const val DATABASE_VERSION = 1
        
        const val TABLE_FORMS = "form_entries"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_MESSAGE = "message"
        const val COLUMN_CREATED_AT = "created_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createFormsTable = """
            CREATE TABLE $TABLE_FORMS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL,
                $COLUMN_ADDRESS TEXT NOT NULL,
                $COLUMN_MESSAGE TEXT,
                $COLUMN_CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createFormsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FORMS")
        onCreate(db)
    }

    fun insertForm(form: FormEntry): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, form.name)
            put(COLUMN_EMAIL, form.email)
            put(COLUMN_PHONE, form.phone)
            put(COLUMN_ADDRESS, form.address)
            put(COLUMN_MESSAGE, form.message)
        }
        val result = db.insert(TABLE_FORMS, null, values)
        db.close()
        return result
    }

    fun getAllForms(): List<FormEntry> {
        val forms = mutableListOf<FormEntry>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_FORMS,
            null, null, null, null, null,
            "$COLUMN_CREATED_AT DESC"
        )
        with(cursor) {
            while (moveToNext()) {
                val form = FormEntry(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    phone = getString(getColumnIndexOrThrow(COLUMN_PHONE)),
                    address = getString(getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    message = getString(getColumnIndexOrThrow(COLUMN_MESSAGE)) ?: "",
                    createdAt = getString(getColumnIndexOrThrow(COLUMN_CREATED_AT)) ?: ""
                )
                forms.add(form)
            }
        }
        cursor.close()
        db.close()
        return forms
    }

    fun deleteForm(id: Int): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_FORMS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun getFormsCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_FORMS", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }
}