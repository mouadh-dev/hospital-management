package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterPostDoctor
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.LikePost
import com.example.stagepfe.entite.Publication
import java.util.ArrayList

class PostDoctorActivity : AppCompatActivity() {
    var adapterPost : MyAdapterPostDoctor? = null
    var listViewPost: ListView? = null
    var listPost = ArrayList<Publication>()
    var userDao = UserDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_doctor)
        initView()
    }

    private fun initView() {
        listViewPost = findViewById(R.id.list_publication_doctor)
        initAdapter()


        adapterPost!!.clear()
        userDao.getPost(object : PostCallback {
            override fun successPost(publication: Publication) {
                listPost.add(publication)
                adapterPost!!.notifyDataSetChanged()

            }

            override fun failurePost() {
            }
        })
    }

    private fun initAdapter() {
        adapterPost = MyAdapterPostDoctor(this,R.layout.list_publication_doctor,listPost)
        listViewPost!!.adapter = adapterPost
    }
}