package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.stagepfe.AuthenticationFragmentContainerActivity
import com.example.stagepfe.ContainerFragmentPasswordActivity
import com.example.stagepfe.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {
    var BackIcon: ImageView? = null
    var NextButton: Button? = null
    var MailForgotPassword: EditText? = null


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
        BackIcon = view.findViewById<ImageView>(R.id.IconReturnBack)
        NextButton = view.findViewById<Button>(R.id.NextForgotPassword)
        MailForgotPassword = view.findViewById(R.id.MailForgotPassword)
        NextButton!!.setOnClickListener {
            var TapTheCode = FragmentTapTheCode()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerForgotPassword, TapTheCode)!!.commit()
        }




    }


}