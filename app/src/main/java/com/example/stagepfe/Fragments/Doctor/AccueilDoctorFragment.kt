package com.example.stagepfe.Fragments.Doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
import com.example.stagepfe.Activity.Doctors.PostDoctorActivity
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


open class AccueilDoctorFragment : Fragment() {
    var timeLine: DatePickerTimeline? = null
    var postButton: Button? = null
    var postText: EditText? = null
    var userDao = UserDao()
    var goToPost: TextView? = null
    var imagePost: ImageView? = null
    var imageUri: Uri? = null

    //    lateinit var test:Uri
    var imageInPost: ImageView? = null
    var imageUserPost: ImageView? = null
    var nameUserPost: TextView? = null
    var textUserPost: TextView? = null
    var hourPost: TextView? = null
    var datePost: TextView? = null
    var likeImage: ImageView? = null
    var likeText: TextView? = null
    var commentImage: ImageView? = null
    var idPost: String? = null
    var idPoster: String? = null
    var imageShown: ImageView? = null
    var linearLayout: LinearLayout? = null
    var listUser: ArrayList<String>? = null
    var like: ArrayList<String>? = null
    var post = Publication()


    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_doctor, container, false)
        initView(view)
        return view
    }


    private fun initView(view: View) {
        timeLine = view.findViewById(R.id.time_line)
        postButton = view.findViewById(R.id.Post_button)
        postText = view.findViewById(R.id.post_text)
        imagePost = view.findViewById(R.id.image_post)
        imageInPost = view.findViewById(R.id.image_topost_dr)
        goToPost = view.findViewById(R.id.go_to_posts)
        imageUserPost = view.findViewById(R.id.Image_post__Docteur)
        nameUserPost = view.findViewById(R.id.Name_post_Docteur)
        textUserPost = view.findViewById(R.id.text_post_Docteur)
        imageShown = view.findViewById(R.id.image_to_post)
        hourPost = view.findViewById(R.id.Time_post_Docteur)
        datePost = view.findViewById(R.id.Date_post_Docteur)
        likeImage = view.findViewById(R.id.like_Image)
        likeText = view.findViewById(R.id.like_text)
        commentImage = view.findViewById(R.id.comment_image)
        linearLayout = view.findViewById(R.id.item_posts_doctor)


        userDao.getPost(object : PostCallback {
            override fun successPost(publication: Publication) {

                linearLayout!!.visibility = VISIBLE
                userDao.populateSearch(object : UserCallback {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(userItem: UserItem) {
                        if (publication.idUser.equals(userItem.id)) {
                            Glide.with(requireContext())
                                .load(userItem.profilPhotos)
                                .into(imageUserPost!!)
                            nameUserPost!!.text = userItem.nom + " " + userItem.prenom
                        }
                    }

                    override fun failure() {
                    }
                })
                Glide.with(requireContext())
                    .load(publication.imagePublication)
                    .into(imageInPost!!)
                textUserPost!!.text = publication.textPublication
                hourPost!!.text = publication.heurePublication
                datePost!!.text = publication.datePublication
                if (publication.imagePublication != "null") {
                    Glide.with(requireContext()).load(publication.imagePublication)
                        .into(imageInPost!!)
                    imageInPost!!.visibility = VISIBLE
                } else {
                    imageInPost!!.visibility = GONE
                }
//

            }

            override fun failurePost() {
            }
        })

        likeImage!!.setOnClickListener {
            userDao.getPost(object : PostCallback {
                override fun successPost(publication: Publication) {

                    userDao.retrieveCurrentDataUser(object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            for (likes in publication.likes!!) {
                                like!!.add(likes)
                                if (likes != userItem.id) {
                                    like!!.add(userItem.id!!)
                                    likeImage!!.setImageResource(R.drawable.red_like_ic)
                                    userDao.sendLike(publication.idUser!!,publication,like)
                                } else {
                                    likeImage!!.setImageResource(R.drawable.like_ic)

                                }
                            }

                        }

                        override fun failure() {
                        }
                    })
                }

                override fun failurePost() {

                }
            })


        }



        postButton!!.setOnClickListener {
            userDao.retrieveCurrentDataUser(object : UserCallback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(userItem: UserItem) {
                    var post = Publication()
                    post.datePublication = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                    post.heurePublication = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                    post.idUser = userItem.id
                    post.imagePublication = imageUri.toString()
                    post.textPublication = postText!!.text.toString().trim()
                    userDao.sendPost(post)
                    postText!!.text.clear()
                    imageShown!!.visibility = GONE
                }

                override fun failure() {
                }
            })

        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        imagePost!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
            imageUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        goToPost!!.setOnClickListener {
            var intent = Intent(requireContext(), PostDoctorActivity::class.java)
            startActivity(intent)
        }
/////////////////////////////////////time line/////////////////////////////////////////////////////
        timeLine!!.setDateLabelAdapter(DateLabelAdapter { calendar, index ->
            Integer.toString(calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR] % 2000
        })
        timeLine!!.setOnDateSelectedListener { year, month, day, index ->

            requireActivity().run {
                var intent = Intent(this, CheckRDVActivity::class.java)
                var months = month + 1
                intent.putExtra(
                    "key",
                    "Veuillez choisir l'heure du rendez-vous pour $day-$months-$year"
                )
                intent.putExtra("keyday", "$day")
                intent.putExtra("keymonth", "$months")
                intent.putExtra("keyyear", "$year")
                startActivity(intent)
//                finish() // If activity no more needed in back stack
            }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        postButton!!.isClickable = true
        postText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                postButton!!.isClickable = postText!!.text.isNotEmpty()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                postButton!!.isClickable = postText!!.text.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                postButton!!.isClickable = postText!!.text.isNotEmpty()
            }
        })
///////////////////////////////////////////////////////////////////////////////////////////////////
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
            imageShown!!.setImageURI(imageUri)
            imageShown!!.visibility = VISIBLE


        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

