package by.kleban.dogdairy.ui.dogpage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdairy.database.mapper.DbDogMapper
import by.kleban.dogdairy.databinding.FragmentDogPageBinding
import by.kleban.dogdairy.entities.SharedConfig
import by.kleban.dogdairy.ui.dogpage.adapter.DogPageAdapter
import by.kleban.dogdairy.ui.dogpage.adapter.DogSpanSizeLookup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogPageFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(DogPageViewModel::class.java)
    }
    private val prefs by lazy { requireActivity().getSharedPreferences(SharedConfig.NAME_SHARED_PREF, Context.MODE_PRIVATE) }

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDogPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.dogPageRecycler
        val pageAdapter = DogPageAdapter(requireContext())

        val layoutManager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = pageAdapter
        layoutManager.spanSizeLookup = DogSpanSizeLookup(pageAdapter, layoutManager.spanCount)
        recycler.layoutManager = layoutManager

        val id = prefs.getLong(SharedConfig.SHARED_PREF_DOG_ID, 0)
        viewModel.getDog(id)
        viewModel.dogWithPostsLiveData.observe(viewLifecycleOwner) {
            val dog = DbDogMapper().map(it.dbDog)
            pageAdapter.setHeader(dog)

        }
    }
}