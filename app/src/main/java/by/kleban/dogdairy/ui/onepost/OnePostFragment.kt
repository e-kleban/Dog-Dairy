package by.kleban.dogdairy.ui.onepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentOnePostBinding
import by.kleban.dogdairy.entities.Post
import by.kleban.dogdairy.entities.Validation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OnePostFragment : Fragment() {

    private val viewModel: OnePostViewModel by viewModels()

    private var _binding: FragmentOnePostBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transitionInflater = TransitionInflater.from(requireContext())
        sharedElementEnterTransition = transitionInflater.inflateTransition(R.transition.post)
            .apply { addListener(createTransitionListener()) }
        sharedElementReturnTransition = transitionInflater.inflateTransition(R.transition.post)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        binding.topAppBarOnePost.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initToolbar()
        binding.btnOnePostSaveChanges.setOnClickListener {
            viewModel.updatePost()
        }
        binding.btnOnePostCancelChanges.setOnClickListener {
            showEdit(true)
            binding.edtDesc.setText(binding.onePostDescription.text)
            viewModel.updateDescription(null)
        }
        binding.edtDesc.doAfterTextChanged { text -> viewModel.updateDescription(text.toString() ?: "") }

        viewModel.postLiveData.observe(viewLifecycleOwner) { setPost(it) }
        viewModel.validationDescriptionLiveData.observe(viewLifecycleOwner) {
            checkValidationDescription(it)
        }
    }

    private fun initToolbar() {
        val toolbar = binding.topAppBarOnePost
        val editPostItem = toolbar.menu.findItem(R.id.dog_page_edit_post)
        editPostItem.setOnMenuItemClickListener {
            showEdit(false)
            binding.txtOnePostEditDesc.visibility = View.VISIBLE
            true
        }
    }

    private fun setPost(it: Post) {
        Timber.d(it.toString())
        binding.onePostImage.transitionName = it.thumbnail
        binding.onePostDescription.text = it.description
        binding.edtDesc.setText(it.description)
        showEdit(true)
        Picasso.get()
            .load(it.thumbnail)
            .error(R.drawable.error_image)
            .into(binding.onePostImage, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                    Timber.e(e)
                }
            })
    }

    private fun showEdit(isShow: Boolean) {
        if (isShow) {
            binding.onePostDescription.visibility = View.VISIBLE
            binding.btnOnePostSaveChanges.visibility = View.GONE
            binding.txtOnePostEditDesc.visibility = View.GONE
            binding.btnOnePostCancelChanges.visibility = View.GONE
        }
        else{
            binding.onePostDescription.visibility = View.GONE
            binding.btnOnePostSaveChanges.visibility = View.VISIBLE
            binding.btnOnePostCancelChanges.visibility = View.VISIBLE
            binding.txtOnePostEditDesc.visibility = View.VISIBLE
        }

    }

    private fun checkValidationDescription(validation: Validation) {
        when (validation) {
            Validation.EMPTY -> {
                binding.txtOnePostEditDesc.isErrorEnabled = true
                binding.txtOnePostEditDesc.error = "Field can not be empty!"
            }
            else -> {
                binding.txtOnePostEditDesc.isErrorEnabled = false
            }
        }
    }

    private fun createTransitionListener(): Transition.TransitionListener {
        return object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                val post = arguments?.getSerializable(ONE_POST) as Post
                Picasso.get()
                    .load(post.image)
                    .placeholder(binding.onePostImage.drawable)
                    .error(R.drawable.error_image)
                    .into(binding.onePostImage)
            }
        }
    }

    companion object {
        const val ONE_POST = "one post"

    }
}