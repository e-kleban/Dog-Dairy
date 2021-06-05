package by.kleban.dogdairy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdairy.adapter.DogPageAdapter
import by.kleban.dogdairy.databinding.FragmentDogPageBinding
import by.kleban.dogdairy.entities.Dog


class DogPageFragment : Fragment() {

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
        layoutManager.paddingStart
        layoutManager.apply {
            //TODO separate class
            spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (pageAdapter.getItemViewType(position)) {
                            DogPageAdapter.TYPE_HEADER -> layoutManager.spanCount
                            DogPageAdapter.TYPE_POST -> 1
                            else -> -1
                        }
                    }
                }
        }
        val dog = Dog(
            name = "Dolka",
            age = 5,
            breed = "Poodle",
            image = "error",
            description = "The best dog",
            sex = "female"
        )

        recycler.layoutManager = layoutManager
        val list = listOf<DogPageAdapter.Item>(
            DogPageAdapter.Item.Header(
                name = dog.name,
                age = dog.age,
                breed = dog.breed,
                image = dog.image,
                description = dog.description,
                sex = dog.sex
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ), DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            ),
            DogPageAdapter.Item.Post(
                "Hello"
            )
        )
        pageAdapter.submitData(list)
    }
}