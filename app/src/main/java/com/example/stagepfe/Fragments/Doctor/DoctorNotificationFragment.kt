package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.Adapters.Doctor.MyAdapterNotificationDoctor
import com.example.stagepfe.R


class DoctorNotificationFragment : Fragment() {
    var listviewNoti: ListView? = null
    var listNoti = mutableListOf<ModelNotification>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_doctor_notification, container, false)
        listviewNoti =view.findViewById<ListView>(R.id.listNotificationDocteur)
        listNoti.add(ModelNotification("Un patient a annulé son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Nouvelle annonce aujourd'hui ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listNoti.add(ModelNotification("Un patient a rapporté son rendez vous ",R.drawable.date))
        listviewNoti!!.adapter = MyAdapterNotificationDoctor(requireContext(), R.layout.notification_doctor_list, listNoti)
        initView(view)
        return view
    }

    private fun initView(view: View) {

    }
}