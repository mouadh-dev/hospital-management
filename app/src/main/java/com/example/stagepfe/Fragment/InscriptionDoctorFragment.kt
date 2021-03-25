package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R
    private var Speciality: Spinner? = null
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
        Speciality!!.setSelection(0)
        Speciality!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.Specialités)
        ) as SpinnerAdapter

        return  view
    }
    private fun initView(view: View) {
        Speciality = view.findViewById<Spinner>(R.id.Speciality_Doctor)
        BioDoctor = view.findViewById<EditText>(R.id.Bio_Doctor)
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
                            requireActivity().run {
                                var intent = Intent(this, AuthenticationFragmentContainerActivity::class.java)
                                startActivity(intent)
                                finish()
                        }
                }
            }
        }

    }
}