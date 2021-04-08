package com.example.stagepfe.Patient.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Patient.ModelMessagePatient
import com.example.stagepfe.Patient.ModelNotificationPatient
import com.example.stagepfe.Patient.MyAdapterNotificationPatient
import com.example.stagepfe.R


class NotificationPatintFragment : Fragment() {


    var listNotificationPatient: ListView? = null
    var list = mutableListOf<ModelNotificationPatient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_notification_patint, container, false)
        listNotificationPatient = view.findViewById<ListView>(R.id.Notification_patientList)
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))
        list.add(ModelNotificationPatient(R.drawable.date,"notification"))

        listNotificationPatient!!.adapter = MyAdapterNotificationPatient(requireContext(),R.layout.notification_patient,list)

        return view
    }


}