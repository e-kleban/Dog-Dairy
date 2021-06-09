package by.kleban.dogdairy.ui.addpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kleban.dogdairy.R
import by.kleban.dogdairy.core.picasso.transformation.CircleTransform
import by.kleban.dogdairy.databinding.FragmentAddPostBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddPostViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtPostDescription.doAfterTextChanged { text -> if (text != null) viewModel.saveDescription(text.toString()) }
        setupImagePostPicker()

        binding.btnAddPostSave.setOnClickListener {

        }

        viewModel.imagePostLiveData.observe(viewLifecycleOwner){
            loadImagePostFromUri(it)
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
}