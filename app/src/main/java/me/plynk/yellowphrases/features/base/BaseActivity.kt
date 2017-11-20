package me.plynk.yellowphrases.features.base

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_phrases.*
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.extensions.dismissProgressBar
import me.plynk.yellowphrases.extensions.showProgressBar

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun showErrorMsg(message: String?) {
        val snackbar = Snackbar.make(findViewById(R.id.container), message ?: "Error", Snackbar.LENGTH_LONG)

        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.primary_light))

        snackbar.show()
    }

    override fun showLoading() {
        mainLayout.showProgressBar()
    }

    override fun dismissLoading() {
        mainLayout.dismissProgressBar()
    }

    fun setToolbarTitle(newTitle: String){
        title = newTitle
    }

    fun resetTitle(){
        setToolbarTitle(getString(R.string.app_name))
    }

}
