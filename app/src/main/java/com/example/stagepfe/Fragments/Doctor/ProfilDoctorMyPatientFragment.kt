package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterPatientListForDoctorProfil
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelPatientListForDoctorProfil
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem


class ProfilDoctorMyPatientFragment : Fragment() {
    var showMore: TextView? = null
    var listviewDoctorProfilMyPatient: ListView? = null
    var listDoctorProfilMyPatient = mutableListOf<ModelPatientListForDoctorProfil>()
    var testOnRepeatingPatientName:String= ""
    var nameCurrentUser:String? = null
    var adapterListPatient: MyAdapterPatientListForDoctorProfil? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profil_doctor_my_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        showMore = view.findViewById<TextView>(R.id.TVShowMore)


        listviewDoctorProfilMyPatient = view.findViewById<ListView>(R.id.listPatientForProfilDoctor)

//        listDoctorProfilMyPatient = arrayListOf<ModelPatientListForDoctorProfil>()
        initAdapter()
/////////////////////////////////////////////////////////////////////////////////////////////////////
        var userdao = UserDao()

        userdao.getAppointment(object : AppointmentCallback {
            override fun successAppointment(appointment: Appointment) {
                userdao.retrieveCurrentDataUser(
                    object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {


                            nameCurrentUser = userItem.nom + " " + userItem.prenom
                            if (appointment.nameDoctor!!.equals(nameCurrentUser) && appointment.namePatient != nameCurrentUser
                                && testOnRepeatingPatientName != appointment.namePatient
                            ) {
                                testOnRepeatingPatientName = appointment.namePatient.toString()
                                listDoctorProfilMyPatient.add(
                                    ModelPatientListForDoctorProfil(
                                        testOnRepeatingPatientName,
                                        R.drawable.logopatient))
                                adapterListPatient!!.notifyDataSetChanged()
                            }
                            //
                        }
                        override fun failure() {}
                    })


            }

            override fun failureAppointment() {}
        })
        listviewDoctorProfilMyPatient!!.setOnItemClickListener { parent, view, position, id ->

//                requireActivity().run {
                    var intent = Intent(activity, ShowInfoPatientForDoctorActivity::class.java)

                    var patientNameInList = view.findViewById<TextView>(R.id.TVnamePatientListForProfilDoctor)
                    intent.putExtra("nomPatient", patientNameInList!!.text.toString())
                    startActivity(intent)
//                    finish() // If activity no more needed in back stack
//                }
        }

        //showMore!!.setOnClickListener {
        //requireActivity().run {
        //var intent =
        //   Intent(this, ShowInfoPatientForDoctorActivity::class.java)
        // startActivity(intent)
        //   finish()
        // }

        //}
    }

    private fun initAdapter() {

        adapterListPatient =
            MyAdapterPatientListForDoctorProfil(
                requireContext(),
                R.layout.list_patient_for_doctor_profil,
                listDoctorProfilMyPatient)
        listviewDoctorProfilMyPatient!!.adapter = adapterListPatient
    }


}