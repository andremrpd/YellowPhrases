package me.plynk.yellowphrases.features.phrases

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_phrases.*
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.extensions.inflate
import me.plynk.yellowphrases.features.base.BaseFragment
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import javax.inject.Inject

class PhrasesFragment: BaseFragment<PhrasesContract.View, PhrasesPresenter>(), PhrasesContract.View, PhrasesAdapter.ClickListener {

    @Inject override lateinit var presenter: PhrasesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_phrases)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            val character = arguments?.getSerializable(ARG_CHARACTER) as Character

            txtName.text = character.fullName
            Picasso.with(baseActivity)
                    .load(character.picture)
                    .placeholder(R.drawable.donut_drawing_150)
                    .into(imgCharacter)


            presenter.loadPhrases(character)
        }
    }

    companion object {

        private val ARG_CHARACTER = "character"

        fun newInstance(character: Character): PhrasesFragment {
            val args = Bundle()
            args.putSerializable(ARG_CHARACTER, character)

            val fragment = PhrasesFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun showPhrases(phrases: List<Phrase>) {
        lstPhrases.layoutManager = LinearLayoutManager(activity)
        lstPhrases.setHasFixedSize(true)
        lstPhrases.adapter = PhrasesAdapter(phrases, this)
    }

    override fun onSaveClick(phraseItem: PhrasesAdapter.PhraseItem) {
        presenter.savePhrase(phraseItem)
    }


}
