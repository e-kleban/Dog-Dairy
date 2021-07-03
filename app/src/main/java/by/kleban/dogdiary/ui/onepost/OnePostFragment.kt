package by.kleban.dogdiary.ui.onepost

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
import by.kleban.dogdiary.R
import by.kleban.dogdiary.databinding.FragmentOnePostBinding
import by.kleban.dogdiary.entities.Post
import by.kleban.dogdiary.entities.Validation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OnePostFragment : Fragment() {

    private var _binding: FragmentOnePostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OnePostViewModel by viewModels()

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
        initToolbar()

        binding.btnOnePostSaveChanges.setOnClickListener {
            viewModel.updatePost()
        }

        binding.btnOnePostCancelChanges.setOnClickListener {
            setEditMode(false)
            binding.edtDesc.setText(binding.onePostDescription.text)
        }

        binding.edtDesc.doAfterTextChanged { text -> viewModel.updateDescription(text?.toString()) }

        viewModel.postLiveData.observe(viewLifecycleOwner) {
            setEditMode(false)
            setPost(it)
        }
        viewModel.validationDescriptionLiveData.observe(viewLifecycleOwner) {
            checkValidationDescription(it)
        }
    }

    private fun initToolbar() {
        binding.topAppBarOnePost.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val editPostItem = binding.topAppBarOnePost
            .menu
            .findItem(R.id.item_menu_one_post_edit_post)

        val deletePostItem = binding.topAppBarOnePost
            .menu
            .findItem(R.id.item_menu_one_post_delete_post)

        editPostItem.setOnMenuItemClickListener {
            setEditMode(true)
            binding.txtOnePostEditDesc.visibility = View.VISIBLE
            true
        }

        deletePostItem.setOnMenuItemClickListener {
            val deletePostDialogFragment = DeletePostDialogFragment()
            deletePostDialogFragment.show(
                requireActivity().supportFragmentManager,
                DeletePostDialogFragment.TAG
            )
            setOnClickDeletePost(deletePostDialogFragment)
            true
        }
        val deletePostDialogFragment = requireActivity().supportFragmentManager
            .findFragmentByTag(DeletePostDialogFragment.TAG) as DeletePostDialogFragment?
        if (deletePostDialogFragment != null) {
            setOnClickDeletePost(deletePostDialogFragment)
        }
    }

    private fun setOnClickDeletePost(deletePostDialogFragment: DeletePostDialogFragment) {
        deletePostDialogFragment.onClickButtonListener = DeletePostDialogFragment.OnClickButtonListener {
            viewModel.deletePost()
            findNavController().navigateUp()
        }
    }

    private fun setPost(it: Post) {
        binding.apply {
            onePostImage.transitionName = it.thumbnail
            onePostDescription.text = it.description
            edtDesc.setText(it.description)

            Picasso.get()
                .load(it.thumbnail)
                .error(R.drawable.error_image)
                .into(onePostImage, object : Callback {
                    override fun onSuccess() {
                        startPostponedEnterTransition()
                    }

                    override fun onError(e: Exception?) {
                        startPostponedEnterTransition()
                        Timber.e(e)
                    }
                })
        }
    }

    private fun setEditMode(inEdit: Boolean) {
        if (inEdit) {
            binding.apply {
                onePostDescription.visibility = View.GONE
                btnOnePostSaveChanges.visibility = View.VISIBLE
                btnOnePostCancelChanges.visibility = View.VISIBLE
                txtOnePostEditDesc.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                onePostDescription.visibility = View.VISIBLE
                btnOnePostSaveChanges.visibility = View.GONE
                txtOnePostEditDesc.visibility = View.GONE
                btnOnePostCancelChanges.visibility = View.GONE
            }
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
                binding.txtOnePostEditDesc.error = ""
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