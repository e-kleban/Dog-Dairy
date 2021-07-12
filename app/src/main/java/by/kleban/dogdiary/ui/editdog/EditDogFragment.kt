package by.kleban.dogdiary.ui.editdog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdiary.R
import by.kleban.dogdiary.databinding.FragmentEditDogBinding
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.Sex
import by.kleban.dogdiary.ui.showbreeds.ShowBreedsFragment
import by.kleban.dogdiary.utils.picasso.transformation.CircleTransform
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDogFragment : Fragment() {

    private var _binding: FragmentEditDogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditDogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditDogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtEditDogBreed.setOnClickListener { findNavController().navigate(R.id.from_editDogFragment_to_showBreedsFragment) }
        binding.edtDogBreed.setOnClickListener { findNavController().navigate(R.id.from_editDogFragment_to_showBreedsFragment) }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
        binding.btnSaveChanges.setOnClickListener {
            viewModel.saveChangedDog()
        }
        setupImagePicker()
        initToolbar()

        binding.edtDogName.doAfterTextChanged { name -> if (name != null) viewModel.changeName(name.toString()) }
        binding.edtDogAge.doAfterTextChanged { age -> if (age != null) viewModel.changeAge(age.toString().toInt()) }
        binding.edtDogDescription.doAfterTextChanged { text -> if (text != null) viewModel.changeDescription(text.toString()) }

        binding.radioGroupSexEditDog.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn_female -> viewModel.changeSex(Sex.FEMALE)
                R.id.radio_btn_male -> viewModel.changeSex(Sex.MALE)
            }
        }

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(ShowBreedsFragment.EXTRA_BREED)
            ?.observe(viewLifecycleOwner) { result ->
                viewModel.changeBreed(result)
            }

        viewModel.dogLiveData.observe(viewLifecycleOwner) { setDog(it) }
        viewModel.dogIsSavedLiveData.observe(viewLifecycleOwner) {
            if (it == true) findNavController().navigateUp()
        }
        viewModel.dogIsDeleteLiveData.observe(viewLifecycleOwner) {
            if (it == true) findNavController().navigate(R.id.from_editDogFragment_to_registrationFragment)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initToolbar() {
        val toolBar = binding.topAppBarEditDog
        val itemDeleteDog = toolBar.menu.findItem(R.id.item_menu_edit_dog_delete)
        itemDeleteDog.setOnMenuItemClickListener {
            val deleteDogDialogFragment = DeleteDogDialogFragment()
            deleteDogDialogFragment.show(
                requireActivity().supportFragmentManager,
                DeleteDogDialogFragment.TAG
            )
            setOnClickDeleteDog(deleteDogDialogFragment)
            true
        }
        val deleteDogDialogFragment = requireActivity().supportFragmentManager
            .findFragmentByTag(DeleteDogDialogFragment.TAG) as DeleteDogDialogFragment?
        if (deleteDogDialogFragment != null) {
            setOnClickDeleteDog(deleteDogDialogFragment)
        }
    }

    private fun setOnClickDeleteDog(deleteDogDialogFragment: DeleteDogDialogFragment) {
        deleteDogDialogFragment.onClickButtonListener = DeleteDogDialogFragment.OnClickButtonListener {
            viewModel.deleteDog()
        }
    }

    private fun setupImagePicker() {
        val pickImages = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                viewModel.changeImage(uri)
            }
        }
        binding.btnEditImgDog.setOnClickListener {
            pickImages.launch(arrayOf("image/*"))
        }
    }

    private fun setDog(dog: Dog) {
        binding.apply {
            if (edtDogName.text?.toString() != dog.name) edtDogName.setText(dog.name)
            if (edtDogAge.text?.toString() != dog.age.toString()) edtDogAge.setText(dog.age.toString())
            if (edtDogBreed.text?.toString() != dog.breed) edtDogBreed.setText(dog.breed)
            if (edtDogDescription.text?.toString() != dog.description) edtDogDescription.setText(dog.description)
            when (dog.sex) {
                Sex.FEMALE -> binding.radioBtnFemale.isChecked = true
                Sex.MALE -> binding.radioBtnMale.isChecked = true
            }

            if (imgEditDog.tag != dog.thumbnail) {
                imgEditDog.tag = dog.thumbnail
                Picasso.get()
                    .load(dog.thumbnail)
                    .error(R.drawable.error_image)
                    .transform(CircleTransform())
                    .into(imgEditDog)
            }
        }
        startPostponedEnterTransition()
    }
}