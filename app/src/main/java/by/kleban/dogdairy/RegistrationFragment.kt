package by.kleban.dogdairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment : Fragment() {

    private lateinit var inputLayoutName: TextInputLayout
    private lateinit var inputLayoutBreed: TextInputLayout
    private lateinit var breedButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputLayoutName = view.findViewById(R.id.txt_input_registration_name)
        inputLayoutBreed = view.findViewById(R.id.txt_input_registration_breed)
        breedButton = view.findViewById(R.id.btn_choose_breed)

        breedButton.setOnClickListener {
            findNavController().navigate(R.id.showShowBreedsFragment)
        }
    }
}