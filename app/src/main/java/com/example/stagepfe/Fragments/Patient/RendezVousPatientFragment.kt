package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.Adapters.Patients.MyAdapterRdvPatient
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao

import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem


class RendezVousPatientFragment : Fragment() {
    var listRDVPatient: ListView? = null
    var list = mutableListOf<ModelRDVPatient>()
    var namePatient: String? = null
    var adapter: MyAdapterRdvPatient? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_rendez_vous, container, false)

initView(view)
        return view
    }

    private fun initView(view: View) {
        listRDVPatient = view.findViewById<ListView>(R.id.List_RDV)
        initAdapter()
        var userdao = UserDao()

       // list.add(ModelRDVPatient("11/05/2021","11:00", "termineé", R.color.green))
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        userdao.getAppointment(object : AppointmentCallback {
            override fun successAppointment(appointment: Appointment) {
                list.clear()
                userdao.retrieveCurrentDataUser(
                    object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {

                            var id = userItem.id
                            if (appointment.idPatient!! == id) {
                                userdao.populateSearch(object : UserCallback {
                                    override fun onSuccess(user: UserItem) {
                                       if (appointment.idDoctor.equals(user.id)){
                                           if (appointment.FinishOrNot.equals("Pas encore")) {
                                               list.add(
                                                   ModelRDVPatient(
                                                       appointment.date.toString(),
                                                       appointment.hour.toString(),
                                                       appointment.FinishOrNot.toString(),
                                                       R.color.green,
                                                       user.nom + " " + user.prenom
                                                   )
                                               )
                                               adapter!!.notifyDataSetChanged()
                                           }
                                           else {
                                               list.add(
                                                   ModelRDVPatient(
                                                       appointment.date.toString(),
                                                       appointment.hour.toString(),
                                                       appointment.FinishOrNot.toString(),
                                                       R.color.red,
                                                       user.nom + " " + user.prenom
                                                   )
                                               )
                                               adapter!!.notifyDataSetChanged()

                                           }
                                       }
                                    }

                                    override fun failure() {
                                    }
                                })

                            }

                        }

                        override fun failure() {
                        }

                    })


            }

            override fun failureAppointment() {}
        })



    }

    private fun initAdapter() {
         adapter = MyAdapterRdvPatient(requireContext(),R.layout.rdv_list_patient,list)
        listRDVPatient!!.adapter = adapter
    }


}