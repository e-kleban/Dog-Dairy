package by.kleban.dogdairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.ShowBreedsFragment.Companion.EXTRA_BREED
import by.kleban.dogdairy.databinding.FragmentRegistrationBinding
import com.squareup.picasso.Picasso


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

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
        binding.edtBreed.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        setupImagePicker()

//        sexRadioGroup.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.radio_btn_female ->viewModel.sexLiveData
//                    R.id.radio_btn_male->
//            }
//        }
        binding.edtName.doAfterTextChanged { name -> if (name != null) viewModel.saveName(name.toString()) }
        binding.edtAge.doAfterTextChanged { age -> if (age != null) viewModel.saveAge(age.toString()) }
        binding.edtDescription.doAfterTextChanged { desc -> if (desc != null) viewModel.saveDescription(desc.toString()) }

        viewModel.imageLiveData.observe(viewLifecycleOwner) { uri ->
            if (!uri.isNullOrEmpty()) {
                Picasso.get()
                    .load(uri)
                    .error(R.drawable.error_image)
                    .into(binding.imgRegistration)
                binding.txtImgLabelRegistration.visibility = View.GONE
            }
        }

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(EXTRA_BREED)
            ?.observe(viewLifecycleOwner) { result -> binding.edtBreed.setText(result) }
    }

    private fun setupImagePicker() {
        val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.saveImage(uri.toString())
            }
        }
        binding.txtImgLabelRegistration.setOnClickListener {
            pickImages.launch("image/*")
        }
        binding.imgRegistration.setOnClickListener {
            pickImages.launch("image/*")
        }
    }

    companion object {
        private val TAG = RegistrationFragment::class.java.simpleName
    }
}