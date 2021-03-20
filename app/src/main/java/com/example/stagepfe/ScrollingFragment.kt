package com.example.stagepfe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScrollingFragment : Fragment(), View.OnClickListener {
    var InscriptionButton: Button? = null
    var ForgotPassWord: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrolling, container, false)
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
            var FirstInscriptioFr = InscriptionFirstPagFragmnt()
            fragmentManager!!.beginTransaction().replace(R.id.ContainerFragmentLayout,FirstInscriptioFr).commit()
        }
    }
}