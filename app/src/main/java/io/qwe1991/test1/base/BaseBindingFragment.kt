package io.qwe1991.test1.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

abstract class BaseBindingFragment<VB : ViewDataBinding, VM : ViewModel> : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModeFactory: ViewModelProvider.Factory by instance<ViewModelProvider.Factory>()

    protected open val vmInitStrategy: ViewModelInitStrategy = ViewModelInitStrategy.FragmentVM

    protected lateinit var viewModel: VM

    protected lateinit var binding: VB


    @LayoutRes
    protected abstract fun getContentView(): Int

    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val vid = getContentView()
        return if (vid != View.NO_ID) {
            val view = inflater.inflate(vid, container, false)
            binding = DataBindingUtil.bind(view)!!
            binding.lifecycleOwner = this
            view
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = when (vmInitStrategy) {
            is ViewModelInitStrategy.FragmentVM -> {
                ViewModelProvider(this@BaseBindingFragment, viewModeFactory).get(getViewModelClass())
            }
            is ViewModelInitStrategy.ActivityVM -> {
                ViewModelProvider(requireActivity(), viewModeFactory).get(getViewModelClass())
            }
        }
    }

    sealed class ViewModelInitStrategy {
        object FragmentVM : ViewModelInitStrategy()
        object ActivityVM : ViewModelInitStrategy()
    }
}
