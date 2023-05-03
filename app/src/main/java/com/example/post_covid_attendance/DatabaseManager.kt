package com.example.post_covid_attendance

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager(context: Context) {

    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)

    fun insertUser(name: String, email: String, password: String): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USER_NAME, name)
            put(DatabaseHelper.COLUMN_USER_EMAIL, email)
            put(DatabaseHelper.COLUMN_USER_PASSWORD, password)
        }
        return databaseHelper.writableDatabase.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    fun getUserByEmail(email: String): User? {
        val columns = arrayOf(DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_EMAIL, DatabaseHelper.COLUMN_USER_PASSWORD)
        val selection = "${DatabaseHelper.COLUMN_USER_EMAIL} = ?"
        val selectionArgs = arrayOf(email)
        val cursor = databaseHelper.readableDatabase.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        return if (cursor.moveToFirst()) {
            User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PASSWORD))
            )
        } else {
            null
        }
    }

    private class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_TABLE_USERS)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // Implement database upgrade logic here
        }

        companion object {
            private const val DATABASE_NAME = "myapp.db"
            private const val DATABASE_VERSION = 1

            // User table
            const val TABLE_USERS = "users"
            const val COLUMN_USER_ID = "_id"
            const val COLUMN_USER_NAME = "name"
            const val COLUMN_USER_EMAIL = "email"
            const val COLUMN_USER_PASSWORD = "password"

            // Create user table query
            private const val CREATE_TABLE_USERS = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT NOT NULL,
                $COLUMN_USER_EMAIL TEXT NOT NULL,
                $COLUMN_USER_PASSWORD TEXT NOT NULL
            )
        """
        }
    }
}