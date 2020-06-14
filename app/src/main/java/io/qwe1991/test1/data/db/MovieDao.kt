package io.qwe1991.test1.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.qwe1991.test1.data.db.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getAll1(): DataSource.Factory<Int, MovieEntity>

    @Insert
    fun insertAll(vararg movies: MovieEntity)

    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("DELETE FROM movie")
    fun deleteAll()
}
