package by.kleban.dogdairy.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.core.picasso.transformation.CircleTransform
import by.kleban.dogdairy.databinding.FragmentRegistrationBinding
import by.kleban.dogdairy.entities.Sex
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.ui.showbreeds.ShowBreedsFragment.Companion.EXTRA_BREED
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtInputRegistrationBreed.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        binding.inputBreed.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        setupImagePicker()

        setRadioGroupOnCheckedChangeListener()

        binding.inputName.doAfterTextChanged { name -> if (name != null) viewModel.saveName(name.toString()) }
        binding.inputAge.doAfterTextChanged { age -> if (age != null) viewModel.saveAge(age.toString().toIntOrNull() ?: -1) }
        binding.inputDescription.doAfterTextChanged { desc -> if (desc != null) viewModel.saveDescription(desc.toString()) }
        binding.inputBreed.doAfterTextChanged { breed -> if (breed != null) viewModel.saveBreed(breed.toString()) }

        binding.btnCreateDog.setOnClickListener {
            viewModel.registration()
        }

        viewModel.imageLiveData.observe(viewLifecycleOwner) { pair ->
            loadImageFromUri(pair.first)
        }

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(EXTRA_BREED)
            ?.observe(viewLifecycleOwner) { result ->
                binding.inputBreed.setText(result)
            }
        viewModel.validationNameLiveData.observe(viewLifecycleOwner) { validation -> checkValidationName(validation) }
        viewModel.validationAgeLiveData.observe(viewLifecycleOwner) { validation -> checkValidationAge(validation) }
        viewModel.validationImageLiveData.observe(viewLifecycleOwner) { validation -> checkValidationImage(validation) }
        viewModel.validationSexLiveData.observe(viewLifecycleOwner) { validation -> checkValidationSex(validation) }
        viewModel.validationBreedLiveData.observe(viewLifecycleOwner) { validation -> checkValidationBreed(validation) }
        viewModel.validationDescriptionLiveData.observe(viewLifecycleOwner) { validation -> checkValidationDescription(validation) }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.registrationProgressBar.visibility = if (it == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.registrationLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.from_registrationFragment_to_dogPageFragment)
            }
        }
    }

    private fun loadImageFromUri(uri: String?) {
        if (!uri.isNullOrEmpty()) {
            Picasso.get()
                .load(uri)
                .error(R.drawable.error_image)
                .transform(CircleTransform())
                .into(binding.imgRegistrationDog)
            binding.txtImgLabelRegistration.visibility = View.GONE
        }
    }

    private fun setRadioGroupOnCheckedChangeListener() {
        binding.radioGroupSexRegistrationDog.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn_female -> viewModel.saveSex(Sex.FEMALE)
                R.id.radio_btn_male -> viewModel.saveSex(Sex.MALE)
            }
        }
    }

    private fun setupImagePicker() {

        val pickImages = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                viewModel.saveImageFile(uri)
            }
        }
        binding.txtImgLabelRegistration.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
        binding.imgRegistrationDog.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
    }

    private fun checkValidationName(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputRegistrationName.isErrorEnabled = true
                binding.txtInputRegistrationName.error = "Field can not be empty!"
            }
            else -> {
                binding.txtInputRegistrationName.isErrorEnabled = false
            }
        }
    }

    private fun checkValidationAge(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputRegistrationAge.isErrorEnabled = true
                binding.txtInputRegistrationAge.error = "Field can not be empty!"
            }
            else -> {
                binding.txtInputRegistrationAge.isErrorEnabled = false
            }
        }
    }

    private fun checkValidationSex(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputRegistrationSex.setTextColor(resources.getColor(R.color.red, null))
            }
            else -> {
                binding.txtInputRegistrationSex.setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }

    private fun checkValidationImage(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtImgLabelRegistration.setTextColor(resources.getColor(R.color.red, null))
            }
            else -> {
                binding.txtImgLabelRegistration.setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }

    private fun checkValidationBreed(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputRegistrationBreed.isErrorEnabled = true
                binding.txtInputRegistrationBreed.error = "Field can not be empty!"
            }
            else -> {
                binding.txtInputRegistrationBreed.isErrorEnabled = false
            }
        }
    }

    private fun checkValidationDescription(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputRegistrationDescription.isErrorEnabled = true
                binding.txtInputRegistrationDescription.error = "Field can not be empty!"
            }
            else -> {
                binding.txtInputRegistrationDescription.isErrorEnabled = false
            }
        }
    }
}