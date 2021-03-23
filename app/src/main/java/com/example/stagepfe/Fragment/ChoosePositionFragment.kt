package com.example.stagepfe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.stagepfe.R

class ChoosePositionFragment : Fragment() {
    private var spinner: Spinner? = null
    private var Matricule: EditText? = null
    private var CIN: EditText? = null
    private var DossierMed: EditText? = null
    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View){
        spinner = view.findViewById(R.id.PositionSpinner)
        Matricule = view.findViewById(R.id.MatriculeEditText)
        CIN = view.findViewById(R.id.NumeroCINEditText)
        DossierMed = view.findViewById(R.id.NumeroDossierEditText)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonChoosePosition)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonChoosePosition)

        ButtonReturn!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, SecondPage)!!.commit()
        }
        ButtonNext!!.setOnClickListener {

            var PatientPage = FragmentPatientInscription()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, PatientPage)!!.commit()
        }

    }
}