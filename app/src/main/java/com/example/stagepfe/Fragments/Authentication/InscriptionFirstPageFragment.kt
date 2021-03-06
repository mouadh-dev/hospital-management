package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.R


class InscriptionFirstPageFragment : Fragment() {

    private var buttonReturn: Button? = null
    lateinit var buttonNext: Button
    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var mail: EditText? = null
    private var password: EditText? = null
    private var confirmPass: EditText? = null
    private var title: TextView? = null
    private var showPasswordIV: ImageView? = null
    private var showPasswordRpeatIV: ImageView? = null



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
        buttonReturn = view.findViewById<Button>(R.id.ReturnbuttonFirstPage)
        buttonNext = view.findViewById<Button>(R.id.NextButtonFirstPage)
        lastName = view.findViewById(R.id.InscriptionLastNameFirstPage)
        firstName = view.findViewById(R.id.InscriptionFirstNameFirstPage)
        mail = view.findViewById(R.id.InscriptionMailFirstPage)
        password = view.findViewById(R.id.InscriptionPasswordFirstPage)
        confirmPass = view.findViewById(R.id.InscriptionConfirmPassFirstPage)
        title = view.findViewById(R.id.TitleDialog)
        showPasswordIV = view.findViewById(R.id.Eye_Show_Inscription)
        showPasswordRpeatIV = view.findViewById(R.id.eye_Show_Repeat_Inscription)


        buttonReturn!!.setOnClickListener {
            val connexionFragment = ConnexionFragment()
            requireFragmentManager().beginTransaction()
                .replace(R.id.ContainerFragmentLayout, connexionFragment)
                .commit()
        }
        buttonNext.setOnClickListener {
            if (lastName!!.text.isEmpty() || firstName!!.text.isEmpty() || mail!!.text.isEmpty() || password!!.text.isEmpty() || confirmPass!!.text.isEmpty()) {


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
                var user = UserItem()
                user.nom=firstName!!.text.trim().toString()
                user.prenom=lastName!!.text.trim().toString()
                user.mail=mail!!.text.trim().toString()
                user.password=password!!.text.toString()
                user.confirmpassword= confirmPass!!.text.toString()
                bundle.putParcelable("datafirstpage", user)

                secondPage.arguments=bundle
                requireFragmentManager().beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, secondPage).commit()

            }
        }
        mail!!.addTextChangedListener(object :  TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(mail!!.text.toString()).matches()) {
                    buttonNext.isEnabled = true
                    buttonNext.setBackgroundResource(R.drawable.button_style_smaller)
                } else{
                    buttonNext.isEnabled = false
                    buttonNext.setBackgroundResource(R.drawable.gray_button)
                    mail!!.error = "invalide Email"
                }
            }

        })
        password!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                    if (password!!.length() <= 8){
                        buttonNext!!.isEnabled = false
                        buttonNext!!.setBackgroundResource(R.drawable.gray_button)
                        password!!.error = "le mot de passe est faible "
                    }else{
                        buttonNext!!.isEnabled = true
                        buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)

                    }
            }


        })
        confirmPass!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    buttonNext.isEnabled = false
                    buttonNext.setBackgroundResource(R.drawable.gray_button)
                    confirmPass!!.error = "le mot de passe ne correspond pas"
                }else{
                    buttonNext.isEnabled = true
                    buttonNext.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })

        showPasswordRpeatIV!!.setOnClickListener{
            if(confirmPass!!.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
            {
                confirmPass!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else {
                confirmPass!!.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        showPasswordIV!!.setOnClickListener {
            if (password!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                password!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                password!!.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

    }

    private fun notequal(): Boolean {
        return confirmPass!!.text.toString() != password!!.text.toString()
    }


}
