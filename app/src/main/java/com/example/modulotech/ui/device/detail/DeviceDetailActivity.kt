package com.example.modulotech.ui.device.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.modulotech.R
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.ui.MainActivity
import com.example.modulotech.ui.device.detail.heater.HeaterFragment
import com.example.modulotech.ui.device.detail.light.LightFragment
import com.example.modulotech.ui.device.detail.rollershutter.RollerShutterFragment

class DeviceDetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_ITEM_ID = "arg_item_id"
        const val ARG_ITEM_TYPE = "arg_item_type"
        const val TAG = "DeviceDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)

        if (savedInstanceState == null) {
            val id = intent.getIntExtra(ARG_ITEM_ID, 0)
            val type = intent.getStringExtra(ARG_ITEM_TYPE)
            when (type) {
                LightDbModel.DEVICE_TYPE -> {
                    LightFragment()
                }
                HeaterDbModel.DEVICE_TYPE -> {
                    HeaterFragment()
                }
                RollerShutterDbModel.DEVICE_TYPE -> {
                    RollerShutterFragment()
                }
                else -> {
                    Log.w(TAG, "Unknown device type")
                    LightFragment()
                }
            }.apply {
                arguments = Bundle().apply {
                    putInt(
                        ARG_ITEM_ID,
                        id
                    )
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.detail_frame, this)
                    .commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
