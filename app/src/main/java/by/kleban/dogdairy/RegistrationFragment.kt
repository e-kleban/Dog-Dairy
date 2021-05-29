package by.kleban.dogdairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.ShowBreedsFragment.Companion.EXTRA_BREED
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


class RegistrationFragment : Fragment() {

    private lateinit var inputLayoutName: TextInputLayout
    private lateinit var inputLayoutBreed: TextInputLayout
    private lateinit var inputLayoutAge: TextInputLayout
    private lateinit var inputLayoutDescription: TextInputLayout
    private lateinit var dogImage: ImageView
    private lateinit var dogImageLabel: TextView
    private lateinit var sexRadioGroup: RadioGroup

    private val viewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputLayoutName = view.findViewById(R.id.txt_input_registration_name)
        inputLayoutBreed = view.findViewById(R.id.txt_input_registration_breed)
        inputLayoutAge = view.findViewById(R.id.txt_input_registration_age)
        inputLayoutDescription = view.findViewById(R.id.txt_input_registration_description)
        dogImage = view.findViewById(R.id.img_registration)
        dogImageLabel = view.findViewById(R.id.txt_click_choose_img)
        sexRadioGroup = view.findViewById(R.id.radio_group_sex_registration)

        inputLayoutBreed.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        inputLayoutBreed.editText?.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        setupImagePicker()

//        sexRadioGroup.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.radio_btn_female ->viewModel.sexLiveData
//                    R.id.radio_btn_male->
//            }
//        }
        inputLayoutName.editText?.doAfterTextChanged { name -> if (name != null) viewModel.saveName(name.toString()) }
        inputLayoutAge.editText?.doAfterTextChanged { age -> if (age != null) viewModel.saveAge(age.toString()) }
        inputLayoutDescription.editText?.doAfterTextChanged { desc -> if (desc != null) viewModel.saveDescription(desc.toString()) }

        viewModel.imageLiveData.observe(viewLifecycleOwner) { uri ->
            Picasso.get()
                .load(uri)
                .error(R.drawable.error_image)
                .into(dogImage)
        }

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(EXTRA_BREED)
            ?.observe(viewLifecycleOwner) { result -> inputLayoutBreed.editText?.setText(result) }
    }

    private fun setupImagePicker() {
        val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.saveImage(uri.toString())
        }
        dogImageLabel.setOnClickListener {
            pickImages.launch("image/*")
            dogImageLabel.visibility = View.GONE
        }
        dogImage.setOnClickListener {
            pickImages.launch("image/*")
            dogImageLabel.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = RegistrationFragment::class.java.simpleName
    }
}