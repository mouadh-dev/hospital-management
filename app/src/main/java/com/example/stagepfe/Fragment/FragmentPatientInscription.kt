package com.example.stagepfe.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R


class FragmentPatientInscription : Fragment() {

    private var ReturnButton: Button? = null
    var maladiPatient: EditText? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null
    var YesMaladi: RadioButton? = null
    var NoMaladi: RadioButton? = null
    var YesMedicament: RadioButton? = null
    var NoMedicament: RadioButton? = null
    var dialog: Dialog? = null
    var firstRadiogroup: RadioGroup? = null
    var secondRadiogroup: RadioGroup? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_patient_inscription, container, false)
        initView(view)
        return view
    }


    @SuppressLint("ResourceType")
    private fun initView(view: View) {
        maladiPatient = view.findViewById(R.id.Maladi_Patient)
        ReturnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        FinishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        YesMaladi = view.findViewById(R.id.Yes_Maladi)
        NoMaladi = view.findViewById(R.id.No_Maladi)
        YesMedicament = view.findViewById<RadioButton>(R.id.YesMedicament)
        NoMedicament = view.findViewById<RadioButton>(R.id.NoMedicament)
        Medicament = view.findViewById(R.id.Medicament)
        secondRadiogroup = view.findViewById<RadioGroup>(R.id.Second_Radio_Group)
        firstRadiogroup = view.findViewById<RadioGroup>(R.id.First_Radio_Group)


//******************************return button**************************************
        ReturnButton!!.setOnClickListener {

            var choosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, choosePosition)!!.commit()
        }

//********************************Second Radio Button**************************************

        firstRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (YesMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.VISIBLE

            } else if (NoMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.GONE
            }
        }

//********************************Second Radio Button**************************************

        secondRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (YesMedicament!!.isChecked) {
                Medicament!!.visibility = View.VISIBLE

            } else if (NoMedicament!!.isChecked) {
                Medicament!!.visibility = View.GONE
            }
        }


//********************************Finish button****************************************
        FinishButton!!.setOnClickListener {

            if (!YesMaladi!!.isChecked && !NoMaladi!!.isChecked || !YesMedicament!!.isChecked && !NoMedicament!!.isChecked) {
                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("veuiller selectionner oui ou non")
                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }
            } else if (YesMaladi!!.isChecked && !NoMaladi!!.isChecked) {
                if (maladiPatient!!.text.isEmpty()) {
                    var v = View.inflate(
                        requireContext(),
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                        }


                }else{
                    var v = View.inflate(
                        requireContext(),
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog)
                        .setText("votre compte a été créé avec succès")

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                            var Connexionpage = ConnexionFragment()
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()
                        }
                }


            } else if (YesMedicament!!.isChecked && !NoMedicament!!.isChecked){
                if (Medicament!!.text.isEmpty()){
                    var v = View.inflate(
                        requireContext(),
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                        }
                }else{
                    var v = View.inflate(
                        requireContext(),
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog)
                        .setText("votre compte a été créé avec succès")

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                            var Connexionpage = ConnexionFragment()
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()
                        }


                }


            }else{
                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("votre compte a été créé avec succès")

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                        var Connexionpage = ConnexionFragment()
                        fragmentManager!!.beginTransaction()
                            .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()
                    }


            }
//////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////


        }

    }
}




