package io.qwe1991.test1.data

import io.qwe1991.test1.data.db.entity.MovieEntity

class MovieEntityConverter {
    fun convert(from: ResultsItem): MovieEntity {
        return MovieEntity(
            from.id ?: 0,
            from.title ?: "",
            from.popularity ?: 0.0,
            from.poster_path,
            from.vote_average ?: 0.0,
            from.adult ?: true,
            from.release_date,
            from.overview ?: ""
        )
    }

    fun convertList(from: List<ResultsItem>): List<MovieEntity> {
        return from.map { this.convert(it) }
    }
}
