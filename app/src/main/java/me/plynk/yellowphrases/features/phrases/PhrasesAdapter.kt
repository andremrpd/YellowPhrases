package me.plynk.yellowphrases.features.phrases

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.model.Phrase
import kotlinx.android.synthetic.main.item_phrase.view.*
import me.plynk.yellowphrases.delegate.LoadingDelegate

class PhrasesAdapter(private val phrases: List<Phrase>, private val clickListener: ClickListener) : RecyclerView.Adapter<PhrasesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_phrase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(phrases[position])
    }

    override fun getItemCount(): Int = phrases.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), PhraseItem {

        override var phrase: Phrase? = null

        fun bind(phrase: Phrase){
            this.phrase = phrase
            itemView.txtPhrase.text = phrase.phrase
            itemView.btnSave.tag = false
            itemView.btnSave.setOnClickListener{
                if (!(itemView.btnSave.tag as Boolean)){
                    clickListener.onSaveClick( this)
                }else{
                    setSaved(false)
                }
            }
        }

        override fun showLoading(){
            itemView.btnSave.visibility = View.INVISIBLE
            itemView.progSave.visibility = View.VISIBLE
        }

        override fun dismissLoading(){
            itemView.btnSave.visibility = View.VISIBLE
            itemView.progSave.visibility = View.GONE
        }

        override fun setSaved(saved: Boolean) {
            itemView.btnSave.tag = saved
            if (saved){
                itemView.btnSave.setImageResource(R.drawable.ic_star_on)
            }else{
                itemView.btnSave.setImageResource(R.drawable.ic_star_off)
            }
        }
    }

    interface PhraseItem: LoadingDelegate{
        var phrase: Phrase?
        fun setSaved(saved: Boolean)
    }

    interface ClickListener{
        fun onSaveClick(phraseItem: PhraseItem)
    }
}
