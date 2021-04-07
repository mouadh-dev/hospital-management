package com.example.stagepfe.Doctor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.stagepfe.R


class ProfilDoctorFragment : Fragment() {
    private var TVfullNameDoctorProfil : TextView? = null
    private var TVtelephoneDoctorProfil: TextView? = null
    private var TVNaissDoctorProfil: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      var view= inflater.inflate(R.layout.fragment_profil_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        TVfullNameDoctorProfil = view.findViewById(R.id.TVfullNameDoctorProfil)
        TVtelephoneDoctorProfil = view.findViewById(R.id.TVtelephoneDoctorProfil)
        TVNaissDoctorProfil = view.findViewById(R.id.TVNaissDoctorProfil)
    }

}