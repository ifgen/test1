package io.qwe1991.test1.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat

@Entity(tableName = "movie")
@Parcelize
data class MovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "score") val score: Double,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "overview") val overview: String
) : Parcelable {
    fun getRating(): String {
        return if (adult) "R" else "G"
    }

    fun getDateFormatted(): String {
        val formatParsed = SimpleDateFormat("yyyy-MM-dd")
        val parsed = formatParsed.parse(releaseDate)

        return SimpleDateFormat("MMM dd, yyyy").format(parsed)
    }
}
