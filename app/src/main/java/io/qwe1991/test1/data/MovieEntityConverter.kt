package io.qwe1991.test1.data

import io.qwe1991.test1.data.db.entity.MovieEntity

class MovieEntityConverter {
    fun convert(from: ResultsItem): MovieEntity {
        return MovieEntity(
            from.id ?: 0,
            from.title ?: "",
            from.popularity ?: 0.0,
            from.poster_path
        )
    }

    fun convertList(from: List<ResultsItem>): List<MovieEntity> {
        return from.map { this.convert(it) }
    }
}
