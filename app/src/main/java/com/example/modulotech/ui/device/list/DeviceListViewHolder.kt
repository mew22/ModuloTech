package com.example.modulotech.ui.device.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modulotech.R
import com.example.modulotech.db.device.DeviceDbModel
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel

class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): DeviceListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.device_list_item, parent, false)
            return DeviceListViewHolder(view)
        }
    }

    private val mTvDeviceName = itemView.findViewById<TextView>(R.id.tv_device_name)
    private val mIvDeviceLogo = itemView.findViewById<AppCompatImageView>(R.id.iv_device_icon)

    fun bind(repoItem: DeviceDbModel) {
        mTvDeviceName.text = repoItem.name
        mIvDeviceLogo.setImageResource(
            when (repoItem) {
                is LightDbModel -> R.drawable.ic_light
                is HeaterDbModel -> R.drawable.ic_heat
                is RollerShutterDbModel -> R.drawable.ic_window
            }
        )
    }
}
