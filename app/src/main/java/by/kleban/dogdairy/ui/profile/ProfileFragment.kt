package by.kleban.dogdairy.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.databinding.FragmentProfileBinding
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBarProfile.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getDogWithPosts()
        viewModel.dogWithPostsLiveData.observe(viewLifecycleOwner) {
            setDog(it.dog)
        }
    }

    private fun setDog(dog: Dog) {
        binding.apply {
            txtProfileName.text = requireContext().getString(R.string.profile_name, dog.name)
            txtProfileAge.text = requireContext().getString(R.string.profile_age, dog.age)
            txtProfileBreed.text = requireContext().getString(R.string.profile_breed, dog.breed)
            txtProfileDescription.text = dog.description
            if (dog.sex == Sex.FEMALE) {
                imgProfileSex.setImageResource(R.drawable.ic_sex_female)
            } else if (dog.sex == Sex.MALE) {
                imgProfileSex.setImageResource(R.drawable.ic_sex_male)
            }
            Picasso.get()
                .load(dog.thumbnail)
                .error(R.drawable.error_image)
                .into(imgProfile, object : Callback {
                    override fun onSuccess() {
                        Picasso.get()
                            .load(dog.image)
                            .placeholder(imgProfile.drawable)
                            .error(R.drawable.error_image)
                            .into(imgProfile)
                    }

                    override fun onError(e: Exception?) {
                        Timber.e(e)
                    }
                })
        }
    }
}