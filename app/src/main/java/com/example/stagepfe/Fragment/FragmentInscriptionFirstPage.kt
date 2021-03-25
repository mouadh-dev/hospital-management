package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stagepfe.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.ShapeAppearanceModel.builder
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder
import java.util.stream.LongStream.builder



class FragmentInscriptionFirstPage : Fragment() {

       private var ButtonReturn: Button? = null
      lateinit var ButtonNext: Button
       private var FirstName: EditText? = null
       private var LastName: EditText? = null
       private var Mail: EditText? = null
       private var Password: EditText? = null
       private var ConfirmPass: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_inscription_first_page, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        ButtonReturn = view.findViewById<Button>(R.id.ReturnbuttonFirstPage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonFirstPage)
        LastName = view.findViewById(R.id.InscriptionLastNameFirstPage)
        FirstName = view.findViewById(R.id.InscriptionFirstNameFirstPage)
        Mail = view.findViewById(R.id.InscriptionMailFirstPage)
        Password = view.findViewById(R.id.InscriptionPasswordFirstPage)
        ConfirmPass = view.findViewById(R.id.InscriptionConfirmPassFirstPage)

        ButtonReturn!!.setOnClickListener {
            val connexionFragment = ConnexionFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, connexionFragment)
                .commit()
        }
        ButtonNext!!.setOnClickListener {
            if (LastName!!.text.isEmpty() || FirstName!!.text.isEmpty() || Mail!!.text.isEmpty() || Password!!.text.isEmpty() || ConfirmPass!!.text.isEmpty()) {
//                Toast.makeText(context, "", Toast.LENGTH_SHORT)
//                .show()
//                val builder = AlertDialog.Builder(requireContext())
//                //builder.setTitle("Are you sure!")
//                builder.setMessage("le champ est obligatoire !")
//
//
//                builder.setNegativeButton("d'accord") { dialogInterface: DialogInterface, i: Int -> }
//                builder.show()

                val v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(v)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.white)






            } else {

                val secondPage = InscriptionSecondPageFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, secondPage)
                    .commit()
            }
        }
    }

}
