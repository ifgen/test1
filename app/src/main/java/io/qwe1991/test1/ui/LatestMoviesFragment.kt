package io.qwe1991.test1.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import io.qwe1991.test1.MainActivity
import io.qwe1991.test1.R
import io.qwe1991.test1.base.BaseBindingFragment
import io.qwe1991.test1.databinding.FragmentLatestMoviesBinding
import kotlinx.android.synthetic.main.fragment_latest_movies.*


class LatestMoviesFragment :
    BaseBindingFragment<FragmentLatestMoviesBinding, LatestMoviesViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_fragment_latest_movies)

        viewModel.loadMoviesList()

        val adapter = LatestMoviesAdapter()

        viewModel.lengthOfPostsList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        latestMoviesRv.adapter = adapter

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getContentView(): Int =
        R.layout.fragment_latest_movies

    override fun getViewModelClass(): Class<LatestMoviesViewModel> =
        LatestMoviesViewModel::class.java

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_latest_movies, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }
}
