package com.xc.brainstore.view.me.helpcenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xc.brainstore.databinding.FragmentHelpCenterBinding

class HelpCenterFragment : Fragment() {
    private lateinit var binding: FragmentHelpCenterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpCenterBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE
        return binding.root
    }

}