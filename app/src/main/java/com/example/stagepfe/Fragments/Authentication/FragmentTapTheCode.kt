package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.stagepfe.R


class FragmentTapTheCode : Fragment() {

    var secondIconBack: ImageView? = null
    var nextButtonCode: Button? = null
    var code: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_tap_the_code, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        secondIconBack = view.findViewById(R.id.IconReturnBack)
        nextButtonCode = view.findViewById(R.id.NextTapTheCode)
       code = view.findViewById(R.id.CodeEditText)

        nextButtonCode!!.setOnClickListener {
             if (code!!.text.isEmpty()) {
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
                var NewPasswordPage = FragmentNewPassword()
                requireFragmentManager().beginTransaction()!!
                    .replace(R.id.ContainerForgotPassword, NewPasswordPage)!!.commit()
             }

        }
    }
}