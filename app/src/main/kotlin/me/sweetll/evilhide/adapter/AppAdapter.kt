package me.sweetll.evilhide.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.sweetll.evilhide.R
import me.sweetll.evilhide.databinding.ItemAppBinding
import me.sweetll.evilhide.model.AppInfo
import me.sweetll.evilhide.viewmodel.AppViewModel

class AppAdapter(var data: MutableList<AppInfo>) : androidx.recyclerview.widget.RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAppBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_app,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = data[position]
        holder.bindAppInfo(appInfo)
    }

    override fun getItemCount(): Int = data.size

    fun setNewData(newData: MutableList<AppInfo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemAppBinding): androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bindAppInfo(appInfo: AppInfo) {
            binding.starBtn.setOnFavoriteChangeListener{
                button, favorite ->
                if (button.isPressed) {
                    binding.viewModel?.onFavoriteChange(favorite)
                }
            }
            binding.switchBtn.setOnCheckedChangeListener {
                button, check ->
                if (button.isPressed) {
                    binding.viewModel?.onCheckChange(check)
                }
            }
            binding.switchBtn.isChecked = appInfo.hidden
            binding.viewModel = AppViewModel(itemView.context, appInfo)
        }
    }

}