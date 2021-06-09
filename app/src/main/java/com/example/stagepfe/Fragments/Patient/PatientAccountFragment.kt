package com.example.stagepfe.Fragments.Patient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Patients.CheckRDVPatientActivity
import com.example.stagepfe.Adapters.Patients.MyAdapterPostPatient
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.LikePost
import com.example.stagepfe.entite.Publication
import java.util.ArrayList

class PatientAccountFragment : Fragment() {
private var rdvImage: ImageView?=null
private var ambulanceImage: ImageView?=null
    var adapterPostPatient : MyAdapterPostPatient? = null
    var listViewPostPatient: ListView? = null
    var listPostPatient = ArrayList<Publication>()
    var userDao = UserDao()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_patient_account, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        rdvImage=view.findViewById(R.id.RDVImg)
        ambulanceImage=view.findViewById(R.id.ambulance)
        listViewPostPatient = view.findViewById(R.id.list_publication_patient)
        initAdapter()

        rdvImage!!.setOnClickListener {
            requireActivity().run {
                var intent =
                    Intent(this, CheckRDVPatientActivity::class.java)
                startActivity(intent)
//                finish()
            }
        }
        ambulanceImage!!.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + 198)
            startActivity(dialIntent)
        }
        adapterPostPatient!!.clear()
//        userDao.getPost(object : PostCallback {
//            override fun successPost(publication: LikePost) {
//                listPostPatient.add(publication)
//                adapterPostPatient!!.notifyDataSetChanged()
//            }
//
//            override fun failurePost() {
//            }
//        })

    }

    private fun initAdapter() {
        adapterPostPatient = MyAdapterPostPatient(requireContext(),R.layout.list_publication_patient,listPostPatient)
        listViewPostPatient!!.adapter = adapterPostPatient
    }

}



