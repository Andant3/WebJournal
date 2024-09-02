package com.example.webjournal.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.webjournal.R
import com.example.webjournal.databinding.FragmentAddPageBinding
import com.example.webjournal.model.Journal
import com.example.webjournal.model.JournalUser
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class AddPageFragment : Fragment() {

    private lateinit var binding: FragmentAddPageBinding

    private var currentUserId: String = ""
    private var currentUserName: String = ""


    // Firebase
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    // Firestore
    var database = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference

    var collectionReference: CollectionReference = database.collection("Journal")
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        storageReference = FirebaseStorage.getInstance().reference

        binding.apply {
            loadingTextView.visibility = View.INVISIBLE

            if(JournalUser.instance != null){
                currentUserId = JournalUser.instance!!
                    .userId.toString()
                currentUserName = JournalUser.instance!!
                    .userName.toString()
            }
            addPhotoImg.setOnClickListener {
                openPickActivityForResult()
            }
        }
    }

    private suspend fun loadingTextViewAnimation(){
        val state = binding.loadingTextView.text.toString()
        while (true){
            delay(700)
            when (state) {
                "Wait..." -> {
                    binding.loadingTextView.text = "Wait"
                }

                else -> {
                    binding.loadingTextView.text = binding.loadingTextView.text.toString() + "."
                }
            }
        }
    }

    fun saveJournal() {
        var title: String = binding.titleEditText.text.toString().trim()
        var postText: String = binding.postEditText.text.toString().trim()

        binding.loadingTextView.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            loadingTextViewAnimation()
        }

        if(!TextUtils.isEmpty(title)
            && !TextUtils.isEmpty(postText)
            && imageUri != null){

            val filePath: StorageReference = storageReference
                .child("journal_images")
                .child("my_image_"+Timestamp.now().seconds)

            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl.addOnSuccessListener {
                        var imageUri: String = it.toString()
                        var timeStamp = Timestamp(Date())

                        var journal: Journal = Journal(
                            title,
                            postText,
                            imageUri,
                            currentUserId,
                            timeStamp,
                            currentUserName
                        )

                        collectionReference.add(journal)
                            .addOnSuccessListener {
                            binding.loadingTextView.visibility = View.INVISIBLE
                            findNavController().navigate(R.id.action_addPageFragment_to_journalFragment)
                        }
                    }
                }.addOnFailureListener {
                    binding.loadingTextView.visibility = View.INVISIBLE
                    CoroutineScope(Dispatchers.IO).cancel()
                }
        }else{
            binding.loadingTextView.visibility = View.INVISIBLE
            CoroutineScope(Dispatchers.IO).cancel()
        }
    }

    fun openPickActivityForResult() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if(data != null){
                imageUri = data.data!!
                binding.addPhotoImg.setImageURI(imageUri)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        user = auth.currentUser!!

    }

    override fun onStop() {
        super.onStop()
        if(auth != null){

        }
    }

}