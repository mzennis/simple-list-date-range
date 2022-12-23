package com.pandaways.simplelist.adapter.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pandaways.simplelist.databinding.ItemPersonBinding
import com.pandaways.simplelist.model.PersonUiModel
import java.text.DateFormat

class PersonViewHolder(
    private val binding: ItemPersonBinding,
    private val listener: Listener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PersonUiModel) {
        binding.ivAvatar.setImageDrawable(ContextCompat.getDrawable(binding.root.context, item.imageRes))
        binding.tvName.text = item.name
        binding.tvBirthDate.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(item.birthDate)
        binding.tvSex.text = item.sex
        binding.tvAddress.text = item.address

        binding.root.setOnClickListener {
            listener.onItemClicked(item)
        }
    }

    interface Listener {
        fun onItemClicked(item: PersonUiModel)
    }
}