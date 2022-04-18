package com.adl.mobileroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adl.mobileroom.dao.UserDao
import com.adl.mobileroom.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabases: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        var instance:UserDatabases?=null

        private var INSTANCE: UserDatabases? = null
        @Synchronized
        fun getInstance(context: Context): UserDatabases {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): UserDatabases {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabases::class.java,
                "user_db"
            )
                .build()
        }
    }
}