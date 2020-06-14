package io.qwe1991.test1.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseViewModel(app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by kodein()
}

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }
