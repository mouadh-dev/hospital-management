package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelRdvDoctor
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem


class ProfilDoctorMyRdvFragment : Fragment() {
    var listviewDoctorProfilRdv: ListView? = null
    var listDoctorProfilRdv = mutableListOf<ModelRdvDoctor>()
    var nameDoctor: String? = null

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

////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////

        userdao.getAppointment(object : AppointmentCallback {
            override fun successAppointment(appointment: Appointment) {
                userdao.retrieveCurrentDataUser(
                    object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            var nameDoctor = userItem.nom + " " + userItem.prenom
                            println("mouadh" + nameDoctor)
                            if (appointment.nameDoctor!!.equals(nameDoctor)) {
                                if (appointment.FinishOrNot.equals("Pas encore")) {
                                    listDoctorProfilRdv.add(
                                        ModelRdvDoctor(
                                            appointment.date.toString(),
                                            appointment.hour.toString(),
                                            appointment.FinishOrNot.toString(),
                                            R.color.green,
                                            appointment.namePatient.toString()
                                        )
                                    )
                                    context?.let {
                                        listviewDoctorProfilRdv!!.adapter = MyAdapterRdvDoctor(
                                            requireContext(),
                                            R.layout.list_rdv_for_doctor,
                                            listDoctorProfilRdv
                                        )
                                    }
                                } else {
                                    listDoctorProfilRdv.add(
                                        ModelRdvDoctor(
                                            appointment.date.toString(),
                                            appointment.hour.toString(),
                                            appointment.FinishOrNot.toString(),
                                            R.color.red,
                                            appointment.namePatient.toString()
                                        )
                                    )
                                    listviewDoctorProfilRdv!!.adapter = MyAdapterRdvDoctor(
                                        requireActivity(),
                                        R.layout.list_rdv_for_doctor,
                                        listDoctorProfilRdv
                                    )
                                }
                            }

                        }

                        override fun failure() {

                        }

                    })


            }

            override fun failureAppointment() {}
        })
    }


}