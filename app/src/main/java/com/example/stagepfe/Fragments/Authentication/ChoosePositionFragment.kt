package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.SignUpCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.entite.UserItem


class ChoosePositionFragment : Fragment() {
    private var spinner: Spinner? = null
    private var matricule: EditText? = null
    private var cin: EditText? = null
    private var buttonReturn: Button? = null
    private var buttonNext: Button? = null
    private var ellipse: View? = null
    private var role: String? = null
    var mContext: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_choose_position, container, false)
        initView(view)
        initdata()

        spinner!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.User_Position)
        ) as SpinnerAdapter


        return view

    }

    private fun initdata() {
        mContext = requireContext()
    }



    private fun initView(view: View) {
        spinner = view.findViewById(R.id.PositionSpinner)
        matricule = view.findViewById(R.id.MatriculeEditText)
        cin = view.findViewById(R.id.NumeroCINEditText)
        buttonReturn = view.findViewById<Button>(R.id.ReturnButtonChoosePosition)
        buttonNext = view.findViewById<Button>(R.id.NextButtonChoosePosition)
        ellipse = view.findViewById<View>(R.id.ForthEllipse)

        buttonReturn!!.setOnClickListener {
            var SecondPage = InscriptionSecondPageFragment()
            requireFragmentManager().beginTransaction()
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

        buttonNext!!.setOnClickListener {
            when (role) {
                "choisir" -> {
                    buttonNext!!.isEnabled = false
                }
                "M??decin" -> {
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

        matricule!!.addTextChangedListener(object : TextWatcher {
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
                if (matricule!!.length() != 5) {
                    matricule!!.error = "se compose de 5 chiffre"
                    buttonNext!!.isEnabled = false
                    buttonNext!!.setBackgroundResource(R.drawable.gray_button)

                } else {
                    buttonNext!!.isEnabled = true
                    buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)


                }
            }

        })
////////////////////////////////////////////////////////////////////////////////////////////////////
        cin!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (cin!!.length() != 8) {
                    cin!!.error = "se compose de 8 chiffre"
                    buttonNext!!.isEnabled = false
                    buttonNext!!.setBackgroundResource(R.drawable.gray_button)

                } else {
                    buttonNext!!.isEnabled = true
                    buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)


                }
            }
        })
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
//***************************************navigation***********************************************
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Labo//////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun navigatetoConnexionLabo() {
        if (matricule!!.text.isEmpty() || cin!!.text.isEmpty()) {
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

///////////////////////////////Send Data //////////////////////////////////////////////////////////
            var connexionpage = ConnexionFragment()
            var bundle = Bundle()
            var user: UserItem = requireArguments().get("datasecondpage") as UserItem
            var userDao = UserDao()

            if (role == "Labo") {
                var mRole = ArrayList<String>()
//                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("Patient")
                user.role = mRole
                user.demande = spinner!!.selectedItem.toString()
            }
            user.matricule = matricule!!.text.toString()
            user.numCIN = cin!!.text.toString()
            bundle.putParcelable("datachooseposition", user)
            println("mouadh " + user.toString())

////////////////////////Send  to  Fire Base /////////////////////////////////////////////////////

            var v = View.inflate(
                mContext,
                R.layout.progress_dialog,
                null
            )
            var builder = AlertDialog.Builder(mContext)
            builder.setView(v)

            var progressdialog = builder.create()
            progressdialog.show()
            progressdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            progressdialog.setCancelable(false)

            userDao.signUpUser(requireActivity(), user, object : SignUpCallback {
                override fun success() {
                    progressdialog.dismiss()

                    var v = View.inflate(
                        mContext,
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog).text =
                        "votre compte a ??t?? cr???? avec succ??s"
                    dialog.findViewById<ImageView>(R.id.CheckDialog)
                        .setBackgroundResource(R.drawable.ellipse_green)
                    dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                    dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, connexionpage).commit()
                            dialog.dismiss()
                        }
                }

                override fun failure(error:String) {
                    progressdialog.dismiss()
                    var v = View.inflate(
                        mContext,
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog).text =
                        error
                    dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                        }

                }
            })


        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Pharmacien //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun navigatetoConnexionPharmacien() {
        if (matricule!!.text.isEmpty() || cin!!.text.isEmpty()) {
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


////////////////////////////////////////////////////////////////////////////////////////////////////
            var bundle = Bundle()
            var user: UserItem = requireArguments().get("datasecondpage") as UserItem
            var userDao = UserDao()
///////////////////////////////////////////////////////////////////////////////////////////////////
            if (role == "Pharmacien") {
                var mRole = ArrayList<String>()
//                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("Patient")
                user.role = mRole
                user.demande = spinner!!.selectedItem.toString()
            }
            user.matricule = matricule!!.text.toString()
            user.numCIN = cin!!.text.toString()

            bundle.putParcelable("datachooseposition", user)
            println("mouadh " + user.toString())



///////////////////////////////////////////////////////////////////////////////////////////////////

            var v = View.inflate(
                mContext,
                R.layout.progress_dialog,
                null
            )
            var builder = AlertDialog.Builder(mContext)
            builder.setView(v)

            var progressdialog = builder.create()
            progressdialog.show()
            progressdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            progressdialog.setCancelable(false)

            userDao.signUpUser(requireActivity(), user, object : SignUpCallback {

                override fun success() {
                    progressdialog.dismiss()
                    var connexionpage = ConnexionFragment()


                    var v = View.inflate(
                        mContext,
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog).text =
                        "votre compte a ??t?? cr???? avec succ??s"
                    dialog.findViewById<ImageView>(R.id.CheckDialog)
                        .setBackgroundResource(R.drawable.ellipse_green)
                    dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                    dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.ContainerFragmentLayout, connexionpage).commit()
                            dialog.dismiss()
                        }
                }

                override fun failure(error:String) {
                    progressdialog.dismiss()
                    var v = View.inflate(
                        mContext,
                        R.layout.fragment_dialog,
                        null
                    )
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setView(v)

                    var dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.findViewById<TextView>(R.id.TitleDialog).text =
                       error
                    dialog

                    dialog.findViewById<Button>(R.id.btn_confirm)
                        .setOnClickListener {
                            dialog.dismiss()
                        }

                }
            })


        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////Patient ////////////////////////////////////////////////

    private fun navigatToPatient() {
        buttonNext!!.isEnabled = true
        buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)

        if (cin!!.text.isEmpty()) {
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
            var patientPage = PatientInscriptionFragment()
            var bundle = Bundle()
            var user: UserItem = requireArguments().get("datasecondpage") as UserItem

            if (role == "Patient") {
                var mRole = ArrayList<String>()
                mRole.add(spinner!!.selectedItem.toString())
                user.role = mRole
            }
            user.numCIN = cin!!.text.toString()

            bundle.putParcelable("datachooseposition", user)
            patientPage.arguments = bundle

            println("mouadh " + user.toString())
            requireFragmentManager().beginTransaction()
                .replace(R.id.ContainerFragmentLayout, patientPage).commit()
        }

    }


    private fun navigateToMedecin() {
        buttonNext!!.isEnabled = true
        buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)

        if (matricule!!.text.isEmpty() || cin!!.text.isEmpty()) {
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
            var user: UserItem = requireArguments().get("datasecondpage") as UserItem

            if (role == "M??decin") {
                var mRole = ArrayList<String>()
//                mRole.add(spinner!!.selectedItem.toString())
                mRole.add("Patient")
                user.role = mRole
                user.demande = spinner!!.selectedItem.toString()
            }
            user.matricule = matricule!!.text.toString()
            user.numCIN = cin!!.text.toString()



            bundle.putParcelable("datachooseposition", user)
            doctorpage.arguments = bundle

            println("mouadh " + user.toString())
            requireFragmentManager().beginTransaction()
                .replace(R.id.ContainerFragmentLayout, doctorpage).commit()

        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////

//******************************************spinner************************************************


    private fun UpdateView(role: String?) {
        when (role) {
            "Choisir" -> {
                buttonNext!!.setBackgroundResource(R.drawable.gray_button)
                matricule!!.visibility = View.GONE
                cin!!.visibility = View.GONE
                ellipse!!.visibility = View.VISIBLE
            }
            "M??decin" -> {
                buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)
                cin!!.visibility = View.VISIBLE
                matricule!!.visibility = View.VISIBLE
                ellipse!!.visibility = View.VISIBLE
                buttonNext!!.text = "Suivant"
            }
            "Patient" -> {
                buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)
                cin!!.visibility = View.VISIBLE
                matricule!!.visibility = View.GONE
                ellipse!!.visibility = View.VISIBLE
                buttonNext!!.text = "Suivant"
            }
            else -> {
                buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)
                cin!!.visibility = View.VISIBLE
                matricule!!.visibility = View.VISIBLE
                ellipse!!.visibility = View.GONE
                buttonNext!!.text = "Terminer"
            }
        }

    }
}