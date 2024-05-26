package com.xc.brainstore.view.me.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.xc.brainstore.databinding.FragmentEditMeBinding

class EditMeFragment : Fragment() {
    private var _binding: FragmentEditMeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMeBinding.inflate(inflater, container, false)
        setupAction()

        return binding.root
    }

    private fun setupAction() {
        val editAndUpdateDataButton: Button = binding.editAndUpdateButton
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}