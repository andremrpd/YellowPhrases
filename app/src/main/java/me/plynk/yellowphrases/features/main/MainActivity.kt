package me.plynk.yellowphrases.features.main

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_error.*
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.extensions.dismissProgressBar
import me.plynk.yellowphrases.extensions.showProgressBar
import me.plynk.yellowphrases.features.base.BaseActivity
import me.plynk.yellowphrases.features.characters.CharactersAdapter
import me.plynk.yellowphrases.features.characters.CharactersFragment
import me.plynk.yellowphrases.features.phrases.PhrasesFragment
import me.plynk.yellowphrases.model.Character

class MainActivity : BaseActivity(), CharactersAdapter.ClickListener, FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener(this)
        shouldDisplayHomeUp()

        if (savedInstanceState == null) {
            setupFragment()
        }
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharactersFragment.newInstance())
                .commit()
    }

    override fun onCharacterClick(character: Character) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhrasesFragment.newInstance(character))
                .addToBackStack(null)
                .commit()
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    fun shouldDisplayHomeUp() {
        val canback = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canback)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    override fun showTryAgainError(message: String?, onClickListener: View.OnClickListener?) {
        incError.visibility = View.VISIBLE
        container.visibility = View.GONE
        txtErrorDetail.text = message

        btnTryAgain.setOnClickListener(onClickListener)
    }
    override fun showLoading() {
        incError.visibility = View.GONE
        container.visibility = View.VISIBLE
        mainLayout.showProgressBar()
    }

    override fun dismissLoading() {
        mainLayout.dismissProgressBar()
    }

}
