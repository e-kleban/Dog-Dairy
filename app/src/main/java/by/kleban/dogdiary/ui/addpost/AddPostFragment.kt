package by.kleban.dogdiary.ui.addpost

import android.net.Uri
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
import by.kleban.dogdiary.R
import by.kleban.dogdiary.databinding.FragmentAddPostBinding
import by.kleban.dogdiary.entities.Validation
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddPostViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtPostDescription.doAfterTextChanged { text -> if (text != null) viewModel.saveDescription(text.toString()) }
        setupImagePostPicker()

        binding.btnAddPostSave.setOnClickListener {
            viewModel.addPost()
        }

        viewModel.validationDescriptionLiveData.observe(viewLifecycleOwner) { checkValidationDescription(it) }
        viewModel.validationImageLiveData.observe(viewLifecycleOwner) { checkImageValidation(it) }

        viewModel.imageLiveData.observe(viewLifecycleOwner) {
            loadImagePostFromUri(it)
        }

        viewModel.isSavedPostLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().popBackStack()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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

    private fun loadImagePostFromUri(uri: Uri) {
        Picasso.get()
            .load(uri)
            .error(R.drawable.error_image)
            .into(binding.imageAddPost)
        binding.btnAddPostChooseImage.visibility = View.GONE
    }

    private fun setupImagePostPicker() {
        val pickImages = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                viewModel.chooseImage(uri)
            }
        }
        binding.imageAddPost.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
        binding.btnAddPostChooseImage.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
    }
}