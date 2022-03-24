package com.example.week5asn1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ("
                + UID_COL + " INTEGER PRIMARY KEY TEXT, "
                + FIRST_NAME_COL + " TEXT, "
                + LAST_NAME_COL + " TEXT, "
                + REWARDS_COL + " INTEGER" + ")")
        Log.i("QUINTRIX query", query)
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addUser(uid: Int, fn: String, ln: String, Rewards: Int){
        val values = ContentValues()
        values.put("UID_COL", uid)
        values.put("FIRST_NAME_COL", fn)
        values.put("LAST_NAME_COL", ln)
        values.put("REWARDS_COL", Rewards)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun delete(uid: Int){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "UID == $uid", null)
    }

    fun getUser(uid: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE UID == $uid", null)
    }

    companion object{
        private val DATABASE_NAME = "ANDROIDKTCLASS"
        private val DATABASE_VERSION = 3
        val TABLE_NAME = "simple_table"
        val UID_COL = "uid"
        val FIRST_NAME_COL = "first_name"
        val LAST_NAME_COL = "last_name"
        val REWARDS_COL = 1
    }


}