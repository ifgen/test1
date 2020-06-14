package io.qwe1991.test1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.qwe1991.test1.data.db.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
