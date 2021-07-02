package by.kleban.dogdairy.ui.showbreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.ShowBreedsFragmentBinding
import by.kleban.dogdairy.ui.showbreeds.adapter.ShowBreedsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowBreedsFragment : Fragment() {

    private var _binding: ShowBreedsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShowBreedsViewModel by viewModels()

    @Inject
    lateinit var showBreedAdapter: ShowBreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShowBreedsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.showRecycler
        showBreedAdapter.breedClickListener = ShowBreedsAdapter.OnBreedClickListener { onBreedCLick(it) }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = showBreedAdapter

        setupSearchView()

        viewModel.breedListLiveData.observe(viewLifecycleOwner) {
            showBreedAdapter.setItems(it)
        }
        viewModel.breedListWithFilter.observe(viewLifecycleOwner) {
            showBreedAdapter.setItems(it)
        }
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.showBreedsProgressBar.visibility = if (it == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loadListBreed()
    }

    private fun setupSearchView() {
        val toolbar = binding.topAppBarBreed
        val searchItem = toolbar.menu.findItem(R.id.item_menu_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter(newText ?: "")
                return true
            }
        })
        searchView.setOnCloseListener {
            viewModel.filter("")
            false
        }
    }

    private fun onBreedCLick(breed: String) {
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(EXTRA_BREED, breed)

        findNavController().navigateUp()
    }

    companion object {
        const val EXTRA_BREED = "breed"
    }
}