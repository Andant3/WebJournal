package com.example.webjournal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.Visibility
import com.example.webjournal.R
import com.example.webjournal.databinding.FragmentJournalBinding
import com.example.webjournal.databinding.FragmentLoginBinding
import com.example.webjournal.databinding.FragmentSignUpBinding
import com.example.webjournal.model.Journal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding

    // Firebase references
    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var user : FirebaseUser

    private var dataBase = FirebaseFirestore.getInstance()
    private var collectionReference: CollectionReference =
        dataBase.collection("Journal")

    private lateinit var storageReference : StorageReference

    private lateinit var journalList: List<Journal>
    private lateinit var journalRecyclerAdapter: JournalRecyclerAdapter

    private lateinit var noPagesLayout: ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseAuth = FirebaseAuth.getInstance()
        user = fireBaseAuth.currentUser!!
        storageReference = FirebaseStorage.getInstance().reference

        noPagesLayout = binding.noPagesLayout
        noPagesLayout.visibility = View.INVISIBLE
    }
}