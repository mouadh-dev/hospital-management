package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
                 requireActivity().run {
                     var intent = Intent(this, AuthenticationFragmentContainerActivity::class.java)
                     startActivity(intent)
                     finish()
                 }
             }
    }
}