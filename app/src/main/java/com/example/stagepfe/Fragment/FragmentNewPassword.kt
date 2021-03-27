package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class FragmentNewPassword : Fragment() {

    private var FinishButton: Button? = null
    private var NewPassword: EditText? = null
    private var  ConfirmNewPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_new_password, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        FinishButton = view.findViewById<Button>(R.id.FinishButtonNewPassword)
        NewPassword = view.findViewById(R.id.NewPassword)
        ConfirmNewPassword = view.findViewById(R.id.ConfirmNewPassword)

        FinishButton!!.setOnClickListener {
            if (NewPassword!!.text.isEmpty() || ConfirmNewPassword!!.text.isEmpty()) {
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
                requireActivity().run {
                    var intent =
                        Intent(this, AuthenticationFragmentContainerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        ConfirmNewPassword!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    FinishButton!!.isEnabled = false
                    ConfirmNewPassword!!.error = "le mot de passe ne correspond pas"
                }
            }

        })
    }
    private fun notequal(): Boolean {
        return  ConfirmNewPassword!!.text.toString() != NewPassword!!.text.toString()
    }
}