package com.example.singleactivityapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.singleactivityapp.R
import com.example.singleactivityapp.databinding.FragmentPermissionsBinding
import com.example.singleactivityapp.navigateTo
import androidx.core.net.toUri


class PermissionsFragment : Fragment() {

    private var _binding: FragmentPermissionsBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            navigateTo(PermissionsFragmentDirections.actionPermissionsFragmentToHomeFramgent())
        } else {
            binding.messageTextView.text = "Permissions denied. Please grant them in settings."
            binding.messageTextView.visibility = View.VISIBLE
            binding.requestPermissionButton.isEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adjust UI based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.requestPermissionButton.visibility = View.GONE
            binding.manageStorageButton.visibility = View.VISIBLE
            binding.manageStorageButton.setOnClickListener {
                if (!Environment.isExternalStorageManager()) {
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    startActivity(
                        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
                    )
                }
            }
        } else {
            binding.requestPermissionButton.visibility = View.VISIBLE
            binding.manageStorageButton.visibility = View.GONE
            binding.requestPermissionButton.setOnClickListener {
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissionsLauncher.launch(permissions)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                navigateTo(PermissionsFragmentDirections.actionPermissionsFragmentToHomeFramgent())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}