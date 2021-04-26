package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.stagepfe.Adapters.Doctor.MyAdapterPatientListForDoctorProfil
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelPatientListForDoctorProfil
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.User
import com.example.stagepfe.entite.UserItem

class ProfilDoctorMyPatientFragment : Fragment() {
    var showMore: TextView?= null
    var listviewDoctorProfilMyPatient: ListView? = null
    var listDoctorDoctorProfilMyPatient = mutableListOf<ModelPatientListForDoctorProfil>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=  inflater.inflate(R.layout.fragment_profil_doctor_my_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        showMore=view.findViewById<TextView>(R.id.TVShowMore)

        listviewDoctorProfilMyPatient =view.findViewById<ListView>(R.id.listPatientForProfilDoctor)
/////////////////////////////////////////////////////////////////////////////////////////////////////
        var userdao = UserDao()
        var userItem = UserItem()
        userdao.getAppointment(userItem, object : AppointmentCallback {
            override fun successAppointment(appointment: Appointment) {
                userdao.retrieveDataUser(requireActivity(),
                    UserItem(),
                    object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {

                                        var nameDoctor = userItem.nom + " " + userItem.prenom
                                        if (appointment.nameDoctor!!.equals(nameDoctor) || nameDoctor != appointment.namePatient) {

                                            listDoctorDoctorProfilMyPatient.add(
                                                ModelPatientListForDoctorProfil(
                                                    appointment.namePatient.toString(),
                                                    R.drawable.logopatient
                                                )
                                            )
                                            listviewDoctorProfilMyPatient!!.adapter =
                                                MyAdapterPatientListForDoctorProfil(
                                                    requireContext(),
                                                    R.layout.list_patient_for_doctor_profil,
                                                    listDoctorDoctorProfilMyPatient
                                                )

                                        }




                        }

                        override fun failure() {
                        }

                    })


            }

            override fun failureAppointment() {}
        })


        //showMore!!.setOnClickListener {
            //requireActivity().run {
                //var intent =
                 //   Intent(this, ShowInfoPatientForDoctorActivity::class.java)
               // startActivity(intent)
             //   finish()
           // }

        //}
    }


}