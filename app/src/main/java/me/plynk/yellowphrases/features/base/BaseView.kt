package me.plynk.yellowphrases.features.base

import android.view.View
import me.plynk.yellowphrases.delegate.LoadingDelegate

interface BaseView: LoadingDelegate {

    fun showErrorMsg(message: String?)
    fun showTryAgainError(message: String?, onClickListener: View.OnClickListener?)

}
