package com.xc.brainstore.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xc.brainstore.databinding.FragmentDetailProductBinding

class DetailProductFragment : Fragment() {
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!
//    private val viewModel: DetailProductViewModel by viewModels()
//
//    private val detailProductViewModel: DetailProductViewModel by lazy {
//        ViewModelProvider(this)[DetailProductViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}