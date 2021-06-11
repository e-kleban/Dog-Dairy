package by.kleban.dogdairy.ui.onepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentOnePostBinding
import by.kleban.dogdairy.entities.Post
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnePostFragment : Fragment() {

    private val viewModel: OnePostViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private var _binding: FragmentOnePostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val post = arguments?.getSerializable(ONE_POST) as Post

        binding.topAppBarRegistration.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.onePostDescription.text = post.postDescription
        Picasso.get()
            .load(post.postImage)
            .error(R.drawable.error_image)
            .into(binding.onePostImage)
    }

    companion object {
        const val ONE_POST = "one post"

    }
}