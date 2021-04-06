package com.example.modulotech.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.modulotech.R
import com.example.modulotech.di.Injection
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date
import kotlinx.android.synthetic.main.fragment_profile.et_profile_birthday
import kotlinx.android.synthetic.main.fragment_profile.et_profile_city
import kotlinx.android.synthetic.main.fragment_profile.et_profile_country
import kotlinx.android.synthetic.main.fragment_profile.et_profile_first_name
import kotlinx.android.synthetic.main.fragment_profile.et_profile_last_name
import kotlinx.android.synthetic.main.fragment_profile.et_profile_postal_code
import kotlinx.android.synthetic.main.fragment_profile.et_profile_street
import kotlinx.android.synthetic.main.fragment_profile.et_profile_street_code
import kotlinx.android.synthetic.main.profile_banner.iv_profile_picture

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    private val mProfileViewModel: ProfileViewModel by viewModels {
        Injection.provideProfileViewModelFactory(requireActivity().applicationContext)
    }

    private var mPicker: MaterialDatePicker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {

        et_profile_birthday.setOnClickListener {
            mPicker?.show(requireActivity().supportFragmentManager, TAG)
        }

        // text field listeners
        setupProfileFirstNameListeners()

        setupProfileLastNameListeners()

        setupProfileCityListeners()

        setupProfilePostalCodeListeners()

        setupProfileStreetNameListeners()

        setupProfileStreetCodeListeners()

        setupProfileCountryListeners()
    }

    private fun setupObservers() {
        mProfileViewModel.firstUser.observe(viewLifecycleOwner) {
            it?.let { user ->
                iv_profile_picture.setImageResource(R.drawable.bitmoji)
                et_profile_first_name.editText?.setText(user.firstName)
                et_profile_last_name.editText?.setText(user.lastName)
                et_profile_birthday.editText?.setText(mProfileViewModel.formatDate(user.birthDate))
                et_profile_city.editText?.setText(user.address.city)
                et_profile_postal_code.editText?.setText(user.address.postalCode.toString())
                et_profile_street.editText?.setText(user.address.street)
                et_profile_street_code.editText?.setText(user.address.streetCode)
                et_profile_country.editText?.setText(user.address.country)

                mPicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date))
                    .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                    .setSelection(user.birthDate.time)
                    .build()
                mPicker?.addOnPositiveButtonClickListener { dateLong ->
                    et_profile_birthday.editText?.setText(
                        mProfileViewModel.formatDate(
                            Date(
                                dateLong
                            )
                        )
                    )
                    saveUser()
                }
            }
        }
    }

    private fun saveUser() {
        mProfileViewModel.saveUser(
            firstName = et_profile_first_name.editText!!.text.toString(),
            lastName = et_profile_last_name.editText!!.text.toString(),
            birthdate = et_profile_birthday.editText!!.text.toString(),
            city = et_profile_city.editText!!.text.toString(),
            postalCode = et_profile_postal_code.editText!!.text.toString(),
            street = et_profile_street.editText!!.text.toString(),
            streetCode = et_profile_street_code.editText!!.text.toString(),
            country = et_profile_country.editText!!.text.toString()
        )
    }

    private fun setupProfileFirstNameListeners() {
        et_profile_first_name.editText?.addTextChangedListener {
            if (mProfileViewModel.checkFirstNameConformity(it.toString())) {
                et_profile_first_name.error = null
            } else {
                et_profile_first_name.error = getString(R.string.profile_field_error)
            }
        }
        et_profile_first_name.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkFirstNameConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfileLastNameListeners() {
        et_profile_last_name.editText?.addTextChangedListener {
            if (mProfileViewModel.checkLastNameConformity(it.toString())) {
                et_profile_last_name.error = null
            } else {
                et_profile_last_name.error = getString(R.string.profile_field_error)
            }
        }
        et_profile_last_name.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkLastNameConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfileCityListeners() {
        et_profile_city.editText?.addTextChangedListener {
            if (mProfileViewModel.checkCityConformity(it.toString())) {
                et_profile_city.error = null
            } else {
                et_profile_city.error = getString(R.string.profile_field_error)
            }
        }
        et_profile_city.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkCityConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfilePostalCodeListeners() {
        et_profile_postal_code.editText?.addTextChangedListener {
            if (mProfileViewModel.checkPostalCodeConformity(it.toString())) {
                et_profile_postal_code.error = null
            } else {
                et_profile_postal_code.error = getString(R.string.profile_postal_code_field_error)
            }
        }
        et_profile_postal_code.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkPostalCodeConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfileStreetNameListeners() {
        et_profile_street.editText?.addTextChangedListener {
            if (mProfileViewModel.checkStreetConformity(it.toString())) {
                et_profile_street.error = null
            } else {
                et_profile_street.error = getString(R.string.profile_field_error)
            }
        }
        et_profile_street.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkStreetConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfileStreetCodeListeners() {
        et_profile_street_code.editText?.addTextChangedListener {
            if (mProfileViewModel.checkStreetCodeConformity(it.toString())) {
                et_profile_street_code.error = null
            } else {
                et_profile_street_code.error = getString(R.string.profile_street_code_field_error)
            }
        }
        et_profile_street_code.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkStreetCodeConformity(textView.text.toString())) {
                        saveUser()
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

    private fun setupProfileCountryListeners() {
        et_profile_country.editText?.addTextChangedListener {
            if (mProfileViewModel.checkCountryConformity(it.toString())) {
                et_profile_country.error = null
            } else {
                et_profile_country.error = getString(R.string.profile_field_error)
            }
        }
        et_profile_country.editText?.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mProfileViewModel.checkCountryConformity(textView.text.toString())) {
                        saveUser()
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
}
