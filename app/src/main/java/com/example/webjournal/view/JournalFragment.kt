package com.example.webjournal.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.transition.Visibility
import com.example.webjournal.R
import com.example.webjournal.databinding.FragmentJournalBinding
import com.example.webjournal.databinding.FragmentLoginBinding
import com.example.webjournal.databinding.FragmentSignUpBinding
import com.example.webjournal.model.Journal
import com.example.webjournal.model.JournalUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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

    private lateinit var journalList: MutableList<Journal>
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

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        fireBaseAuth = FirebaseAuth.getInstance()
        user = fireBaseAuth.currentUser!!
        storageReference = FirebaseStorage.getInstance().reference

        noPagesLayout = binding.noPagesLayout
        noPagesLayout.visibility = View.INVISIBLE

        binding.pagesRecyclerView.setHasFixedSize(true)
        binding.pagesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        journalList = arrayListOf<Journal>()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_journalFragment_to_addPageFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        collectionReference.whereEqualTo("userId",
            JournalUser.instance?.userId)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    it.forEach {
                        // Converting snapshots to Journal
                        var journal = it.toObject(Journal::class.java)

                        journalList.add(journal)


                        // RecyclerView
                        journalRecyclerAdapter = JournalRecyclerAdapter(requireContext(), journalList)
                        binding.pagesRecyclerView.adapter = journalRecyclerAdapter
                        journalRecyclerAdapter.notifyDataSetChanged()

                        Log.i("TAGY", "Successful collection reference JournalFragment")
                    }
                }else{
                    binding.noPagesLayout.visibility = View.VISIBLE
                }
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()

                Log.i("TAGY", "Unsuccessful collection reference JournalFragment")
            }

    }
}