package by.kleban.dogdairy.ui.addpost

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentAddPostBinding
import by.kleban.dogdairy.entities.SharedConfig
import by.kleban.dogdairy.entities.Validation
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddPostViewModel::class.java)
    }

    private val prefs by lazy { requireActivity().getSharedPreferences(SharedConfig.NAME_SHARED_PREF, Context.MODE_PRIVATE) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtPostDescription.doAfterTextChanged { text -> if (text != null) viewModel.saveDescription(text.toString()) }
        setupImagePostPicker()

        binding.btnAddPostSave.setOnClickListener {
            val dogId = prefs.getLong(SharedConfig.SHARED_PREF_DOG_ID, 0)
            viewModel.addPost(dogId)
        }

        viewModel.validationDescriptionLiveData.observe(viewLifecycleOwner) { checkValidationDescription(it) }
        viewModel.validationImageLiveData.observe(viewLifecycleOwner) { checkImageValidation(it) }

        viewModel.imagePostLiveData.observe(viewLifecycleOwner) {
            loadImagePostFromUri(it)
        }

        viewModel.isSavedPostLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigateUp()
            }
        }
    }

    private fun checkImageValidation(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.btnAddPostChooseImage.setTextColor((resources.getColor(R.color.red, null)))
            }
            else -> {
                binding.btnAddPostChooseImage.setTextColor(resources.getColor(R.color.white, null))
            }
        }
    }

    private fun checkValidationDescription(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtInputAppPostDescription.isErrorEnabled = true
                binding.txtInputAppPostDescription.error = "Field can not be empty!"
            }
            else -> {
                binding.txtInputAppPostDescription.isErrorEnabled = false
            }
        }
    }

    private fun loadImagePostFromUri(uri: String?) {
        if (!uri.isNullOrEmpty()) {
            Picasso.get()
                .load(uri)
                .error(R.drawable.error_image)
                .into(binding.imageAddPost)
            binding.btnAddPostChooseImage.visibility = View.GONE
        }
    }

    private fun setupImagePostPicker() {

        val pickImages = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                viewModel.saveImageFile(uri, requireContext())
            }
        }
        binding.imageAddPost.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
        binding.btnAddPostChooseImage.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
    }

    companion object {
        const val ADD_POST = "add post"
    }
}