package by.kleban.dogdairy.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.entities.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nextFragmentLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    Screen.REGISTRATION -> {
                        findNavController().navigate(R.id.showRegistrationFragment)
                    }
                    Screen.DOG -> {
                        findNavController().navigate(R.id.from_splashScreenFragment_to_dogPageFragment)
                    }
                }
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}