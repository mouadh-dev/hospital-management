package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R


class InscriptionSecondPageFragment : Fragment() {

    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var Adresse: EditText? = null
    private var DateNaiss: EditText? = null
    private var PhoneNumber: EditText? = null
    private var BloodGroup: EditText? = null
    private var Male: RadioButton? = null
    private var Female: RadioButton? = null
    private var NoChoice: RadioButton? = null
    private var Sexe: RadioGroup? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inscription_second_page, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        ButtonReturn = view.findViewById<Button>(R.id.ReurnbuttonSecondePage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonSecondePage)
        Adresse = view.findViewById(R.id.InscriptionAdresseSecondPage)
        DateNaiss = view.findViewById(R.id.InscriptionDateSecondPage)
        PhoneNumber = view.findViewById(R.id.InscriptionPhoneNumberSecondPage)
        BloodGroup = view.findViewById(R.id.InscriptionBloodSecondPage)
        Male = view.findViewById<RadioButton>(R.id.FirstRadioButtonSecondeInscription)
        Female = view.findViewById<RadioButton>(R.id.SecondeRadioButtonSecondeInscription)
        NoChoice = view.findViewById<RadioButton>(R.id.ThirdRadioButtonSecondeInscription)


        ButtonReturn!!.setOnClickListener {
            var firstPage = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, firstPage)
                .commit()
        }

        ButtonNext!!.setOnClickListener {
            if (Adresse!!.text.isEmpty() || DateNaiss!!.text.isEmpty() || PhoneNumber!!.text.isEmpty() || BloodGroup!!.text.isEmpty()) {


                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            }else if (!Male!!.isChecked && !Female!!.isChecked && !NoChoice!!.isChecked){
                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
dialog.findViewById<TextView>(R.id.TitleDialog).setText("definissez votre sexe")
                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }

            }else {
                var choosePosition = ChoosePositionFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, choosePosition).addToBackStack(null)
                    .commit()
            }
        }


        // phone number case

        PhoneNumber!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (PhoneNumber!!.length() < 8 || PhoneNumber!!.length() > 8){
                    ButtonNext!!.isEnabled = false
                    PhoneNumber!!.error = "le numero n'existe pas"
                }else{
                    ButtonNext!!.isEnabled = true
                }
            }

        })
    }

}

