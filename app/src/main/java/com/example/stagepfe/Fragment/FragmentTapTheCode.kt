package com.example.stagepfe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.stagepfe.R


class FragmentTapTheCode : Fragment() {

    var SecondIconBack: ImageView? = null
    var NextButtonCode: Button? = null
    var CaseOne: EditText? = null
    var CaseTwo: EditText? = null
    var CaseThree: EditText? = null
    var CaseFour: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_tap_the_code, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        SecondIconBack = view.findViewById(R.id.IconReturnBack)
        NextButtonCode = view.findViewById(R.id.NextTapTheCode)
        CaseOne = view.findViewById(R.id.CaseOne)
        CaseTwo = view.findViewById(R.id.CaseTwo)
        CaseThree = view.findViewById(R.id.CaseThree)
        CaseFour = view.findViewById(R.id.CaseFour)

        NextButtonCode!!.setOnClickListener {
             if (CaseOne!!.text.isEmpty() || CaseTwo!!.text.isEmpty() || CaseThree!!.text.isEmpty() || CaseFour!!.text.isEmpty()) {
                 Toast.makeText(context, "le champ est obligatoire", Toast.LENGTH_SHORT)
                 .show()
                 } else {
                var NewPasswordPage = FragmentNewPassword()
                fragmentManager!!.beginTransaction()!!
                    .replace(R.id.ContainerForgotPassword, NewPasswordPage)!!.commit()
             }

        }
    }
}