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
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.R


class FragmentPatientInscription : Fragment() {

    private var returnButton: Button? = null
    var maladiPatient: EditText? = null
    var finishButton: Button? = null
    var medicament: EditText? = null
    var yesMaladi: RadioButton? = null
    var noMaladi: RadioButton? = null
    var yesMedicament: RadioButton? = null
    var noMedicament: RadioButton? = null
    var dialog: Dialog? = null
    var firstRadiogroup: RadioGroup? = null
    var secondRadiogroup: RadioGroup? = null
    var testingMsg: String? = null


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
        returnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        finishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        yesMaladi = view.findViewById(R.id.Yes_Maladi)
        noMaladi = view.findViewById(R.id.No_Maladi)
        yesMedicament = view.findViewById<RadioButton>(R.id.YesMedicament)
        noMedicament = view.findViewById<RadioButton>(R.id.NoMedicament)
        medicament = view.findViewById(R.id.Medicament)
        secondRadiogroup = view.findViewById<RadioGroup>(R.id.Second_Radio_Group)
        firstRadiogroup = view.findViewById<RadioGroup>(R.id.First_Radio_Group)


//******************************return button**************************************
        returnButton!!.setOnClickListener {

            var choosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, choosePosition)!!.commit()
        }

//********************************Second Radio Button**************************************

        firstRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (yesMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.VISIBLE

            } else if (noMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.GONE
            }
        }

//********************************Second Radio Button**************************************

        secondRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (yesMedicament!!.isChecked) {
                medicament!!.visibility = View.VISIBLE

            } else if (noMedicament!!.isChecked) {
                medicament!!.visibility = View.GONE
            }
        }


//********************************Finish button****************************************
        finishButton!!.setOnClickListener {

            if (!yesMaladi!!.isChecked && !noMaladi!!.isChecked || !yesMedicament!!.isChecked && !noMedicament!!.isChecked) {
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
            } else if (yesMaladi!!.isChecked) {


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


                } else {
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

                        }

                    var Connexionpage = ConnexionFragment()
                    var bundle = Bundle()
                    var user: UserItem = arguments!!.get("datachooseposition") as UserItem

                    user.maladi = maladiPatient!!.text.toString()
                    bundle.putParcelable("dataPatient", user)
                    Connexionpage.arguments = bundle
                    println("mouadh" + user.toString())
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()


                }
            } else if (yesMedicament!!.isChecked) {
                if (medicament!!.text.isEmpty()) {
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

                } else {
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
                    dialog.findViewById<ImageView>(R.id.CheckDialog)
                        .setBackgroundResource(R.drawable.ellipse_green)

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()

                            var Connexionpage = ConnexionFragment()
                            var bundle = Bundle()
                            var user: UserItem = arguments!!.get("datachooseposition") as UserItem
                            user.medicament = medicament!!.text.toString()
                            bundle.putParcelable("dataPatient", user)
                            Connexionpage.arguments = bundle
                            println("mouadh" + user.toString())
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()
                        }
                }


            } else {
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
                    }
                var Connexionpage = ConnexionFragment()
                var bundle = Bundle()
                var user: UserItem = arguments!!.get("datachooseposition") as UserItem

                user.maladi = maladiPatient!!.text.toString()
                bundle.putParcelable("dataPatient", user)
                Connexionpage.arguments = bundle
                println("mouadh" + user.toString())
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, Connexionpage).commit()


            }
//////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////


        }

    }
}





