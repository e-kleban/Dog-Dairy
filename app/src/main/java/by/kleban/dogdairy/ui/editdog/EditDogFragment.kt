package by.kleban.dogdairy.ui.editdog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kleban.dogdairy.R
import by.kleban.dogdairy.core.picasso.transformation.CircleTransform
import by.kleban.dogdairy.databinding.FragmentEditDogBinding
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import by.kleban.dogdairy.ui.showbreeds.ShowBreedsFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDogFragment : Fragment() {

    private val viewModel: EditDogViewModel by viewModels()

    private var _binding: FragmentEditDogBinding? = null
    private val binding get() = _binding!!

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
            findNavController().navigate(R.id.from_editDogFragment_to_registrationFragment)
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