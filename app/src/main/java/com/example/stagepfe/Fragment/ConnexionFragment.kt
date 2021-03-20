package com.example.stagepfe.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.R

class ConnexionFragment : Fragment(), View.OnClickListener {
    private var InscriptionButton: Button? = null
    private var ForgotPassWord: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {
        ForgotPassWord!!.findViewById<TextView>(R.id.Motdepasseoubliee)
        InscriptionButton!!.findViewById<Button>(R.id.InscriptionButton)
    }

    override fun onClick(v: View?) {
        InscriptionButton!!.setOnClickListener{
            var FirstInscriptionFr = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction()!!.replace(R.id.ContainerFragmentLayout,FirstInscriptionFr)!!.commit()
        }
    }
}