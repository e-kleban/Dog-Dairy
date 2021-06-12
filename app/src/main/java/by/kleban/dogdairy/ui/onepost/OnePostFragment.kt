package by.kleban.dogdairy.ui.onepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentOnePostBinding
import by.kleban.dogdairy.entities.Post
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

        val post = arguments?.getSerializable(ONE_POST) as Post
        binding.onePostImage.transitionName = post.thumbnail
        binding.onePostDescription.text = post.description
        Picasso.get()
            .load(post.thumbnail)
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