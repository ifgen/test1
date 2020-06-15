package io.qwe1991.test1

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.qwe1991.test1.base.KodeinViewModelFactory
import io.qwe1991.test1.data.MovieEntityConverter
import io.qwe1991.test1.data.TmdbApiService
import io.qwe1991.test1.data.TmdbRepository
import io.qwe1991.test1.data.db.AppDatabase
import io.qwe1991.test1.data.db.MovieDao
import io.qwe1991.test1.ui.latest_movies.LatestMoviesViewModel
import io.qwe1991.test1.ui.movie_detail.MovieDetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {

        // api

        bind<Moshi>() with singleton {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val httpBuilder = OkHttpClient.Builder()
            @Suppress("ConstantConditionIf")
            if (BuildConfig.ENABLE_LOGGING) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpBuilder.interceptors()
                    .add(httpLoggingInterceptor)
            }

//            httpBuilder.addInterceptor(ApiHeaderInterceptor(instance(), instance()))
            httpBuilder.build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .client(instance<OkHttpClient>())
                .baseUrl("https://api.themoviedb.org") //TODO
                .addConverterFactory(MoshiConverterFactory.create(instance<Moshi>()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        bind<TmdbApiService>() with singleton {
            instance<Retrofit>().create(TmdbApiService::class.java)
        }

        // view models
        bind<ViewModelProvider.Factory>() with singleton { KodeinViewModelFactory(kodein) }

        bind<LatestMoviesViewModel>() with singleton {
            LatestMoviesViewModel(
                this@App
            )
        }
        bind<MovieDetailViewModel>() with singleton {
            MovieDetailViewModel(
                this@App
            )
        }

        bind<TmdbRepository>() with singleton { TmdbRepository(instance<TmdbApiService>()) }

        bind<AppDatabase>() with singleton {
            Room.databaseBuilder(
                this@App.applicationContext,
                AppDatabase::class.java, "test_db"
            ).build()
        }

        bind<MovieDao>() with singleton { instance<AppDatabase>().movieDao() }
        bind<MovieEntityConverter>() with singleton { MovieEntityConverter() }
    }
}
