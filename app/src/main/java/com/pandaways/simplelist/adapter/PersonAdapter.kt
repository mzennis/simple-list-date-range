package com.pandaways.simplelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandaways.simplelist.adapter.viewholder.PersonViewHolder
import com.pandaways.simplelist.databinding.ItemPersonBinding
import com.pandaways.simplelist.model.PersonUiModel

class PersonAdapter(
    private val listener: PersonViewHolder.Listener
): RecyclerView.Adapter<PersonViewHolder>() {

    private val mItemList = mutableListOf<PersonUiModel>()

    fun setItems(itemList: List<PersonUiModel>) {
        mItemList.clear()
        mItemList.addAll(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(mItemList[position])
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }
}