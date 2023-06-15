package com.myfitness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myfitness.model.UserDataList

@Database(entities = [UserDataList::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun DoaService(): DoaService

    companion object {

        var dbObject: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            synchronized(this) {
                if (dbObject == null) {
                    dbObject = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "userListDB"
                    )
                        .build()
                }
            }
            return dbObject!!
        }
    }

}