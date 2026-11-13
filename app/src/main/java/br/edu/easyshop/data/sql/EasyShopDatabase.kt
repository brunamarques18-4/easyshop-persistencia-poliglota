package br.edu.easyshop.data.sql

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CustomerEntity::class], version = 1, exportSchema = false)
abstract class EasyShopDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    companion object {
        fun create(context: Context): EasyShopDatabase = Room.databaseBuilder(
            context.applicationContext, EasyShopDatabase::class.java, "easyshop-customers.db"
        ).build()
    }
}
