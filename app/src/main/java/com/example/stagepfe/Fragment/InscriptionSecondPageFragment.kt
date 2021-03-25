package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.stagepfe.R


class InscriptionSecondPageFragment : Fragment() {

    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var Adresse: EditText? = null
    private var DateNaiss: EditText? = null
    private var PhoneNumber: EditText? = null
    private var BloodGroup: EditText? = null
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
        Sexe = view.findViewById(R.id.InscriptionSexeSecondPage)


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
            } else {
                var choosePosition = ChoosePositionFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, choosePosition)
                    .commit()
            }
        }
    }

}

