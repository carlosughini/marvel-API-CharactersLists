package com.carlosughini.appmarvel.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.carlosughini.appmarvel.R
import com.carlosughini.appmarvel.extensions.load
import com.carlosughini.appmarvel.models.entity.Characters
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter : PagedListAdapter<Characters, CharactersAdapter.VH>(characterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character = getItem(position)
        holder.txtName.text = character?.name
        holder.imgThumbnail.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.textCharacters
    }

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Characters>() {
            override fun areItemsTheSame(old: Characters, aNew: Characters): Boolean {
                return old.id == aNew.id

            }

            override fun areContentsTheSame(old: Characters, aNew: Characters): Boolean {
                return old == aNew
            }

        }
    }
}