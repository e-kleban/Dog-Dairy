package by.kleban.dogdairy.ui.dogpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentDogPageBinding
import by.kleban.dogdairy.entities.Post
import by.kleban.dogdairy.ui.dogpage.adapter.DogPageAdapter
import by.kleban.dogdairy.ui.dogpage.adapter.DogSpanSizeLookup
import by.kleban.dogdairy.ui.onepost.OnePostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DogPageFragment : Fragment() {

    private val viewModel: DogPageViewModel by viewModels()

    @Inject
    lateinit var dogPageAdapter: DogPageAdapter

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDogPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.dogPageRecycler
        dogPageAdapter.postClickListener = DogPageAdapter.OnPostClickListener { post, extra -> onItemCLick(post, extra) }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = dogPageAdapter
        layoutManager.spanSizeLookup = DogSpanSizeLookup(dogPageAdapter, layoutManager.spanCount)
        recycler.layoutManager = layoutManager

        viewModel.dogWithPostsLiveData.observe(viewLifecycleOwner) {
            dogPageAdapter.setHeader(it.dog)
            dogPageAdapter.setPosts(it.posts)
        }

        val toolBar = binding.topAppBarDogPage
        val addPostItem = toolBar.menu.findItem(R.id.dog_page_add_post)
        addPostItem.setOnMenuItemClickListener {
            findNavController().navigate(R.id.from_dogPageFragment_to_addPostFragment)
            true
        }
        viewModel.getDogWithPosts()
    }

    private fun onItemCLick(post: Post, extra: Pair<ImageView, String>) {
        val bundlePost = bundleOf(OnePostFragment.ONE_POST to post)
        val extras = FragmentNavigatorExtras(extra)
        findNavController()
            .navigate(R.id.from_dogPageFragment_to_onePostFragment, bundlePost, null, extras)
    }
}