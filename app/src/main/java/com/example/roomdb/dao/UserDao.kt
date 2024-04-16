package com.example.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomdb.models.User

@Dao
interface UserDao
{
    @Insert
   fun insert(note:User)

    @Update
   fun update(note: User)

   @Delete
   fun delete(note: User)

    @Query("SELECT * FROM userData WHERE name LIKE :search OR email LIKE :search OR phnNumber LIKE :search")
    fun makeQuery(search: String): List<User>


    @Query("SELECT COUNT(*) FROM userData")
    fun getUserCount(): Int

}