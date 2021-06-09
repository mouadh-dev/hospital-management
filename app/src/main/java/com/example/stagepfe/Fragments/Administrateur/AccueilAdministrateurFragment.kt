package com.example.stagepfe.Fragments.Administrateur

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Adapters.Administrateur.MyAdapterPostAdmin
import com.example.stagepfe.Adapters.Doctor.MyAdapterPostDoctor
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication
import com.example.stagepfe.entite.UserItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList


class AccueilAdministrateurFragment : Fragment() {
    var postButtonAdmin: Button? = null
    var postTextAdmin: EditText? = null
    var imagePostAdmin : ImageView? = null
    var imageToPostAdmin : ImageView? = null
    var adapterPostAdmin : MyAdapterPostAdmin? = null
    var listViewPostAdmin: ListView? = null
    var listPostAdmin = ArrayList<Publication>()
    var userDao = UserDao()
    var imageUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        postButtonAdmin = view.findViewById(R.id.Post_button_administrateur)
        postTextAdmin = view.findViewById(R.id.post_text_administrateur)
        imagePostAdmin = view.findViewById(R.id.image_post_administrateur)
        imageToPostAdmin = view.findViewById(R.id.image_to_post_admin)
        listViewPostAdmin = view.findViewById(R.id.list_publication_admin)
        initAdapter()

        imagePostAdmin!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
        }

        postButtonAdmin!!.setOnClickListener {
            userDao.retrieveCurrentDataUser(object : UserCallback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(userItem: UserItem) {
                    if (postTextAdmin!!.text.isEmpty()) {
                        postButtonAdmin!!.isClickable = false
                    } else {
                        postButtonAdmin!!.isClickable = true
                        var post = Publication()
                        post.datePublication = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                        post.heurePublication = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                        post.idsenderPublication = userItem.id
                        post.textPublication = postTextAdmin!!.text.toString().trim()

                        userDao.sendPost(post)
                        postTextAdmin!!.text.clear()
                        imageToPostAdmin!!.visibility = View.GONE
                    }


                }

                override fun failure() {
                }
            })

        }

        adapterPostAdmin!!.clear()
        userDao.getPost(object : PostCallback {
            override fun successPost(publication: Publication) {
                listPostAdmin.add(publication)
                adapterPostAdmin!!.notifyDataSetChanged()
            }

            override fun failurePost() {
            }
        })

    }

    private fun initAdapter() {
        adapterPostAdmin = MyAdapterPostAdmin(requireContext(),R.layout.list_publication_administrateur,listPostAdmin)
        listViewPostAdmin!!.adapter = adapterPostAdmin
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
            imageToPostAdmin!!.setImageURI(imageUri)
            imageUri = data?.data
            imageToPostAdmin!!.setImageURI(imageUri)
            imageToPostAdmin!!.visibility = View.VISIBLE

        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}