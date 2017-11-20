package me.plynk.yellowphrases.features.characters

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_characters.*
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.extensions.inflate
import me.plynk.yellowphrases.features.base.BaseFragment
import me.plynk.yellowphrases.model.Character
import javax.inject.Inject

class CharactersFragment: BaseFragment<CharactersContract.View, CharactersPresenter>(), CharactersContract.View {

    @Inject override lateinit var presenter: CharactersPresenter

    companion object {
        fun newInstance(): CharactersFragment = CharactersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_characters)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity?.title = getString(R.string.app_name)
        presenter.loadCharacters()
    }

    override fun showCharacters(characters: List<Character>) {
        lstCharacters.setHasFixedSize(true)
        lstCharacters.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        lstCharacters.adapter = CharactersAdapter(characters, activity as CharactersAdapter.ClickListener)
    }


}