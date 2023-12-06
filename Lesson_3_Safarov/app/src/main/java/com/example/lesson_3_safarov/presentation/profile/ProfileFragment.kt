package com.example.lesson_3_safarov.presentation.profile

import android.Manifest
import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_safarov.BuildConfig
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.databinding.FragmentProfileBinding
import com.example.lesson_3_safarov.presentation.exception.getError
import dagger.android.support.AndroidSupportInjection
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class ProfileFragment : Fragment() {

    companion object {
        private const val VIEW_FLIPPER_LOADING = 0
        private const val VIEW_FLIPPER_ERROR = 1
        private const val VIEW_FLIPPER_DATA = 2
        private const val TIMESTAMP_PATTERN = "MMddyyyy_HHmmss"
        private const val PROVIDER_PATHS_POSTFIX = ".fileprovider"
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var startCameraForResultLauncher: ActivityResultLauncher<Intent>? = null
    private var startGalleryForResultLauncher: ActivityResultLauncher<Intent>? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startCameraForResultLauncher?.launch(photoIntent)
            }
        }
    private var savedPhotoPath: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ProfileViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.black_20pc)
        setStateObserver()
        setListeners()
        setActivityResults()
        return binding.root
    }

    private fun setListeners() {
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.textOccupation.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToOccupationBottomSheetFragment()
            )
        }
        binding.circularProfilePhoto.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToPhotoBottomSheetFragment()
            )
        }
        setFragmentResultListener(PhotoBottomSheetFragment.PHOTO_KEY) { _, bundle ->
            when (bundle.getInt(PhotoBottomSheetFragment.BUNDLE_PHOTO_KEY)) {
                PhotoBottomSheetFragment.MAKE_PHOTO_INTENT_ID -> {
                    setPictureFromCamera()
                }

                PhotoBottomSheetFragment.CHOOSE_PHOTO_INTENT_ID -> {
                    setPictureFromGallery()
                }
            }
        }
        setFragmentResultListener(OccupationBottomSheetFragment.OCCUPATION_KEY) { _, bundle ->
            val result = bundle.getString(OccupationBottomSheetFragment.BUNDLE_OCCUPATION_KEY)
            binding.textOccupation.setText(result)
        }
        binding.textSurname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                binding.layoutSurname.clearFocus()
                true
            } else false
        }
    }

    private fun setPictureFromCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                val alertDialogBuilder: AlertDialog.Builder =
                    AlertDialog.Builder(requireContext())
                alertDialogBuilder
                    .setTitle(getString(R.string.ask_notification_title))
                    .setMessage(getString(R.string.ask_notification_message))
                    .setNegativeButton(getString(R.string.ask_notification_decline)) { _, _ -> }
                    .setPositiveButton(getString(R.string.ask_notification_accept)) { _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                val dialog: AlertDialog = alertDialogBuilder.create()
                dialog.show()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        } else {
            val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            photoIntent.resolveActivity(requireContext().packageManager)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        BuildConfig.APPLICATION_ID + PROVIDER_PATHS_POSTFIX,
                        it
                    )
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startCameraForResultLauncher?.launch(photoIntent)
                }
            }

        }
    }

    private fun setActivityResults() {
        startGalleryForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val data: Intent? = result.data
                val imageUri = data?.data
                try {
                    Glide.with(requireContext())
                        .load(imageUri)
                        .transform(CircleCrop())
                        .into(binding.circularProfilePhoto)
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }
        startCameraForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Glide.with(requireContext())
                    .load(savedPhotoPath)
                    .transform(CircleCrop())
                    .into(binding.circularProfilePhoto)
            }
        }
    }

    private fun setPictureFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startGalleryForResultLauncher?.launch(galleryIntent)
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            getString(R.string.img_prefix, timeStamp),
            getString(R.string.suffix_img),
            storageDir
        ).apply { savedPhotoPath = absolutePath }
    }

    private fun setStateObserver() {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseStates.Success -> {
                    binding.textName.setText(state.data.name)
                    binding.textSurname.setText(state.data.surname)
                    binding.textOccupation.setText(state.data.occupation)
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_DATA
                }

                is ResponseStates.Failure -> {
                    binding.unexpectedErrorDescription.text = state.e.getError()
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_ERROR
                }

                is ResponseStates.Loading -> {
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_LOADING
                }
            }
        }
    }
}