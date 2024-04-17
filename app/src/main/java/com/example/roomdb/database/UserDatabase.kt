package com.example.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdb.dao.UserDao
import com.example.roomdb.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [User::class], version = 2)
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
            val state = SQLCipherUtils.getDatabaseState(ctx = cxt,"truecaller")

            if(state==SQLCipherUtils.State.UNENCRYPTED){
                SQLCipherUtils.encrypt(cxt,"truecaller","vamsi".toCharArray())
            }

            return Room.databaseBuilder(cxt.applicationContext, UserDatabase::class.java, "truecaller")
                .addCallback(roomCallback)
                .addMigrations(MIGRATION_1_2)
                .openHelperFactory(factory)
                .build()
        }

        private fun checkIfDatabaseNeedsPopulation(db: UserDatabase) {
            val userDao = db.userDao()
            if (userDao.getUserCount() == 0) {
                populateDatabase(userDao)
            }
        }

        private val MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userData ADD COLUMN age INTEGER DEFAULT 0")
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
                        name = "shaji",
                        email = "shaji1@gmail.com",
                        phnNumber = "8885965414",
                        age = 0
                    )
                )
                userDao.insert(
                    User(
                        name = "vamsi2",
                        email = "shaji2@gmail.com",
                        phnNumber = "45757575",
                        age = 0
                    )
                )
                userDao.insert(
                    User(
                        name = "shaji3",
                        email = "shaji3@gmail.com",
                        phnNumber = "545475474757",
                        age = 0
                    )
                )
                userDao.insert(
                    User(
                        name = "shaji4",
                        email = "shaji4@gmail.com",
                        phnNumber = "8398958985",
                        age = 0
                    )
                )
            }
        }


    }

}