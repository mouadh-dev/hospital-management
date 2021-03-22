package com.example.stagepfe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.R


class FragmentInscriptionFirstPage : Fragment() {

       private var ButtonReturn: Button? = null
       private var ButtonNext: Button? = null
       private var FirstName: EditText? = null
       private var LastName: EditText? = null
       private var Mail: EditText? = null
       private var Password: EditText? = null
       private var ConfirmPass: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_inscription_first_page, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        ButtonReturn = view.findViewById<Button>(R.id.ReturnbuttonFirstPage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonFirstPage)
        LastName = view.findViewById(R.id.InscriptionLastNameFirstPage)
        FirstName = view.findViewById(R.id.InscriptionFirstNameFirstPage)
        Mail = view.findViewById(R.id.InscriptionMailFirstPage)
        Password = view.findViewById(R.id.InscriptionPasswordFirstPage)
        ConfirmPass = view.findViewById(R.id.InscriptionConfirmPassFirstPage)

        ButtonReturn!!.setOnClickListener {
            var ConnexionFragment = ConnexionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ConnexionFragment)!!.commit()
        }
        ButtonNext!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, SecondPage)!!.commit()
        }
    }

}
