package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem


class ProfilDoctorMyRdvFragment : Fragment() {
    var listviewDoctorProfilRdv: ListView? = null
    var listDoctorProfilRdv = mutableListOf<ModelRdvDocteur>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profil_doctor_my_rdv, container, false)

        initView(view)
        return view
    }

    private fun initView(view: View) {

        listviewDoctorProfilRdv = view.findViewById<ListView>(R.id.listRdvDocteur)


var userdao = UserDao()
        var appointment = Appointment()
        var userItem = UserItem()
        userdao.getAppointment(appointment,userItem,object : AppointmentCallback{
            override fun successAppointment(appointment: Appointment) {
                listDoctorProfilRdv.add(ModelRdvDocteur(appointment.date.toString(),appointment.hour.toString(),appointment.FinishOrNot.toString(),R.color.green))
                listviewDoctorProfilRdv!!.adapter = MyAdapterRdvDoctor(requireContext(),R.layout.list_rdv_for_doctor,listDoctorProfilRdv)
            }

            override fun failureAppointment() {

            }

        })



//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Terminer",R.color.green))
//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Pas encore",R.color.red))
//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Terminer",R.color.green))
//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Pas encore",R.color.red))
//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Terminer",R.color.green))
//        listDoctorProfilRdv.add(ModelRdvDocteur("12/12/2021","12:35","Pas encore",R.color.red))
//
//        listviewDoctorProfilRdv!!.adapter = MyAdapterRdvDoctor(requireContext(),R.layout.list_rdv_for_doctor,listDoctorProfilRdv)


    }

}