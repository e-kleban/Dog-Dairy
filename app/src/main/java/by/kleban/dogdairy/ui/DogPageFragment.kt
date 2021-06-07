package by.kleban.dogdairy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdairy.adapter.DogPageAdapter
import by.kleban.dogdairy.adapter.gridlayoutmanager.DogSpanSizeLookup
import by.kleban.dogdairy.databinding.FragmentDogPageBinding
import by.kleban.dogdairy.entities.Dog


class DogPageFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(DogPageViewModel::class.java)
    }

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDogPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.dogPageRecycler
        val pageAdapter = DogPageAdapter()

        val layoutManager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = pageAdapter
        layoutManager.spanSizeLookup = DogSpanSizeLookup(pageAdapter)
        recycler.layoutManager = layoutManager
        pageAdapter.setHeader(Dog("Dolka", "fgfg", 5, "female", "Poodle", "The best dog"))
    }
}