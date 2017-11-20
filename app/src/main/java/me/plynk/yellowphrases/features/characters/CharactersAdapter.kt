package me.plynk.yellowphrases.features.characters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*
import me.plynk.yellowphrases.R
import me.plynk.yellowphrases.model.Character

class CharactersAdapter(val characters: List<Character>, var clickListener: ClickListener): RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(character: Character){
            with(character){
                itemView.txtName.text = fullName

                Picasso.with(itemView.context)
                        .load(picture)
                        .placeholder(R.drawable.donut_drawing_150)
                        .into(itemView.imgCharacter)

                itemView.setOnClickListener {
                    clickListener.onCharacterClick(character)
                }
            }
        }
    }

    interface ClickListener{
        fun onCharacterClick(character: Character)
    }
}