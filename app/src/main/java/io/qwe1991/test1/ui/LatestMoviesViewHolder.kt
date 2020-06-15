package io.qwe1991.test1.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.qwe1991.test1.data.db.entity.MovieEntity
import io.qwe1991.test1.databinding.ItemLatestMovieBinding
import kotlinx.android.synthetic.main.item_latest_movie.view.*

class LatestMoviesViewHolder constructor(
    private val binding: ItemLatestMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieEntity?, clickCallback: (movie: MovieEntity) -> Unit) {
        binding.movie = item
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            item?.let{ clickCallback(it) }
        }

        if (item?.posterPath != null) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide
                .with(binding.root.context)
                //TODO:
                .load("https://image.tmdb.org/t/p/w342" + item.posterPath)
                .centerCrop()
                .transform(RoundedCorners(16))

                .placeholder(circularProgressDrawable)
                .into(binding.root.latestMovieImage)
        }
    }
}
