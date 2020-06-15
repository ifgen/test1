package io.qwe1991.test1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import io.qwe1991.test1.data.db.entity.MovieEntity
import io.qwe1991.test1.databinding.ItemLatestMovieBinding

class LatestMoviesAdapter(
    private val clickCallback: (movie: MovieEntity) -> Unit
) :
    PagedListAdapter<MovieEntity, LatestMoviesViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: LatestMoviesViewHolder, position: Int) {
        val movie: MovieEntity? = getItem(position)

        holder.bind(movie, clickCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesViewHolder {
        return LatestMoviesViewHolder(
            ItemLatestMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<MovieEntity>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldConcert: MovieEntity,
                newConcert: MovieEntity
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: MovieEntity,
                newConcert: MovieEntity
            ) = oldConcert == newConcert
        }
    }
}
