package io.qwe1991.test1.data

import io.qwe1991.test1.BuildConfig
import kotlinx.coroutines.Deferred

class TmdbRepository(private val tmdbApiService: TmdbApiService) {

    fun getLatestMovies(page: Int = 1): Deferred<Response> {
        return tmdbApiService.getNowPlaying(BuildConfig.TMDB_API_KEY, page)
    }
}
