package com.example.modulotech.ui.device.detail.light

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
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.di.Injection
import com.example.modulotech.ui.device.detail.DeviceDetailActivity.Companion.ARG_ITEM_ID
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_detail_light.et_profile_device_name
import kotlinx.android.synthetic.main.fragment_detail_light.profile_light_intensity_slider
import kotlinx.android.synthetic.main.fragment_detail_light.profile_light_mode_switch
import kotlinx.android.synthetic.main.profile_banner.iv_profile_picture

class LightFragment : Fragment() {

    companion object {
        const val COLOR_SATURATION_DIVIDER = 100f
        const val COLOR_SATURATION_NULL = 0f
        const val DEFAULT_DEVICE_ID = 0
    }

    private val mLightDetailViewModel: LightDetailViewModel by viewModels {
        Injection.provideLightDetailViewModelFactory(requireActivity().applicationContext)
    }

    private var mDeviceId = DEFAULT_DEVICE_ID

    private val mDeviceNameChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Nothing to do
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (mLightDetailViewModel.checkDeviceNameConformity(s.toString())) {
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
    ): View =
        inflater.inflate(R.layout.fragment_detail_light, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                mDeviceId = it.getInt(ARG_ITEM_ID)
                mLightDetailViewModel.getLightById(mDeviceId).removeObservers(viewLifecycleOwner)
                mLightDetailViewModel.getLightById(mDeviceId)
                    .observe(viewLifecycleOwner) { light ->
                        light?.let { lightNotNull ->
                            setupViews(lightNotNull)
                        }
                    }
            } else {
                requireActivity().finish()
            }
        }
        setupListeners()
    }

    private fun setupListeners() {
        profile_light_intensity_slider.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Do nothing
            }

            override fun onStopTrackingTouch(slider: Slider) {
                mLightDetailViewModel.setIntensity(mDeviceId, slider.value)
            }
        })
        profile_light_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            mLightDetailViewModel.turnDevice(mDeviceId, isChecked)
        }
        et_profile_device_name.editText?.addTextChangedListener(mDeviceNameChangedListener)
        et_profile_device_name.editText?.setOnEditorActionListener { textView, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mLightDetailViewModel.checkDeviceNameConformity(textView.text.toString())) {
                        mLightDetailViewModel.saveDeviceName(mDeviceId, textView.text.toString())
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
        profile_light_intensity_slider.clearOnSliderTouchListeners()
        profile_light_mode_switch.setOnCheckedChangeListener(null)
    }

    private fun setupViews(light: LightDbModel) {
        cleanListeners()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = light.name

        profile_light_intensity_slider.value = light.intensity.toFloat()
        profile_light_mode_switch.isChecked = light.mode is DeviceMode.DeviceOn
        et_profile_device_name.editText?.setText(light.name)

        iv_profile_picture.setImageResource(R.drawable.ic_light_profile)
        iv_profile_picture.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
            setSaturation(
                if (light.mode is DeviceMode.DeviceOn) {
                    light.intensity / COLOR_SATURATION_DIVIDER
                } else {
                    COLOR_SATURATION_NULL
                }
            )
        })

        setupListeners()
    }
}
