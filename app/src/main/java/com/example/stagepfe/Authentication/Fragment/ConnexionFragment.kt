 package com.example.stagepfe.Authentication.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Authentication.Activity.ContainerFragmentPasswordActivity
import com.example.stagepfe.R

class ConnexionFragment : Fragment(), View.OnClickListener {
    private var inscriptionButton: Button? = null
    private var forgotPassWord: TextView? = null



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
        forgotPassWord = view.findViewById<TextView>(R.id.Motdepasseoubliee)
        inscriptionButton = view.findViewById<Button>(R.id.InscriptionButton)

        inscriptionButton!!.setOnClickListener {
            var FirstInscriptionFr = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction().replace(R.id.ContainerFragmentLayout, FirstInscriptionFr).commit()
        }
        forgotPassWord!!.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, ContainerFragmentPasswordActivity::class.java))
                finish() // If activity no more needed in back stack
            }

        }

    }

    override fun onClick(v: View?) {


    }
}