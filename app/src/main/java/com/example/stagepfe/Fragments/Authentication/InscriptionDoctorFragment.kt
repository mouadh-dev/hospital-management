package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.R
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.entite.UserItem

private var speciality: Spinner? = null
private var bioDoctor: EditText? = null
private var buttonReturn: Button? = null
private var buttonFinish: Button? = null
private var role: String? = null


class InscriptionDoctorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inscription_doctor, container, false)
        initView(view)
        speciality!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.Specialités)
        ) as SpinnerAdapter

        return view
    }

    private fun initView(view: View) {
        speciality = view.findViewById<Spinner>(R.id.Speciality_Doctor)
        bioDoctor = view.findViewById<EditText>(R.id.Bio_Doctor)
        buttonReturn = view.findViewById<Button>(R.id.ReturnButtonDoctorInscription)
        buttonFinish = view.findViewById<Button>(R.id.FinishInscriptionDoctor)

        buttonReturn!!.setOnClickListener {
            var ChoosePosition = ChoosePositionFragment()
            fragmentManager!!.beginTransaction()!!
                .replace(R.id.ContainerFragmentLayout, ChoosePosition)!!.commit()
        }


///********************************button finish**********************************************

        buttonFinish!!.setOnClickListener {

            if (bioDoctor!!.text.isEmpty()) {
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


                var View = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(View)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("votre compte a été créé avec succès")
                dialog.findViewById<ImageView>(R.id.CheckDialog).setBackgroundResource(R.drawable.ellipse_green)
                dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = android.view.View.GONE
                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                    var connexionpage = ConnexionFragment()
                    var bundle = Bundle()
                    connexionpage.arguments = bundle

                    var user: UserItem = requireArguments().get("datachooseposition") as UserItem


                    var userDao= UserDao()
                    user.speciality = speciality!!.selectedItem.toString()
                    user.bio = bioDoctor!!.text.toString()

                    bundle.putParcelable("dataDoctor", user)

                    println("mouadh" + user.toString())

                    userDao.insertUser(user)
////////////////////////////////////////////////////////////////////////////////////////////////////

                    requireFragmentManager().beginTransaction()
                        .replace(R.id.ContainerFragmentLayout, connexionpage).commit()
                }
            }
        }
    }



}
