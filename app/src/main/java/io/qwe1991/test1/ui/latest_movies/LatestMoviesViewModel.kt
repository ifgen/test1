package io.qwe1991.test1.ui.latest_movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.qwe1991.test1.base.BaseViewModel
import io.qwe1991.test1.base.SingleLiveEvent
import io.qwe1991.test1.data.MovieEntityConverter
import io.qwe1991.test1.data.TmdbRepository
import io.qwe1991.test1.data.db.MovieDao
import io.qwe1991.test1.data.db.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.generic.instance

class LatestMoviesViewModel(app: Application) : BaseViewModel(app) {

    private val tmdbRepository: TmdbRepository by instance<TmdbRepository>()
    private val movieDao: MovieDao by instance<MovieDao>()
    private val movieEntityConverter: MovieEntityConverter by instance<MovieEntityConverter>()

    val lengthOfPostsList: LiveData<PagedList<MovieEntity>> = movieDao.getAll1().toLiveData(
        pageSize = 10,
        initialLoadKey = 20,
        boundaryCallback = object: PagedList.BoundaryCallback<MovieEntity>() {
            private var page = 1

            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()

                Log.e("!", "onZeroItemsLoaded")

                page = 1
                loadMoviesList(page)
            }

            override fun onItemAtFrontLoaded(itemAtFront: MovieEntity) {
                super.onItemAtFrontLoaded(itemAtFront)

                Log.e("!", "onItemAtFrontLoaded" + itemAtFront.originalTitle)
            }

            override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) {
                super.onItemAtEndLoaded(itemAtEnd)

                Log.e("!", "onItemAtEndLoaded" + itemAtEnd.originalTitle)

                page++
                loadMoviesList(page)
            }
        }
    )
    val isLoading = MutableLiveData<Boolean>(false)
    val error = SingleLiveEvent<Exception>()


    fun loadMoviesList(pageToLoad: Int = 1) {
        isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = tmdbRepository.getLatestMovies(pageToLoad).await()

                    if (pageToLoad == 1) {
                        movieDao.deleteAll()
                    }

                    movieDao.insertAll(movieEntityConverter.convertList(result.results))
                } catch (e: Exception) {
                    error.postValue(e)
                }
            }
        }
    }
}
