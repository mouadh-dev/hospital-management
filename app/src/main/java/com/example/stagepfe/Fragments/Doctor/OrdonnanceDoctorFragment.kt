package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.R


class OrdonnanceDoctorFragment : Fragment() {
    private var medicamentDoctorET: EditText? = null
    private var descriptionMedicamentET: EditText? = null
    private var confirmMedicamentBT: Button? = null
    private var moreMedicamentBT: Button? = null
    private var lessMedicamentBT: Button? = null
    private var numberMedicamentTV: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ordonnance_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        medicamentDoctorET = view.findViewById(R.id.OrdonnanceDoctor)
        descriptionMedicamentET = view.findViewById(R.id.DescriptionOrdonnanceDoctor)
        confirmMedicamentBT = view.findViewById<Button>(R.id.ConfirmOrdonnanceDoctorButton)
        moreMedicamentBT = view.findViewById<Button>(R.id.MoreNumberMedicament)
        lessMedicamentBT = view.findViewById<Button>(R.id.LessNumberMedicament)
        numberMedicamentTV = view.findViewById(R.id.NumberMedicamentDoctor)

        confirmMedicamentBT!!.setOnClickListener {
            if (descriptionMedicamentET!!.text.isEmpty() || medicamentDoctorET!!.text.isEmpty() || numberMedicamentTV!!.text.isEmpty()) {
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
                    .setText("votre ordonnance a été bien envoyée ")
                dialog.findViewById<ImageView>(R.id.CheckDialog)
                    .setBackgroundResource(R.drawable.ellipse_green)
                dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()

                        requireActivity().run {
                            var intent =
                                Intent(this, AccountDoctorActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

            }
        }
    }

}