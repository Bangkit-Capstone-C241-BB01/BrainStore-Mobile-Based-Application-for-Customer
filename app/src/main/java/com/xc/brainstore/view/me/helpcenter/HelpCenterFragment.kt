package com.xc.brainstore.view.me.helpcenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xc.brainstore.R
import com.xc.brainstore.databinding.FragmentHelpCenterBinding

class HelpCenterFragment : Fragment() {
    private var _binding: FragmentHelpCenterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help_center, container, false)
    }

}