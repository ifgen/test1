package io.qwe1991.test1.ui.movie_detail

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.qwe1991.test1.MainActivity
import io.qwe1991.test1.R
import io.qwe1991.test1.base.BaseBindingFragment
import io.qwe1991.test1.databinding.FragmentMovieDetailBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : BaseBindingFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun getContentView(): Int =
        R.layout.fragment_movie_detail

    override fun getViewModelClass(): Class<MovieDetailViewModel> =
        MovieDetailViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = args.movie.originalTitle

        binding.movie = args.movie

        Glide
            .with(binding.root.context)
            .load("https://image.tmdb.org/t/p/w342" + args.movie.posterPath)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(appCompatImageViewBg)

        Glide
            .with(binding.root.context)
            .load("https://image.tmdb.org/t/p/w342" + args.movie.posterPath)
            .into(appCompatImageView)
    }
}
