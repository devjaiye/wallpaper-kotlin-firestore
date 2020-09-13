package com.dev_app.firestorewallify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_detail)

        homeBtn.visibility = View.GONE
        //nextBtn.visibility = View.GONE
        //prevBtn.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE

        val ref = intent.getStringExtra("ref")

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("wallpapers").child(ref)
        val imageList: ArrayList<DetailItem> = ArrayList()

        //if need vertical orientation
//        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        val listAllTask: Task<ListResult> = storageRef.listAll()
        listAllTask.addOnCompleteListener { result ->
            val items: List<StorageReference> = result.result!!.items
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {url->
                    Log.d("storage", "pos: $index, url: $url")
                    imageList.add(DetailItem(index, url.toString()))
                    //sort if need
                    imageList.sortBy { detailItem ->
                        detailItem.pos
                    }

                    Picasso.get().load(url).fetch(object : Callback {
                        override fun onSuccess() {
                            Log.d("Load", "success $index")
                            if (index == imageList.size - 1) {
                                Log.d("Load", "All ${imageList.size} elements is load!")
                                homeBtn.visibility = View.VISIBLE
                               // nextBtn.visibility = View.VISIBLE
                                //prevBtn.visibility = View.VISIBLE
                                loadingLayout.visibility = View.GONE

                                viewPager.adapter = ViewPagerAdapter(imageList, this@DetailActivity)
                            }
                        }

                        override fun onError(e: Exception?) {
                            e!!.printStackTrace()
                            Toast.makeText(this@DetailActivity, "Error! Check internet connection!", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }

        /*
        //next slide
        nextView.setOnClickListener {
            viewPager.setCurrentItem(getItem(+1), true)
        }
        //prev slide
        prevView.setOnClickListener {
            viewPager.setCurrentItem(getItem(-1), true)
        }
         */



        //go home
        homeBtn.setOnClickListener {
            finish()
        }
    }

    private fun getItem(i: Int): Int {
        return viewPager.currentItem + i
    }
}