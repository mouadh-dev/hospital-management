package com.example.stagepfe.Patient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.stagepfe.R


class HomePatientFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_patient, container, false)

//        var patientAccount = PatientAccountFragment()
//        childFragmentManager.beginTransaction().replace(
//            R.id.containerhomePatientFragment,
//            patientAccount
//        ).commit()
//
//        var ft: FragmentTransaction = getFragmentManager()?.beginTransaction() ?:
//        ft.replace(R.id.containerhomePatientFragment, patientAccount)
//        ft.commit()


    }


}