package by.kleban.dogdairy.ui.onepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentOnePostBinding
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
}