package by.kleban.dogdairy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kleban.dogdairy.databinding.FragmentDogPageBinding


class DogPageFragment : Fragment() {

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}