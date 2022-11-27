package com.example.foodmap.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import om.example.foodmap.Repository.FoodReviewItem

// Annotates class to be a Room Database with a table (entity) of the ToDoItem class
@Database(entities = arrayOf(FoodReviewItem::class), version = 1, exportSchema = false)
public abstract class FoodReviewListRoomDatabase : RoomDatabase() {

    abstract fun toDoListDao(): FoodReviewListDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FoodReviewListRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FoodReviewListRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodReviewListRoomDatabase::class.java,
                    "todolist_database"
                )
                    .addCallback(ToDoListDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class ToDoListDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.toDoListDao())
                }
            }
        }

        suspend fun populateDatabase(toDoListDao: FoodReviewListDao) {
            // Delete all content here.
            toDoListDao.deleteAll()

            // Add sample words.
            val toDoItem = FoodReviewItem(null, "Assignment 2", "Complete Assignment 2", 0, 0)
            toDoListDao.insert(toDoItem)
        }
    }

}