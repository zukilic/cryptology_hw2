package com.example.cryptology_hw2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT NOT NULL UNIQUE, " + //NOT NULL UNIQUE
                PASSWORD_COL + " TEXT NOT NULL" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun tanimlama(name : String, password : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COL, name)
        values.put(PASSWORD_COL, password)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    //clear all tables
    fun clear() {
        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME)
        db.close()
    }

    fun deleteUser(name: String) {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "name=?", arrayOf(name))
        db.close()
    }

    // below method is to get
    // all data from our database
    fun listeleme(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun checkUser(name: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(ID_COL)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$NAME_COL = ? AND $PASSWORD_COL = ?"
        // selection arguments
        val selectionArgs = arrayOf(name, password)
        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }

    companion object{
        private val DATABASE_NAME = "HOMEWORK2"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "users"
        val ID_COL = "id"
        val NAME_COL = "name"
        val PASSWORD_COL = "password"
    }
}
