package com.example.stagepfe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        var view = inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)
        return view

//        ArrayAdapter
//            .createFromResource(this, R.array.User_Position,android.R.layout.simple_spinner_item)
//            .also()
//        {
//                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)   spinner!!.adapter = adapter
//        }
//        spinner!!.setOnItemSelectedListener(this)
    }



    private fun initView(view: View) {
        spinner = view.findViewById(R.id.PositionSpinner)
        Matricule = view.findViewById(R.id.MatriculeEditText)
        CIN = view.findViewById(R.id.NumeroCINEditText)
        DossierMed = view.findViewById(R.id.NumeroDossierEditText)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonChoosePosition)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonChoosePosition)

        ButtonReturn!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, SecondPage).commit()
        }
        ButtonNext!!.setOnClickListener {
             if (  DossierMed!!.text.isEmpty() || CIN!!.text.isEmpty() ||  Matricule!!.text.isEmpty()) {
                 Toast.makeText(context, "le champ est obligatoire", Toast.LENGTH_SHORT)
                     .show()
             } else {
            var PatientPage = FragmentPatientInscription()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, PatientPage).commit()
        }
    }

    }
    // fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    // if (position==0) {
     // Matricule!!.visibility = View.GONE
    //  CIN!!.visibility = View.GONE
    //  DossierMed!!.visibility = View.GONE
    // }
    // else if (position==2){
    //     CIN!!.visibility= View.VISIBLE
    //     DossierMed!!.visibility= View.VISIBLE
    //       Matricule!!.visibility= View.GONE
    //   }else  {
    //      CIN!!.visibility= View.GONE
    //     DossierMed!!.visibility= View.GONE
    //      Matricule!!.visibility= View.VISIBLE

    //  }
    // }

    // fun onNothingSelected(parent: AdapterView<*>?) {
    // TODO("Not yet implemented")
    // }
}