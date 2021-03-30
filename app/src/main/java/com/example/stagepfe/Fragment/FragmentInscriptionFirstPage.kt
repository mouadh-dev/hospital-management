package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.FireBase.dao.UserItem
import com.example.stagepfe.R


class FragmentInscriptionFirstPage : Fragment() {

    private var ButtonReturn: Button? = null
    lateinit var ButtonNext: Button
    private var FirstName: EditText? = null
    private var LastName: EditText? = null
    private var Mail: EditText? = null
    private var Password: EditText? = null
    private var ConfirmPass: EditText? = null
    private var Title: TextView? = null

//    var nom = FirstName!!.text.toString()
//    var prenom = LastName!!.text.toString()
//    var mail = Mail!!.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inscription_first_page, container, false)
        initView(view)





        return view



    }

    private fun initView(view: View) {
        ButtonReturn = view.findViewById<Button>(R.id.ReturnbuttonFirstPage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonFirstPage)
        LastName = view.findViewById(R.id.InscriptionLastNameFirstPage)
        FirstName = view.findViewById(R.id.InscriptionFirstNameFirstPage)
        Mail = view.findViewById(R.id.InscriptionMailFirstPage)
        Password = view.findViewById(R.id.InscriptionPasswordFirstPage)
        ConfirmPass = view.findViewById(R.id.InscriptionConfirmPassFirstPage)
        Title = view.findViewById(R.id.TitleDialog)


        ButtonReturn!!.setOnClickListener {
            val connexionFragment = ConnexionFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, connexionFragment)
                .commit()
        }
        ButtonNext.setOnClickListener {
            if (LastName!!.text.isEmpty() || FirstName!!.text.isEmpty() || Mail!!.text.isEmpty() || Password!!.text.isEmpty() || ConfirmPass!!.text.isEmpty()) {


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



                var secondPage =
                    InscriptionSecondPageFragment()
                var bundle= Bundle()
                var user:UserItem= UserItem()
                user.nom=FirstName!!.text.trim().toString()
                user.prenom=LastName!!.text.trim().toString()
                user.mail=Mail!!.text.trim().toString()
                user.password=Password!!.text.toString()
                user.confirmpassword= ConfirmPass!!.text.toString()
                bundle.putParcelable("datafirstpage", user)
                secondPage.arguments=bundle
//             var user1=   arguments!!.get("data1")
                println("mouadh "+ user.toString())
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, secondPage).commit()

            }
        }
        Mail!!.addTextChangedListener(object :  TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(Mail!!.text.toString()).matches())
                    ButtonNext.isEnabled = true
                else{
                    ButtonNext.isEnabled = false
                    Mail!!.error = "invalide Email"
                }
            }

        })
        ConfirmPass!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    ButtonNext.isEnabled = false
                    ConfirmPass!!.error = "le mot de passe ne correspond pas"
                }else{
                    ButtonNext.isEnabled = true
                }
            }


        })

    }

    private fun notequal(): Boolean {
        return ConfirmPass!!.text.toString() != Password!!.text.toString()
    }


}
