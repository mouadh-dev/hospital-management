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
import com.example.stagepfe.FireBase.dao.UserItem
import com.example.stagepfe.R

class ChoosePositionFragment : Fragment() {
    private var spinner: Spinner? = null
    private var Matricule: EditText? = null
    private var CIN: EditText? = null
    private var DossierMed: EditText? = null
    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var Ellipse: View? = null
    private var role: String? = null
    private var user: UserItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)
        initData()

        spinner!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.User_Position)
        ) as SpinnerAdapter


        return view

    }

    private fun initData() {

    }


    private fun initView(view: View) {
        spinner = view.findViewById(R.id.PositionSpinner)
        Matricule = view.findViewById(R.id.MatriculeEditText)
        CIN = view.findViewById(R.id.NumeroCINEditText)
        DossierMed = view.findViewById(R.id.NumeroDossierEditText)
        ButtonReturn = view.findViewById<Button>(R.id.ReturnButtonChoosePosition)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonChoosePosition)
        Ellipse = view.findViewById<View>(R.id.ForthEllipse)

        ButtonReturn!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, SecondPage).commit()
        }


        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                role = spinner!!.selectedItem.toString()
                UpdateView(role)

            }
/////////////////////////////////////////////////////////////////////////////////////////////////
        }
///////////////////////////////////////////////////////////////////////////////////////////////////

        ButtonNext!!.setOnClickListener {
            when (role) {
                "choisir" -> {
                    ButtonNext!!.isEnabled = false
                    var userchooseposition = arguments!!.get("datasecondpage")

                }
                "Médecin" -> {
                    navigateToMedecin()
                }
                "Patient" -> {
                    navigatToPatient()
                }
                "Pharmacien" -> {
                    navigatetoConnexionPharmacien()
                }
                "Labo" -> {
                    navigatetoConnexionLabo()
                }

            }
        }

////////////////////////////////////////////////////////////////////////////////////////////////////

        Matricule!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (Matricule!!.length() != 5) {

                    Matricule!!.error = "se compose de 5 chiffre"
                    ButtonNext!!.isEnabled = false
                } else {
                    ButtonNext!!.isEnabled = true
                }
            }

        })


    }



////////////////////////////////////////////////////////////////////////////////////////////////////
//***************************************navigation***********************************************

    private fun navigatetoConnexionLabo() {
        if (Matricule!!.text.isEmpty()) {
            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
            var builder = AlertDialog.Builder(requireContext())
            builder.setView(v)

            var dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                dialog.dismiss()
            }
        }
        else {

            var Connexion = ConnexionFragment()
            var bundle = Bundle()
            var user: UserItem = arguments!!.get("datasecondpage") as UserItem

            if (role == "Patient") {
                var mRole = ArrayList<String>()
                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("patient")
                user.role = mRole
            }
            user.matricule = Matricule!!.text.toString()

            bundle.putParcelable("datachooseposition", user)
            Connexion.arguments = bundle

            println("mouadh " + user.toString())
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, Connexion).commit()

        }    }


    private fun navigatetoConnexionPharmacien() {
        if (Matricule!!.text.isEmpty()) {
            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
            var builder = AlertDialog.Builder(requireContext())
            builder.setView(v)

            var dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                dialog.dismiss()
            }
        }
        else {

            var Connexion = ConnexionFragment()
            var bundle = Bundle()
            var user: UserItem = arguments!!.get("datasecondpage") as UserItem

            if (role == "Patient") {
                var mRole = ArrayList<String>()
                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("patient")
                user.role = mRole
            }
            user.matricule = Matricule!!.text.toString()

            bundle.putParcelable("datachooseposition", user)
            Connexion.arguments = bundle

            println("mouadh " + user.toString())
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, Connexion).commit()

        }    }

    private fun navigatToPatient() {
        ButtonNext!!.isEnabled = true

        if (DossierMed!!.text.isEmpty() || CIN!!.text.isEmpty()) {
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
            var patientPage = FragmentPatientInscription()
            var bundle = Bundle()
            var user: UserItem = arguments!!.get("datasecondpage") as UserItem
            user.position = spinner!!.selectedItem.toString()
            if (role == "Patient") {
                var mRole = ArrayList<String>()
                mRole.add(spinner!!.selectedItem.toString())

                user.role = mRole
            }
            user.numCIN = CIN!!.text.toString()

            bundle.putParcelable("datachooseposition", user)
            patientPage.arguments = bundle

            println("mouadh " + user.toString())
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, patientPage).commit()
        }

    }


    private fun navigateToMedecin() {
        ButtonNext!!.isEnabled = true

        if (Matricule!!.text.isEmpty()) {
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


            var doctorpage =
                InscriptionDoctorFragment()
            var bundle = Bundle()
            var user: UserItem = arguments!!.get("datasecondpage") as UserItem

            if (role == "Médecin") {
                var mRole = ArrayList<String>()
                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("patient")
                user.role = mRole
            }
            user.matricule = Matricule!!.text.toString()





            bundle.putParcelable("datachooseposition", user)
            doctorpage.arguments = bundle

            println("mouadh " + user.toString())
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, doctorpage).commit()

        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////

//******************************************spinner************************************************


    private fun UpdateView(role: String?) {
        when (role) {
            "Choisir" -> {
                Matricule!!.visibility = View.GONE
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Ellipse!!.visibility = View.VISIBLE
            }
            "Médecin" -> {
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Matricule!!.visibility = View.VISIBLE
                Ellipse!!.visibility = View.VISIBLE
            }
            "Patient" -> {
                CIN!!.visibility = View.VISIBLE
                DossierMed!!.visibility = View.VISIBLE
                Matricule!!.visibility = View.GONE
                Ellipse!!.visibility = View.VISIBLE
            }
            else -> {
                CIN!!.visibility = View.GONE
                DossierMed!!.visibility = View.GONE
                Matricule!!.visibility = View.VISIBLE
                Ellipse!!.visibility = View.GONE
            }
        }

    }
}