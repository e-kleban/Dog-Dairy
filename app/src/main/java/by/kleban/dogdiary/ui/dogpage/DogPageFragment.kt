package by.kleban.dogdiary.ui.dogpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdiary.R
import by.kleban.dogdiary.databinding.FragmentDogPageBinding
import by.kleban.dogdiary.entities.Post
import by.kleban.dogdiary.ui.dogpage.adapter.DogPageAdapter
import by.kleban.dogdiary.ui.dogpage.adapter.DogSpanSizeLookup
import by.kleban.dogdiary.ui.onepost.OnePostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DogPageFragment : Fragment() {

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DogPageViewModel by viewModels()

    @Inject
    lateinit var dogPageAdapter: DogPageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDogPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }

        val recycler = binding.dogPageRecycler
        dogPageAdapter.postClickListener = DogPageAdapter.OnPostClickListener { post, extra -> onItemCLick(post, extra) }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = dogPageAdapter
        layoutManager.spanSizeLookup = DogSpanSizeLookup(dogPageAdapter, layoutManager.spanCount)
        recycler.layoutManager = layoutManager

        initToolBar()
        viewModel.getDogWithPosts()
        viewModel.dogWithPostsLiveData.observe(viewLifecycleOwner) {
            dogPageAdapter.setHeader(it.dog)
            val reversedPosts = it.posts.reversed()
            dogPageAdapter.setPosts(reversedPosts)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initToolBar() {
        val toolBar = binding.topAppBarDogPage
        val addPostItem = toolBar.menu.findItem(R.id.item_menu_dog_page_add_post)
        val profileItem = toolBar.menu.findItem(R.id.item_menu_dog_page_profile)
        profileItem.setOnMenuItemClickListener {
            findNavController().navigate(R.id.from_dogPageFragment_to_profileFragment)
            true
        }
        addPostItem.setOnMenuItemClickListener {
            findNavController().navigate(R.id.from_dogPageFragment_to_addPostFragment)
            true
        }
    }

    private fun onItemCLick(post: Post, extra: Pair<ImageView, String>) {
        val bundlePost = bundleOf(OnePostFragment.ONE_POST to post)
        val extras = FragmentNavigatorExtras(extra)
        findNavController()
            .navigate(R.id.from_dogPageFragment_to_onePostFragment, bundlePost, null, extras)
    }
}