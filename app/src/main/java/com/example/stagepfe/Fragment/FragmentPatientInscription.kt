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
    var Condition: EditText? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null
    var YesMedicament: RadioButton? = null
    var NoMedicament: RadioButton? = null
    var dialog: Dialog? = null
    var radiogroup: RadioGroup? = null

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
        Condition = view.findViewById(R.id.ConditionPatient)
        ReturnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        FinishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        YesMedicament = view.findViewById<RadioButton>(R.id.YesMedicament)
        NoMedicament = view.findViewById<RadioButton>(R.id.NoMedicament)
        Medicament = view.findViewById(R.id.Medicament)
        radiogroup = view.findViewById<RadioGroup>(R.id.Radio_Group)



//******************************return button**************************************
        ReturnButton!!.setOnClickListener {

            var choosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, choosePosition)!!.commit()
        }


//********************************Radio Button**************************************
        radiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (YesMedicament!!.isChecked) {
                Medicament!!.visibility = View.VISIBLE

            } else if (NoMedicament!!.isChecked) {
                Medicament!!.visibility = View.GONE
            }
        }


//********************************Finish button****************************************
        FinishButton!!.setOnClickListener {
            if (Condition!!.text.isEmpty()) {
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
            }else if (!YesMedicament!!.isChecked && !NoMedicament!!.isChecked){


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
                dialog.findViewById<TextView>(R.id.TitleDialog).setText("button radio est obligatoire")
                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }





            } else if (YesMedicament!!.isChecked && !NoMedicament!!.isChecked) {
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
                    var Connexion = ConnexionFragment()
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.ContainerFragmentLayout, Connexion).commit()

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
                    dialog.findViewById<TextView>(R.id.TitleDialog).text = "votre compte a été créé avec succès"
                    dialog.findViewById<TextView>(R.id.msgdialog).text = "vous pouvez se connecter"
                    dialog.findViewById<TextView>(R.drawable.check).background

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
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
                dialog.findViewById<TextView>(R.id.TitleDialog).setText("votre compte a été créé avec succès")

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }

                var Connexion = ConnexionFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, Connexion).commit()



            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////



/////////////////////////////////////////////////////////////////////////////////////////////////
//    private fun initDialog()
//    {
//        var v = View.inflate(
//            requireContext(),
//            R.layout.fragment_dialog,
//            null
//        )
//        var builder = AlertDialog.Builder(requireContext())
//        builder.setView(v)
//
//        dialog = builder.create()
//        dialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//        dialog!!.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
//                dialog!!.dismiss()
//            }
//    }
//    private fun updateDialogInfo(message:String?, icon:Int?){
//
//        if (message!=null){
//            var textViewDialogExemple =dialog!!.findViewById<TextView>(R.id.TitleDialog)
//            textViewDialogExemple.text = message
//        }
//        if (icon!=null){
//            R.drawable.close
//
//        }
//        dialog!!.show()
//
//    }


}
