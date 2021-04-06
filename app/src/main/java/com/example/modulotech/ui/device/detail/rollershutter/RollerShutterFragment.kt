package com.example.modulotech.ui.device.detail.rollershutter

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
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.di.Injection
import com.example.modulotech.ui.device.detail.DeviceDetailActivity.Companion.ARG_ITEM_ID
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_detail_shutter.et_profile_device_name
import kotlinx.android.synthetic.main.fragment_detail_shutter.profile_shutter_position_slider
import kotlinx.android.synthetic.main.profile_banner.iv_profile_picture

class RollerShutterFragment : Fragment() {

    companion object {
        const val COLOR_SATURATION_DIVIDER = 100f
        const val DEFAULT_DEVICE_ID = 0
    }

    private val mShutterDetailViewModel: RollerShutterDetailViewModel by viewModels {
        Injection.provideShutterDetailViewModelFactory(requireActivity().applicationContext)
    }

    private var mDeviceId = DEFAULT_DEVICE_ID

    private val mDeviceNameChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Nothing to do
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (mShutterDetailViewModel.checkDeviceNameConformity(s.toString())) {
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
        inflater.inflate(R.layout.fragment_detail_shutter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                mDeviceId = it.getInt(ARG_ITEM_ID)
                mShutterDetailViewModel.getShutterById(mDeviceId)
                    .removeObservers(viewLifecycleOwner)
                mShutterDetailViewModel.getShutterById(mDeviceId)
                    .observe(viewLifecycleOwner) { shutter ->
                        shutter?.let { shutterNotNull ->
                            setupViews(shutterNotNull)
                        }
                    }
            } else {
                requireActivity().finish()
            }
        }
        setupListeners()
    }

    private fun setupListeners() {
        profile_shutter_position_slider.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Do nothing
            }

            override fun onStopTrackingTouch(slider: Slider) {
                mShutterDetailViewModel.setPosition(mDeviceId, slider.value)
            }
        })
        et_profile_device_name.editText?.addTextChangedListener(mDeviceNameChangedListener)
        et_profile_device_name.editText?.setOnEditorActionListener { textView, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mShutterDetailViewModel
                            .checkDeviceNameConformity(textView.text.toString())) {
                        mShutterDetailViewModel.saveDeviceName(mDeviceId, textView.text.toString())
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
        profile_shutter_position_slider.clearOnSliderTouchListeners()
    }

    private fun setupViews(shutter: RollerShutterDbModel) {
        cleanListeners()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = shutter.name

        profile_shutter_position_slider.value = shutter.position.toFloat()
        et_profile_device_name.editText?.setText(shutter.name)

        iv_profile_picture.setImageResource(R.drawable.ic_window_profile)
        iv_profile_picture.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
            setSaturation(
                shutter.position / COLOR_SATURATION_DIVIDER
            )
        })

        setupListeners()
    }
}
