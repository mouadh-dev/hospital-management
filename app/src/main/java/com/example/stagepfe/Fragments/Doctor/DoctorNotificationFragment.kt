package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.Adapters.Doctor.MyAdapterNotificationDoctor
import com.example.stagepfe.Adapters.Patients.MyAdapterNotificationPatient
import com.example.stagepfe.Dao.NotificationCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelNotificationPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Notification
import com.example.stagepfe.entite.UserItem


class DoctorNotificationFragment : Fragment() {
    var listviewNoti: ListView? = null
    var listNoti = mutableListOf<ModelNotification>()
    var adapterNotificationDoctor: MyAdapterNotificationDoctor? = null
    var userDao = UserDao()
    var currentUser: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_doctor_notification, container, false)


        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewNoti =view.findViewById<ListView>(R.id.listNotificationDocteur)
        initAdapter()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                currentUser = userItem.id
                userDao.getNotification(object : NotificationCallback {
                    override fun successNotification(notification: Notification?) {
                        if (notification!!.idDoctor.equals(currentUser)){
                            if (notification.type.equals("appointment")){
                                listNoti.add(
                                    ModelNotification("Vous avez un nouveau rendez-vous",R.drawable.date,
                                        notification.timeNotification!!.substring(1, 5)
                                    )
                                )
                                adapterNotificationDoctor!!.notifyDataSetChanged()

                            }
                            listNoti.sortWith(Comparator { o1, o2 -> o1.timeNotificationDoctor.compareTo(o2.timeNotificationDoctor) })
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
        adapterNotificationDoctor =
            MyAdapterNotificationDoctor(requireContext(), R.layout.notification_doctor_list, listNoti)
        listviewNoti!!.adapter = adapterNotificationDoctor
    }
}