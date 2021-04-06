 package com.example.stagepfe.Authentication.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Authentication.Activity.ContainerFragmentPasswordActivity
import com.example.stagepfe.R
import com.example.stagepfe.Authentication.dao.ResponseCallback
import com.example.stagepfe.Authentication.dao.UserDao
import com.example.stagepfe.Authentication.entite.UserItem
import com.example.stagepfe.Patient.AccountPatientActivity
import com.google.firebase.auth.FirebaseAuth

 class ConnexionFragment : Fragment(), View.OnClickListener {
    private var inscriptionButton: Button? = null
    private var forgotPassWord: TextView? = null
    private var connectButton: Button? = null
    private var mailConnexionET: EditText? = null
    private var passwordConnectET: EditText? = null
    private lateinit var mAuth: FirebaseAuth
    private var mContext: Context? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_connexion, container, false)
        init(view)
        mContext= requireContext()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun init(view: View) {
        forgotPassWord = view.findViewById<TextView>(R.id.Motdepasseoubliee)
        inscriptionButton = view.findViewById<Button>(R.id.InscriptionButton)
        connectButton = view.findViewById<Button>(R.id.Connect_Button)
        mailConnexionET = view.findViewById<EditText>(R.id.Mail_Connexion)
        passwordConnectET = view.findViewById<EditText>(R.id.Password_Connexion)

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
        ////////////////////////////////////////////////////////////////////////////////
        connectButton!!.setOnClickListener {

            if (mailConnexionET!!.text.toString().isEmpty() || passwordConnectET!!.text.toString().isEmpty()){
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
                    .setText("Email ou adresse n'est pas correcte")
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()
                    }
            }else{

                var userDao = UserDao()


                var v = View.inflate(
                    mContext,
                    R.layout.progress_dialog,
                    null
                )
                var builder = AlertDialog.Builder(mContext)
                builder.setView(v)

                var progressdialog = builder.create()
                progressdialog.show()
                progressdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                progressdialog.setCancelable(false)
                userDao.signIn(requireActivity(),
                    UserItem(mail = mailConnexionET!!.text.toString(),password = passwordConnectET!!.text.toString()),
                    object : ResponseCallback {



                    override fun success() {

                        var v = View.inflate(
                            mContext,
                            R.layout.fragment_dialog,
                            null
                        )

                        progressdialog.dismiss()
                        requireActivity().run {
                            startActivity(Intent(this, AccountPatientActivity::class.java))
                            finish() // If activity no more needed in back stack
                        }

                    }

                    override fun failure() {
                        progressdialog.dismiss()
                        var v = View.inflate(
                            mContext,
                            R.layout.fragment_dialog,
                            null
                        )
                        var builder = AlertDialog.Builder(requireContext())
                        builder.setView(v)

                        var dialog = builder.create()
                        dialog.show()
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        dialog.findViewById<TextView>(R.id.TitleDialog)
                            .setText("Email ou adresse n'est pas correcte")
                        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                        dialog.findViewById<Button>(R.id.btn_confirm)
                            .setOnClickListener {
                                dialog.dismiss()
                            }
                    }

                })
            }

        }

    }

    override fun onClick(v: View?) {


    }
}