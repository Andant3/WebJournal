package com.example.webjournal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.webjournal.R
import com.example.webjournal.databinding.FragmentJournalBinding
import com.example.webjournal.databinding.FragmentLoginBinding
import com.example.webjournal.databinding.FragmentSignUpBinding

class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }
}