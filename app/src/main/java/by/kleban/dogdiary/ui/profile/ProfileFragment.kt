package by.kleban.dogdiary.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdiary.R
import by.kleban.dogdiary.databinding.FragmentProfileBinding
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.Sex
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.getDogWithPosts()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBarProfile.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        initToolbar()

        viewModel.dogWithPostsLiveData.observe(viewLifecycleOwner) {
            setDog(it.dog)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initToolbar() {
        val toolBar = binding.topAppBarProfile
        val itemEditDog = toolBar.menu.findItem(R.id.item_menu_profile_edit_dog)
        itemEditDog.setOnMenuItemClickListener {
            findNavController().navigate(R.id.from_profileFragment_to_editDogFragment)
            true
        }
    }

    private fun setDog(dog: Dog) {
        binding.apply {
            txtProfileName.text = requireContext().getString(R.string.profile_name, dog.name)
            txtProfileAge.text = requireContext().getString(R.string.profile_age, dog.age)
            txtProfileBreed.text = requireContext().getString(R.string.profile_breed, dog.breed)
            txtProfileDescription.text = dog.description
            val idSex = when (dog.sex) {
                Sex.FEMALE -> R.drawable.ic_sex_female
                Sex.MALE -> R.drawable.ic_sex_male
            }
            imgProfileSex.setImageResource(idSex)
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