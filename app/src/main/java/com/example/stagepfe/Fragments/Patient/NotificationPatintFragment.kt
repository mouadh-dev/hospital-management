package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Models.Patient.ModelNotificationPatient
import com.example.stagepfe.Adapters.Patients.MyAdapterNotificationPatient
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem
import java.util.*
import kotlin.Comparator


class NotificationPatintFragment : Fragment() {


    var listNotificationPatient: ListView? = null
    var list = mutableListOf<ModelNotificationPatient>()
    var adapterNotification: MyAdapterNotificationPatient? = null
    var userDao = UserDao()
    var currentUser:String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_notification_patint, container, false)
        listNotificationPatient = view.findViewById<ListView>(R.id.Notification_patientList)
        initAdapter()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                currentUser = userItem.id
                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(user: UserItem) {
                        var rapports = user.rapports
                        if (rapports != null) {
                            for (rapport in rapports) {
                                var rapportValue = rapport.value
                                if (rapportValue.idPatientRapport.equals(currentUser)) {
                                    list.add(
                                        ModelNotificationPatient(
                                            R.drawable.date,
                                            "Vous avez un nouveau rapport de le part " + user.prenom + " " + user.nom,
                                            rapportValue.hourRapport!!.substring(1, 5)
                                        )
                                    )
                                    adapterNotification!!.notifyDataSetChanged()
                                }
                            }
                        }

                        var ordonances = user.ordonance
                        if (ordonances != null) {
                            for (ordonance in ordonances) {
                                var ordonanceValue = ordonance.value
                                if (ordonanceValue.idPatient.equals(currentUser)) {
                                    if (ordonanceValue.typeOrdonnance.equals("Ordonnance médicament")){
                                        list.add(
                                            ModelNotificationPatient(
                                                R.drawable.ordonance,
                                                "Vous avez un nouveau rapport de le part " + user.prenom + " " + user.nom,
                                                ordonanceValue.hourOrdonanceSend!!.substring(1, 5)
                                            )
                                        )
//                                        Collections.sort(list,object :Comparator<ModelNotificationPatient?>{
//                                            override fun compare(
//                                                o1: ModelNotificationPatient?,
//                                                o2: ModelNotificationPatient?
//                                            ): Int {
//                                                return o1.imgNotificationPatient.compareTo(o2.imgNotificationPatient)
//                                            }
//                                        })
//                                        System.currentTimeMillis()
                                        adapterNotification!!.notifyDataSetChanged()
                                    }else{
                                        list.add(
                                            ModelNotificationPatient(
                                                R.drawable.logoanalyse,
                                                "Vous avez un nouveau rapport de le part " + user.prenom + " " + user.nom,
                                                ordonanceValue.hourOrdonanceSend!!.substring(1, 5)
                                            )
                                        )
                                        adapterNotification!!.notifyDataSetChanged()
                                    }

                                }
                            }

                            userDao.getAppointment(object : AppointmentCallback {
                                override fun successAppointment(appointment: Appointment) {
                                    if (user.id.equals(appointment.idPatient)){
                                        list.add(
                                            ModelNotificationPatient(
                                                R.drawable.date,
                                                "Vous avez un nouveau rendez_vous de le part " + user.prenom + " " + user.nom + " " + "à la date " + appointment.date,
                                                appointment.hour!!
                                            )
                                        )
                                        adapterNotification!!.notifyDataSetChanged()
                                    }
                                }

                                override fun failureAppointment() {

                                }

                            })
                        }

                    }

                    override fun failure() {
                    }
                })
            }

            override fun failure() {
            }
        })



//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
//        list.add(ModelNotificationPatient(R.drawable.date,"notification"))



        return view
    }

    private fun initAdapter() {
        adapterNotification = MyAdapterNotificationPatient(requireContext(),R.layout.notification_patient,list)
        listNotificationPatient!!.adapter = adapterNotification
    }


}