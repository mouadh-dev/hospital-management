package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)


        spinner!!.setSelection(0)
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
                if (position == 0) {
                    Matricule!!.visibility = View.GONE
                    CIN!!.visibility = View.GONE
                    DossierMed!!.visibility = View.GONE
                }


//*******************************************Medecin*********************************************
                else if (position == 1) {
                    CIN!!.visibility = View.GONE
                    DossierMed!!.visibility = View.GONE
                    Matricule!!.visibility = View.VISIBLE

                    ButtonNext!!.setOnClickListener {

                        if (Matricule!!.text.isEmpty()) {
                            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            var DoctorPage = InscriptionDoctorFragment()
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, DoctorPage).commit()
                        }
                    }

//**************************************Patient***********************************************
                } else if (position == 2) {
                    CIN!!.visibility = View.VISIBLE
                    DossierMed!!.visibility = View.VISIBLE
                    Matricule!!.visibility = View.GONE
                    ButtonNext!!.setOnClickListener {

                        if (DossierMed!!.text.isEmpty() || CIN!!.text.isEmpty()) {
                            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            var PatientPage = FragmentPatientInscription()
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, PatientPage).commit()
                        }
                    }

                }
//*********************************Pharmacien et labo************************************************
                else {
                    CIN!!.visibility = View.GONE
                    DossierMed!!.visibility = View.GONE
                    Matricule!!.visibility = View.VISIBLE
                    Ellipse!!.visibility = View.GONE
                    ButtonNext!!.setOnClickListener {

                        if (Matricule!!.text.isEmpty()) {
                            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            var Connexion = ConnexionFragment()
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, Connexion).commit()

                            var congratView = View.inflate(
                                requireContext(),
                                R.layout.fragment_dialog,
                                null
                            )
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(congratView)

                            var dialogcongrat = builder.create()
                            dialogcongrat.show()
                            dialogcongrat.window?.setBackgroundDrawableResource(android.R.color.transparent)

                            dialogcongrat.findViewById<Button>(R.id.btn_confirm)
                                .setOnClickListener {
                                    dialogcongrat.dismiss()

                                }

                        }
                    }

                }

            }

        }

        fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        // fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
        // }
    }
}