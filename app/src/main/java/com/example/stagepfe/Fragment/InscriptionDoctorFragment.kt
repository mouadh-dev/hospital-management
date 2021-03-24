package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R
    private var Specialité: Spinner? = null
    private var BioDoctor: EditText? = null
    private var ButtonReturn: Button? = null
    private var ButtonFinish: Button? = null

class InscriptionDoctorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_inscription_doctor, container, false)
        initView(view)
        return  view
    }
    private fun initView(view: View) {
        Specialité = view.findViewById(R.id.Specialité)
        BioDoctor = view.findViewById(R.id.BioDoctor)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonDoctorInscription)
        ButtonFinish = view.findViewById<Button>(R.id.FinishInscriptionDoctor)

        ButtonReturn!!.setOnClickListener {
            var ChoosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }
        ButtonFinish!!.setOnClickListener {

            if (BioDoctor!!.text.isEmpty()) {
                Toast.makeText(context, "le champ est obligatoire", Toast.LENGTH_SHORT)
                    .show()
            } else {
                requireActivity().run {
                    var intent = Intent(this, AuthenticationFragmentContainerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}