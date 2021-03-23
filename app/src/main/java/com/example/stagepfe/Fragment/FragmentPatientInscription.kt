package com.example.stagepfe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.stagepfe.R


class FragmentPatientInscription : Fragment() {
        var ReturnButton: Button? = null
    var ConditionSpinner: Spinner? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        ArrayAdapter.createFromResource(
//            this,
//            R.array.User_Position,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            ConditionSpinner!!.adapter = adapter
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_patient_inscription, container, false)
        initView(view)
        return view


    }

    private fun initView(view: View) {
//        ConditionSpinner = view.findViewById(R.id.ConditionSpinner)
        ReturnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        FinishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        Medicament = view.findViewById(R.id.Medicament)



    }


}