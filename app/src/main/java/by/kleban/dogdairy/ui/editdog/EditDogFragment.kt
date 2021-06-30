package by.kleban.dogdairy.ui.editdog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kleban.dogdairy.databinding.FragmentEditDogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDogFragment : Fragment() {

    private val viewModel: EditDogViewModel by viewModels()

    private var _binding: FragmentEditDogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditDogBinding.inflate(inflater, container, false)
        return binding.root
    }
}