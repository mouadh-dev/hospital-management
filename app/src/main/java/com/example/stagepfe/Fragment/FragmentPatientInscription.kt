package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class FragmentPatientInscription : Fragment() {

    var ReturnButton: Button? = null
    var Condition: EditText? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null


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
        Medicament = view.findViewById(R.id.Medicament)

        ReturnButton!!.setOnClickListener {

            var ChoosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }

        FinishButton!!.setOnClickListener {
        if (Condition!!.text.isEmpty() ||  Medicament!!.text.isEmpty()) {
            Toast.makeText(context, "le champ est obligatoire", Toast.LENGTH_SHORT).show()

        } else {
            requireActivity().run {
                var intent = Intent(this, AuthenticationFragmentContainerActivity::class.java)
                startActivity(intent)
                finish()

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