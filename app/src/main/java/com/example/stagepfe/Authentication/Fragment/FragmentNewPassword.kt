package com.example.stagepfe.Authentication.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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
import com.example.stagepfe.Authentication.Activity.AuthenticationFragmentContainerActivity
import com.example.stagepfe.R


class FragmentNewPassword : Fragment() {

    private var finishButton: Button? = null
    private var newPassword: EditText? = null
    private var  confirmNewPassword: EditText? = null
     private var eyeShowNewPassword: ImageView? = null

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
        newPassword = view.findViewById(R.id.New_Password)
        confirmNewPassword = view.findViewById(R.id.Confirm_NewPassword)
        eyeShowNewPassword= view.findViewById<ImageView>(R.id.eyeShowPassword)

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
                    .setText("votre compte a été créé avec succès")
                dialog.findViewById<ImageView>(R.id.CheckDialog)
                    .setBackgroundResource(R.drawable.ellipse_green)
                dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()

                        requireActivity().run {
                            var intent =
                                Intent(this, AuthenticationFragmentContainerActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            }
        }
        newPassword!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPassword!!.length() <= 8){
                    finishButton!!.isEnabled = false
                    finishButton!!.setBackgroundResource(R.drawable.gray_button)
                    newPassword!!.error = "le mot de passe est faible "
                }else{
                    finishButton!!.isEnabled = true
                    finishButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })
        confirmNewPassword!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    finishButton!!.isEnabled = false
                    finishButton!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPassword!!.error = "le mot de passe ne correspond pas"
                }else{
                    finishButton!!.isEnabled = true
                    finishButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })
        eyeShowNewPassword!!.setOnClickListener {

                if(newPassword!!.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
        {
          newPassword!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }else {
          newPassword!!.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        }



//        public void ShowHidePass(View view){
//
//            if(eye){
//
//                if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                    ((ImageView(view)).setImageResource(R.drawable.hide_password);
//
//                    //Show Password
//                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//                else{
//                    ((ImageView)(view)).setImageResource(R.drawable.show_password);
//
//                    //Hide Password
//                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//                }
//            }
//        }

    }

    private fun notequal(): Boolean {
        return  confirmNewPassword!!.text.toString() != newPassword!!.text.toString()
    }


}