package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Patients.MyAdapterNotificationPatient
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.NotificationCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelNotificationPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.Notification
import com.example.stagepfe.entite.UserItem
import java.util.*
import kotlin.Comparator


class NotificationPatintFragment : Fragment() {


    var listNotificationPatient: ListView? = null
    var list = mutableListOf<ModelNotificationPatient>()
    var adapterNotification: MyAdapterNotificationPatient? = null
    var userDao = UserDao()
    var currentUser: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_notification_patint, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listNotificationPatient = view.findViewById<ListView>(R.id.Notification_patientList)
        initAdapter()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                currentUser = userItem.id
                userDao.getNotification(object : NotificationCallback {
                    override fun successNotification(notification: Notification?) {
                        if (notification!!.idPatient.equals(currentUser)){
                            if (notification.type.equals("rapport")){
                                list.add(
                                    ModelNotificationPatient(
                                        R.drawable.date,
                                        "Vous avez un nouveau rapport ",
                                        notification.timeNotification!!.substring(1, 5)
                                    )
                                )
                                adapterNotification!!.notifyDataSetChanged()
                            }else if (notification.type.equals("Ordonnance médicament")){
                                list.add(
                                    ModelNotificationPatient(
                                        R.drawable.ordonance,
                                        "Vous avez un nouveau Ordonnance médicament",
                                        notification.timeNotification!!.substring(1, 5)
                                    )
                                )
                                adapterNotification!!.notifyDataSetChanged()
                            }else if (notification.type.equals("Ordonnance analyse")){
                                list.add(
                                    ModelNotificationPatient(
                                        R.drawable.logoanalyse,
                                        "Vous avez un nouveau ordonnance analyse",
                                        notification.timeNotification!!.substring(1, 5)
                                    )
                                )
                                adapterNotification!!.notifyDataSetChanged()
                            }else {
                                list.add(
                                    ModelNotificationPatient(
                                        R.drawable.date,
                                        "Vous avez un nouveau rendez_vous à la date " + notification.dateNotification,
                                        notification.timeNotification!!.substring(1, 5)
                                    )
                                )
                                adapterNotification!!.notifyDataSetChanged()
                            }
                            list.sortWith(Comparator { o1, o2 -> o1.timeNotification.compareTo(o2.timeNotification) })
                            System.currentTimeMillis()
                        }
                    }

                    override fun failureNotification() {
                    }
                })
            }

            override fun failure() {
            }
        })
    }




    private fun initAdapter() {
        adapterNotification =
            MyAdapterNotificationPatient(requireContext(), R.layout.notification_patient, list)
        listNotificationPatient!!.adapter = adapterNotification
    }


}