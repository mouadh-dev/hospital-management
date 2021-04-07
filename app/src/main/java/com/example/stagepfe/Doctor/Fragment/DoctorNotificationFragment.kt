package com.example.stagepfe.Doctor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Doctor.ModelNotification
import com.example.stagepfe.Doctor.MyAdapterNotification
import com.example.stagepfe.Patient.MyAdapter
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
        listviewNoti!!.adapter = MyAdapterNotification(requireContext(), R.layout.notification_doctor_list, listNoti)
        initView(view)
        return view
    }

    private fun initView(view: View) {

    }
}