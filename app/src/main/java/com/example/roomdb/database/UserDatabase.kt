package com.example.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdb.dao.UserDao
import com.example.roomdb.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase()
{

    abstract fun userDao() : UserDao

    companion object
    {
        private var instance:UserDatabase? = null

        fun getInstance(cxt: Context): UserDatabase {
            return instance ?: buildDatabase(cxt).also { instance = it }
        }

        private fun buildDatabase(cxt: Context): UserDatabase {
            val byteArray = SQLiteDatabase.getBytes("vamsi".toCharArray())
            val factory = SupportFactory(byteArray)
            return Room.databaseBuilder(cxt.applicationContext, UserDatabase::class.java, "truecaller")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build()
        }

        private fun checkIfDatabaseNeedsPopulation(db: UserDatabase) {
            val userDao = db.userDao()
            if (userDao.getUserCount() == 0) {
                populateDatabase(userDao)
            }
        }


        //callback function called
        private val roomCallback = object :Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase)
            {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) {
                    checkIfDatabaseNeedsPopulation(instance!!)
                }

            }
        }

        //populate default database
        private fun populateDatabase(userDao:UserDao) {
            if (userDao.getUserCount() == 0) {
                userDao.insert(
                    User(
                        name = "vamsi",
                        email = "vamsip140@gmail.com",
                        phnNumber = "8885965414"
                    )
                )
                userDao.insert(
                    User(
                        name = "vamsi2",
                        email = "vamsip141@gmail.com",
                        phnNumber = "8885965415"
                    )
                )
                userDao.insert(
                    User(
                        name = "vamsi3",
                        email = "vamsip142@gmail.com",
                        phnNumber = "8885965416"
                    )
                )
                userDao.insert(
                    User(
                        name = "vamsi4",
                        email = "vamsip143@gmail.com",
                        phnNumber = "8885965417"
                    )
                )
            }
        }


    }

}