package com.dev_app.firestorewallify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items: ArrayList<WallItem> = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WallAdapter(items, this)

        val db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()

        storage.maxDownloadRetryTimeMillis = 60000 // wait 1 min for download
        storage.maxOperationRetryTimeMillis = 10000 // wait 10s for normal ops

        progressBar.visibility = View.VISIBLE
        loadingTextView.visibility = View.VISIBLE

        db.collection("wallpapers").get()
            .addOnSuccessListener { documents ->
                // get all documents in collection
                for (document in documents) {
                    val wallItem = document.toObject(WallItem::class.java)
                    //get title image
                    val storageRef = storage.reference.child("wallpapers").child(document.id)

                    storageRef.list(1).addOnCompleteListener { result ->
                        val mItem: List<StorageReference> = result.result!!.items
                        //get first element from list to set on title image
                        mItem.first().downloadUrl.addOnSuccessListener {
                            Log.d("storage", "$it")
                            wallItem.image = it.toString()
                            wallItem.ref = document.id
                            wallItem.title = document["title"].toString()

                            //if need you may sort list before set adapter
                            recyclerView.adapter = adapter
                        }
                    }
                    items.add(wallItem)
                }
            }.addOnFailureListener {
                Log.d("fail", "Error getting document: $it")
            }.addOnCompleteListener {
                progressBar.visibility = View.GONE
                loadingTextView.visibility = View.GONE
            }
    }
}