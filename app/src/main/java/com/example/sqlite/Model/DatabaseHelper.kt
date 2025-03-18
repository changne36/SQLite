package com.example.sqlite.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "contact.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "contacts"
        private val COLUMN_ID = "id"
        private val COLUMN_NAME = "name"
        private val COLUMN_PHONE = "phone"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_PHONE TEXT)
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }
    fun insertContact(name: String, phone: String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues().apply() {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
        }
        val result = db.insert(TABLE_NAME, null, value)
        db.close()
        return result != -1L
    }

    fun updateContact(name: String, phone: String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues().apply() {
            put(COLUMN_PHONE, phone)
        }
        val result = db.update(TABLE_NAME, value, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
        return result > 0
    }
    fun deleteContact(name: String) : Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
        return result > 0
    }

    fun getContact() : List<Pair<String,String>>{
        val contacts = mutableListOf<Pair<String,String>>()
        val db = this.readableDatabase
        var cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if(cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                contacts.add(Pair(name, phone))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contacts
    }


}