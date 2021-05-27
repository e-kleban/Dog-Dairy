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

        inputLayoutBreed.onClickListenerNavigate(R.id.showShowBreedsFragment)
        inputLayoutBreed.editText?.onClickListenerNavigate(R.id.showShowBreedsFragment)
    }


    private fun View.onClickListenerNavigate(resId:Int){
        this.setOnClickListener {
            findNavController().navigate(resId)
        }
    }
}