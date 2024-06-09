package com.xc.brainstore.view.me.editprofile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xc.brainstore.R
import com.xc.brainstore.data.remote.response.UserDetailResponse
import com.xc.brainstore.databinding.FragmentEditMeBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.utils.reduceFileImage
import com.xc.brainstore.utils.uriToFile
import com.xc.brainstore.view.ViewModelFactory
import com.xc.brainstore.view.me.MeViewModel

class EditMeFragment : Fragment() {
    private var _binding: FragmentEditMeBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private val repository by lazy { Injection.provideRepository(requireActivity()) }

    private val meViewModel: MeViewModel by lazy {
        val factory = ViewModelFactory(repository)
        ViewModelProvider(this, factory)[MeViewModel::class.java]
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast(getString(R.string.permission_granted))
            } else {
                showToast(getString(R.string.permission_denied))
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMeBinding.inflate(inflater, container, false)
        setupAction()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meViewModel.getUserDetail()
        observeViewModel()
    }

    private fun observeViewModel() {
        meViewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
            userDetail?.let {
                updateUI(userDetail)
            }
        }

        meViewModel.message.observe(viewLifecycleOwner) { message ->
            message?.let {
                if (it.isNotEmpty()) {
                    if (it.contains("updated", true)) {
                        showToast(it)
                        meViewModel.getUserDetail()
                    } else {
                        showToast(it)
                    }
                    meViewModel.clearMessage()
                }
            }
        }
    }

    private fun setupAction() {
        val prevPageIcon = binding.previousPageIcon
        val textName = binding.edEditMeUsername
        val textPhoneNum = binding.edEditMePhoneNum
        val textAddress = binding.edEditMeAddress
        val changeImgButton = binding.changeImageButton
        val editDataButton = binding.editButton
        val updateDataButton = binding.updateButton

        prevPageIcon.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        changeImgButton.setOnClickListener {
            if (!allPermissionsGranted()) {
                showToast(getString(R.string.req_stor_permission))
                AlertDialog.Builder(requireActivity()).apply {
                    setTitle(getString(R.string.req_permission))
                    setMessage(getString(R.string.stor_need_permission))
                    setPositiveButton(getString(R.string.open_setting)) { _, _ ->
                        showPermissionSettings()
                    }
                    setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        requestPermissionLauncher.launch(REQUIRED_PERMISSION)
                    }
                    show()
                }
            } else {
                startGallery()
            }
        }

        editDataButton.setOnClickListener {
            textName.isEnabled = true
            textPhoneNum.isEnabled = true
            textAddress.isEnabled = true
            changeImgButton.visibility = View.VISIBLE
            editDataButton.visibility = View.GONE
            updateDataButton.visibility = View.VISIBLE
        }

        updateDataButton.setOnClickListener {
            val userImg = currentImageUri?.let { uriToFile(it, requireContext()).reduceFileImage() }
            val userName = binding.edEditMeUsername.text.toString()
            val userPhone = binding.edEditMePhoneNum.text.toString()
            val userAddress = binding.edEditMeAddress.text.toString()

            if (userImg != null) {
                meViewModel.putUserDetail(userImg, userName, userPhone, userAddress)
            } else {
                meViewModel.putUserDetailWithoutImg(userName, userPhone, userAddress)
            }

            textName.isEnabled = false
            textPhoneNum.isEnabled = false
            textAddress.isEnabled = false
            changeImgButton.visibility = View.GONE
            updateDataButton.visibility = View.GONE
            editDataButton.visibility = View.VISIBLE
        }
    }

    private fun updateUI(userDetail: UserDetailResponse) {
        Glide.with(this)
            .load(userDetail.userImg)
            .into(binding.userProfilePicture)
        binding.edEditMeUsername.text =
            Editable.Factory.getInstance().newEditable(userDetail.userName ?: "")
        binding.edEditMeEmail.text =
            Editable.Factory.getInstance().newEditable(userDetail.userEmail ?: "")
        binding.edEditMePhoneNum.text =
            Editable.Factory.getInstance().newEditable(userDetail.userPhone ?: "")
        binding.edEditMeAddress.text =
            Editable.Factory.getInstance().newEditable(userDetail.userAddress ?: "")
    }

    private fun showPermissionSettings() {
        Intent().also {
            it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            it.data = uri
            startActivity(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.userProfilePicture.setImageURI(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        val activity = requireActivity()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val REQUIRED_PERMISSION = if (Build.VERSION.SDK_INT >= 33) {
            Manifest.permission.ACCESS_MEDIA_LOCATION
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }
}
