package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class FragmentNewPassword : Fragment() {

    private var finishButton: Button? = null
    private var newPassword: EditText? = null
    private var  confirmNewPassword: EditText? = null

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
        finishButton = view.findViewById<Button>(R.id.FinishButtonNewPassword)
        newPassword = view.findViewById(R.id.NewPassword)
        confirmNewPassword = view.findViewById(R.id.ConfirmNewPassword)

        finishButton!!.setOnClickListener {
            if (newPassword!!.text.isEmpty() || confirmNewPassword!!.text.isEmpty()) {
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
        confirmNewPassword!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    finishButton!!.isEnabled = false
                    confirmNewPassword!!.error = "le mot de passe ne correspond pas"
                }else{
                    finishButton!!.isEnabled = true
                }
            }

        })
    }
    private fun notequal(): Boolean {
        return  confirmNewPassword!!.text.toString() != newPassword!!.text.toString()
    }
}