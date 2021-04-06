package com.example.modulotech.ui.device.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.modulotech.db.device.DeviceDbModel

class DeviceAdapter : ListAdapter<DeviceDbModel, RecyclerView.ViewHolder>(DEVICE_COMPARATOR) {
    companion object {
        private val DEVICE_COMPARATOR = object : DiffUtil.ItemCallback<DeviceDbModel>() {
            override fun areItemsTheSame(oldItem: DeviceDbModel, newItem: DeviceDbModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: DeviceDbModel,
                newItem: DeviceDbModel
            ): Boolean =
                oldItem.equalsContent(newItem)
        }
    }

    var clickListener: ((DeviceDbModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DeviceListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as DeviceListViewHolder).bind(repoItem)
            holder.itemView.setOnClickListener { clickListener?.invoke(repoItem) }
        }
    }
}
