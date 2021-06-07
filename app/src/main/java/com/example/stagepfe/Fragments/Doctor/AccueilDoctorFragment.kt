package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Adapters.Administrateur.NewUsersAdapterAdmin
import com.example.stagepfe.Adapters.Doctor.MyAdapterPostDoctorclass
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.DatePickerTimeline.OnDateSelectedListener
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


open class AccueilDoctorFragment : Fragment() {
    var timeLine: DatePickerTimeline? = null
    var postButton: Button? = null
    var postText:EditText? = null
    var userDao = UserDao()
    var adapterPost :MyAdapterPostDoctorclass? = null
    var listViewPost:ListView? = null
    var listPost = ArrayList<Publication>()

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
        listViewPost = view.findViewById(R.id.list_publication_doctor)
        initAdapter()



        timeLine!!.setDateLabelAdapter(DateLabelAdapter { calendar, index ->
            Integer.toString(calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR] % 2000
        })
        timeLine!!.setOnDateSelectedListener { year, month, day, index ->

            requireActivity().run {
               var intent = Intent(this, CheckRDVActivity::class.java)
                var months = month+1
                intent.putExtra("key", "Veuillez choisir l'heure du rendez-vous pour $day-$months-$year")
                intent.putExtra("keyday", "$day")
                intent.putExtra("keymonth", "$months")
                intent.putExtra("keyyear", "$year")
                startActivity(intent)
//                finish() // If activity no more needed in back stack
            }
        }

        postButton!!.setOnClickListener {
            userDao.retrieveCurrentDataUser(object : UserCallback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(userItem: UserItem) {
                    if (postText!!.text.isEmpty()){
                        postButton!!.isClickable = false
                    }else{
                        postButton!!.isClickable = true
                        var post = Publication()
                        post.datePublication = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                        post.heurePublication = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                        post.idsenderPublication = userItem.id
                        post.textPublication = postText!!.text.toString().trim()
                        userDao.sendPost(post)
                        postText!!.text.clear()
                    }


                    }
                override fun failure() {
                }
            })

        }

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
        adapterPost = MyAdapterPostDoctorclass(requireContext(),R.layout.list_publication_doctor,listPost)
        listViewPost!!.adapter = adapterPost
    }


}

