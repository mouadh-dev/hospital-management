package com.example.stagepfe.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.ContainerFragmentPasswordActivity
import com.example.stagepfe.R

class ConnexionFragment : Fragment(), View.OnClickListener {
    private var InscriptionButton: Button? = null
    private var ForgotPassWord: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_connexion, container, false)
        init(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun init(view: View) {
        ForgotPassWord = view.findViewById<TextView>(R.id.Motdepasseoubliee)
        InscriptionButton = view.findViewById<Button>(R.id.InscriptionButton)

        InscriptionButton!!.setOnClickListener {
            var FirstInscriptionFr = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction().replace(R.id.ContainerFragmentLayout, FirstInscriptionFr).commit()
        }
        ForgotPassWord!!.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, ContainerFragmentPasswordActivity::class.java))
                finish() // If activity no more needed in back stack
            }

        }

    }

    override fun onClick(v: View?) {


    }
}