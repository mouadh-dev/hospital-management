package com.example.stagepfe.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R

class ChoosePositionFragment : Fragment() {
    private var spinner: Spinner? = null
    private var Matricule: EditText? = null
    private var CIN: EditText? = null
    private var DossierMed: EditText? = null
    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var Ellipse: View? = null
    private var role: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)


        spinner!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.User_Position)
        ) as SpinnerAdapter


        return view

    }


    private fun initView(view: View) {
        spinner = view.findViewById(R.id.PositionSpinner)
        Matricule = view.findViewById(R.id.MatriculeEditText)
        CIN = view.findViewById(R.id.NumeroCINEditText)
        DossierMed = view.findViewById(R.id.NumeroDossierEditText)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonChoosePosition)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonChoosePosition)
        Ellipse = view.findViewById<View>(R.id.ForthEllipse)

        ButtonReturn!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, SecondPage).commit()
        }
        spinner!!.setSelection(1)


        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                role = spinner!!.selectedItem.toString()
                UpdateView(role)

            }
/////////////////////////////////////////////////////////////////////////////////////////////////
        }
///////////////////////////////////////////////////////////////////////////////////////////////////

        ButtonNext!!.setOnClickListener {
            when (role){
                "choisir" ->{

                }
            }
        }

////////////////////////////////////////////////////////////////////////////////////////////////////

        Matricule!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (Matricule!!.length() != 5) {

                    Matricule!!.error = "se compose de 5 chiffre"
                    ButtonNext!!.isEnabled = false
                } else {
                    ButtonNext!!.isEnabled = true
                }
            }

        })

        fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        // fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
        // }
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //******************************************spinner************************************************

    private fun onItemSelected() {

    }

    private fun UpdateView(role: String?) {
        when (role) {
            "Choisir" -> {
                Matricule!!.visibility = View.GONE
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Ellipse!!.visibility = View.VISIBLE
            }
            "Médecin" ->{
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Matricule!!.visibility = View.VISIBLE
                Ellipse!!.visibility = View.VISIBLE
            }
            "Patient"->{
                CIN!!.visibility = View.VISIBLE
                DossierMed!!.visibility = View.VISIBLE
                Matricule!!.visibility = View.GONE
                Ellipse!!.visibility = View.VISIBLE
            }
            else ->{
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Matricule!!.visibility = View.VISIBLE
                Ellipse!!.visibility = View.GONE
            }
        }

    }
}