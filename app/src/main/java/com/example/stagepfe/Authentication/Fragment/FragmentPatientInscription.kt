package com.example.stagepfe.Authentication.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R
import com.example.stagepfe.dao.ResponseCallback
import com.example.stagepfe.dao.SendToFireBase
import com.example.stagepfe.entite.UserItem


class FragmentPatientInscription : Fragment() {

    private var returnButton: Button? = null
    var maladiPatient: EditText? = null
    var finishButton: Button? = null
    var medicamentPatient: EditText? = null
    var yesMaladi: RadioButton? = null
    var noMaladi: RadioButton? = null
    var yesMedicament: RadioButton? = null
    var noMedicament: RadioButton? = null
    var dialog: Dialog? = null
    var firstRadiogroup: RadioGroup? = null
    var secondRadiogroup: RadioGroup? = null
    var testingMsg: String? = null
    var mContext: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_patient_inscription, container, false)
        initView(view)
        initdata()
        return view
    }

    private fun initdata() {
        mContext = requireContext()
    }


    @SuppressLint("ResourceType")
    private fun initView(view: View) {
        maladiPatient = view.findViewById(R.id.Maladi_Patient)
        returnButton = view.findViewById<Button>(R.id.ReturnButtonPatientInscription)
        finishButton = view.findViewById<Button>(R.id.FinishButtonPatientInscription)
        yesMaladi = view.findViewById(R.id.Yes_Maladi)
        noMaladi = view.findViewById(R.id.No_Maladi)
        yesMedicament = view.findViewById<RadioButton>(R.id.YesMedicament)
        noMedicament = view.findViewById<RadioButton>(R.id.NoMedicament)
        medicamentPatient = view.findViewById(R.id.Medicament)
        secondRadiogroup = view.findViewById<RadioGroup>(R.id.Second_Radio_Group)
        firstRadiogroup = view.findViewById<RadioGroup>(R.id.First_Radio_Group)


//******************************return button**************************************
        returnButton!!.setOnClickListener {

            var choosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, choosePosition)!!.commit()
        }

//********************************Second Radio Button**************************************

        firstRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (yesMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.VISIBLE

            } else if (noMaladi!!.isChecked) {
                maladiPatient!!.visibility = View.GONE
            }
        }

//********************************Second Radio Button**************************************

        secondRadiogroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (yesMedicament!!.isChecked) {
                medicamentPatient!!.visibility = View.VISIBLE

            } else if (noMedicament!!.isChecked) {
                medicamentPatient!!.visibility = View.GONE
            }
        }


//********************************Finish button****************************************
        finishButton!!.setOnClickListener {
//all are not checked
            if (!yesMaladi!!.isChecked && !noMaladi!!.isChecked || !yesMedicament!!.isChecked && !noMedicament!!.isChecked) {
                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("veuiller selectionner oui ou non")
                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }
                /// all yes are checked
            } else if (yesMaladi!!.isChecked && maladiPatient!!.text.isEmpty() || yesMedicament!!.isChecked && medicamentPatient!!.text.isEmpty()) {
                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }
            } else {
                var connexionpage = ConnexionFragment()
                var bundle = Bundle()
                connexionpage.arguments = bundle

                var user: UserItem = arguments!!.get("datachooseposition") as UserItem


                var userDao = SendToFireBase()
                user.medicament = medicamentPatient!!.text.toString()
                user.maladi = maladiPatient!!.text.toString()

                bundle.putParcelable("dataPatient", user)

                println("mouadh" + user.toString())

                userDao.insertUser(user)

/////////////////////////////////////////Progress dialog////////////////////////////////////////////

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


////////////////////////////////////////////////////////////////////////////////////////////////////

                userDao.signUpUser(requireActivity(), user, object : ResponseCallback {
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
                            "votre compte a été créé avec succès"

                        dialog.findViewById<Button>(R.id.btn_confirm)
                            .setOnClickListener {
                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.ContainerFragmentLayout, connexionpage).commit()
                                dialog.dismiss()
                            }
                    }

                    override fun failure() {
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
                            "il y a une faute réessayez"

                        dialog.findViewById<Button>(R.id.btn_confirm)
                            .setOnClickListener {
                                dialog.dismiss()
                            }

                    }
                })
////////////////////////////////////////////////////////////////////////////////////////////////////


            }

        }

    }
}





