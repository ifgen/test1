package io.qwe1991.test1.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("/3/movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Deferred<Response>
}
