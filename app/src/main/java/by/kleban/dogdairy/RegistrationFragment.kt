package by.kleban.dogdairy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


class RegistrationFragment : Fragment() {

    private lateinit var inputLayoutName: TextInputLayout
    private lateinit var inputLayoutBreed: TextInputLayout
    private lateinit var dogImage: ImageView
    private lateinit var dogImageLabel: TextView

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
        dogImage = view.findViewById(R.id.img_registration)
        dogImageLabel = view.findViewById(R.id.txt_click_choose_img)

        inputLayoutBreed.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        inputLayoutBreed.editText?.setOnClickListener { findNavController().navigate(R.id.showShowBreedsFragment) }
        showBreedFromArgs()
        setupImagePicker()
    }

    private fun showBreedFromArgs() {
        val breed = arguments?.getString(ShowBreedsFragment.BUNDLE_BREED)
        inputLayoutBreed.editText?.setText(breed)
    }

    private fun setupImagePicker() {
        val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Picasso.get()
                .load(uri)
                .error(R.drawable.error_image)
                .into(dogImage)
        }
        dogImageLabel.setOnClickListener {
            pickImages.launch("image/*")
            dogImageLabel.visibility = View.GONE
        }
        dogImage.setOnClickListener {
            pickImages.launch("image/*")
            dogImageLabel.visibility = View.GONE
        }
    }
}