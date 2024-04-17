package com.example.roomdb.database

import android.content.Context
import android.content.SharedPreferences.Editor
import android.text.Editable
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteStatement
import java.io.File

object SQLCipherUtils {
    enum class State {
        ENCRYPTED, UNENCRYPTED, DOES_NOT_EXIST
    }

    fun getDatabaseState(ctx: Context, databaseName: String): State {
        SQLiteDatabase.loadLibs(ctx)
        return getDatabaseState(ctx.getDatabasePath(databaseName))
    }

    fun getDatabaseState(filePath: File): State {
        if (filePath.exists()) {
            var db: SQLiteDatabase? = null
            try {
                db = SQLiteDatabase.openDatabase(
                    filePath.absolutePath,
                    "",
                    null,
                    SQLiteDatabase.OPEN_READONLY
                )
                val version = db.version
                println("Db version $version")
                return State.UNENCRYPTED
            } catch (e: Exception) {
                return State.ENCRYPTED
                e.printStackTrace()
            } finally {
                db?.close()
            }
        }
        return State.DOES_NOT_EXIST;
    }

    fun encrypt(ctx: Context, dbName: String, pass: String) {
        val passCharArray: CharArray = pass.toCharArray()
        encrypt(ctx, dbName, passCharArray)
    }

    fun encrypt(ctx: Context, dbName: String, charpassArray: CharArray) {
        val dbFilePath = ctx.getDatabasePath(dbName)
        val factory: ByteArray = SQLiteDatabase.getBytes(charpassArray)
        SQLiteDatabase.loadLibs(ctx)
        if (dbFilePath.exists()){
            val tempFile = File.createTempFile("sqlcipherutils","tmp",ctx.cacheDir)
            val normalDb = SQLiteDatabase.openDatabase(dbFilePath.absolutePath,"",null,SQLiteDatabase.OPEN_READWRITE)
            val dbVersion = normalDb.version
            normalDb.close()

            val tempDbOpened: SQLiteDatabase = SQLiteDatabase.openDatabase(tempFile.absolutePath,charpassArray, null,SQLiteDatabase.OPEN_READWRITE,null,null)
            val pathe= dbFilePath.absolutePath
            val sqlStatement = tempDbOpened.compileStatement("ATTACH DATABASE '$pathe' AS plaintext KEY '';")
//            sqlStatement.bindString(1,dbFilePath.absolutePath)
            sqlStatement.execute()
            println("sample data")

            tempDbOpened.rawExecSQL("SELECT sqlcipher_export('main', 'plaintext')");
            tempDbOpened.rawExecSQL("DETACH DATABASE plaintext");
            tempDbOpened.setVersion(dbVersion);
            sqlStatement.close();
            tempDbOpened.close();

            dbFilePath.delete();
            tempFile.renameTo(dbFilePath);


        }else{
            println("Database file not exists")
        }
    }


}