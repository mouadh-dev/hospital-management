package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class InscriptionSecondPageFragment : Fragment() {

    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var  Adresse: EditText? = null
    private var DateNaiss: EditText? = null
    private var  PhoneNumber: EditText? = null
    private var  BloodGroup: EditText? = null
    private var Sexe: RadioGroup?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_inscription_second_page, container, false)
        initView(view)
        return  view
    }
    private fun initView(view: View){
        ButtonReturn = view.findViewById<Button>(R.id.ReurnbuttonSecondePage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonFirstPage)
        Adresse= view.findViewById(R.id.InscriptionAdresseSecondPage)
        DateNaiss= view.findViewById(R.id.InscriptionDateSecondPage)
        PhoneNumber= view.findViewById(R.id.InscriptionPhoneNumberSecondPage)
        BloodGroup= view.findViewById(R.id.InscriptionBloodSecondPage)
        Sexe= view.findViewById(R.id.InscriptionSexeSecondPage)
        ButtonReturn!!.setOnClickListener {
            var FirstPage = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, FirstPage)!!.commit()
        }
        ButtonNext!!.setOnClickListener {
            var ChoosePosition= ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }
    }

}

