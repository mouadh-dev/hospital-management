package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.stagepfe.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {
    private var backIcon: ImageView? = null
    var nextButton: Button? = null
     var mailForgotPassword: EditText? = null
    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_forgot_password, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        backIcon = view.findViewById<ImageView>(R.id.IconReturnBack)
        nextButton = view.findViewById<Button>(R.id.NextForgotPassword)
        mailForgotPassword = view.findViewById(R.id.MailForgotPassword)

        nextButton!!.setOnClickListener {
            if (mailForgotPassword!!.text.isEmpty()) {
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
                mAuth.sendPasswordResetEmail(mailForgotPassword!!.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Email sent.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                mailForgotPassword!!.addTextChangedListener(object : TextWatcher {
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
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(mailForgotPassword!!.text.toString())
                                .matches()
                        )
                            nextButton!!.isEnabled = true
                        else {
                            nextButton!!.isEnabled = false
                            mailForgotPassword!!.error = "invalide Email"
                        }
                    }


                })
            }
        }
    }

}