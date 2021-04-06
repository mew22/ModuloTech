package com.example.modulotech.ui.device.detail.heater

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.modulotech.R
import com.example.modulotech.db.device.DeviceMode
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.di.Injection
import com.example.modulotech.ui.device.detail.DeviceDetailActivity.Companion.ARG_ITEM_ID
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_detail_heater.et_profile_device_name
import kotlinx.android.synthetic.main.fragment_detail_heater.profile_heater_mode_switch
import kotlinx.android.synthetic.main.fragment_detail_heater.profile_heater_temperature_slider
import kotlinx.android.synthetic.main.profile_banner.iv_profile_picture

class HeaterFragment : Fragment() {

    companion object {
        const val HEATER_TEMPERATURE_MAX = 28
        const val COLOR_SATURATION_DIVIDER = HEATER_TEMPERATURE_MAX
        const val COLOR_SATURATION_NULL = 0f
        const val DEFAULT_DEVICE_ID = 0
    }

    private val mHeaterDetailViewModel: HeaterDetailViewModel by viewModels {
        Injection.provideHeaterDetailViewModelFactory(requireActivity().applicationContext)
    }

    private var mDeviceId = DEFAULT_DEVICE_ID

    private val mDeviceNameChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Nothing to do
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (mHeaterDetailViewModel.checkDeviceNameConformity(s.toString())) {
                et_profile_device_name.error = null
            } else {
                et_profile_device_name.error = getString(R.string.profile_field_error)
            }
        }

        override fun afterTextChanged(s: Editable) {
            // Nothing to do
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_detail_heater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                mDeviceId = it.getInt(ARG_ITEM_ID)
                mHeaterDetailViewModel.getHeaterById(mDeviceId).removeObservers(viewLifecycleOwner)
                mHeaterDetailViewModel.getHeaterById(mDeviceId)
                    .observe(viewLifecycleOwner) { heater ->
                        heater?.let { heaterNotNull ->
                            setupViews(heaterNotNull)
                        }
                    }
            } else {
                requireActivity().finish()
            }
        }
        setupListeners()
    }

    private fun setupListeners() {
        profile_heater_temperature_slider.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Do nothing
            }

            override fun onStopTrackingTouch(slider: Slider) {
                mHeaterDetailViewModel.setTemperature(mDeviceId, slider.value)
            }
        })
        profile_heater_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            mHeaterDetailViewModel.turnDevice(mDeviceId, isChecked)
        }
        et_profile_device_name.editText?.addTextChangedListener(mDeviceNameChangedListener)
        et_profile_device_name.editText?.setOnEditorActionListener { textView, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mHeaterDetailViewModel
                            .checkDeviceNameConformity(textView.text.toString())) {
                        mHeaterDetailViewModel.saveDeviceName(mDeviceId, textView.text.toString())
                        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                                as InputMethodManager).hideSoftInputFromWindow(
                            textView.windowToken,
                            0
                        )
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun cleanListeners() {
        et_profile_device_name.editText?.removeTextChangedListener(mDeviceNameChangedListener)
        et_profile_device_name.editText?.setOnEditorActionListener(null)
        profile_heater_temperature_slider.clearOnSliderTouchListeners()
        profile_heater_mode_switch.setOnCheckedChangeListener(null)
    }

    private fun setupViews(heater: HeaterDbModel) {
        cleanListeners()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = heater.name

        profile_heater_temperature_slider.value = heater.temperature
        profile_heater_mode_switch.isChecked = heater.mode is DeviceMode.DeviceOn
        et_profile_device_name.editText?.setText(heater.name)

        iv_profile_picture.setImageResource(R.drawable.ic_heat_profile)
        iv_profile_picture.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
            setSaturation(
                if (heater.mode is DeviceMode.DeviceOn) {
                    heater.temperature / COLOR_SATURATION_DIVIDER
                } else {
                    COLOR_SATURATION_NULL
                }
            )
        })

        setupListeners()
    }
}
