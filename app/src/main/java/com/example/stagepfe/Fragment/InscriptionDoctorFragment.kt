package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R
    private var Specialité: Spinner? = null
    private var BioDoctor: EditText? = null
    private var ButtonReturn: Button? = null
    private var ButtonFinish: Button? = null

class InscriptionDoctorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_inscription_doctor, container, false)
        initView(view)
        Specialité!!.setSelection(0)
        Specialité!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.Specialités)
        ) as SpinnerAdapter
        Specialité?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }
        }
        return  view
    }
    private fun initView(view: View) {
        Specialité = view.findViewById(R.id.Specialité)
        BioDoctor = view.findViewById(R.id.BioDoctor)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonDoctorInscription)
        ButtonFinish = view.findViewById<Button>(R.id.FinishInscriptionDoctor)

        ButtonReturn!!.setOnClickListener {
            var ChoosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }
        ButtonFinish!!.setOnClickListener {

            if (BioDoctor!!.text.isEmpty()) {
                Toast.makeText(context, "le champ est obligatoire", Toast.LENGTH_SHORT)
                    .show()
            } else {
                requireActivity().run {
                    var intent = Intent(this, AuthenticationFragmentContainerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}