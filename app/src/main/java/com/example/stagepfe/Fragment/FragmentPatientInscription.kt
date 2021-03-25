package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class FragmentPatientInscription : Fragment() {

    var ReturnButton: Button? = null
    var Condition: EditText? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null
    var YesMedicament: RadioButton? = null
    var NoMedicament: RadioButton? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_patient_inscription, container, false)
        initView(view)
        return  view
    }
    private fun initView(view: View) {
        Condition = view.findViewById(R.id.ConditionPatient)
        ReturnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        FinishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        YesMedicament = view.findViewById<RadioButton>(R.id.YesMedicament)
       NoMedicament = view.findViewById<RadioButton>(R.id.NoMedicament)
        Medicament = view.findViewById(R.id.Medicament)

        ReturnButton!!.setOnClickListener {

            var ChoosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }
        fun onRadioButtonClicked(view: View) {
            if (view is RadioButton) {
                // Is the button now checked?
                val checked = view.isChecked

                // Check which radio button was clicked
                when (view.getId()) {
                    R.id.YesMedicament ->
                        if (checked) {
                            Medicament!!.visibility = View.VISIBLE
                            FinishButton!!.setOnClickListener {
                                if (Condition!!.text.isEmpty() || Medicament!!.text.isEmpty()) {
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
                                    var Connexion = ConnexionFragment()
                                    fragmentManager!!.beginTransaction()
                                        .replace(R.id.ContainerFragmentLayout, Connexion).commit()

                                        var congratView = View.inflate(
                                            requireContext(),
                                            R.layout.fragment_dalog_congart_frament,
                                            null
                                        )
                                        var builder = AlertDialog.Builder(requireContext())
                                        builder.setView(congratView)

                                        var dialogcongrat = builder.create()
                                        dialogcongrat.show()
                                        dialogcongrat.window?.setBackgroundDrawableResource(android.R.color.transparent)

                                        dialogcongrat.findViewById<Button>(R.id.Congrats_Button)
                                            .setOnClickListener {
                                                dialogcongrat.dismiss()
                                            }

                                }
                            } }
                    R.id.NoMedicament ->
                        if (checked) {
                            Medicament!!.visibility = View.GONE
                            FinishButton!!.setOnClickListener {
                                if (Condition!!.text.isEmpty()) {
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
                                            R.layout.fragment_dalog_congart_frament,
                                            null
                                        )
                                        var builder = AlertDialog.Builder(requireContext())
                                        builder.setView(congratView)

                                        var dialogcongrat = builder.create()
                                        dialogcongrat.show()
                                        dialogcongrat.window?.setBackgroundDrawableResource(android.R.color.transparent)

                                        dialogcongrat.findViewById<Button>(R.id.Congrats_Button)
                                            .setOnClickListener {
                                                dialogcongrat.dismiss()
                                            }

                                }
                            }
                        }
                }
            }
        }




}
}