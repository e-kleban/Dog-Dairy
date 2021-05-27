package by.kleban.dogdairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kleban.dogdairy.adapter.ShowBreedsAdapter

class ShowBreedsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ShowBreedsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_breeds_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.show_recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val showBreedAdapter = ShowBreedsAdapter()

        recycler.adapter = showBreedAdapter

        viewModel.breedListLiveData.observe(viewLifecycleOwner) {
            showBreedAdapter.setItems(it)
        }
        viewModel.loadListBreed()
    }
}