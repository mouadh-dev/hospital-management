package com.example.stagepfe.Fragments.Authentication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

private var speciality: Spinner? = null
private var bioDoctor: EditText? = null
private var buttonReturn: Button? = null
private var buttonFinish: Button? = null
private var role: String? = null
var mContext: Context? = null


class InscriptionDoctorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inscription_doctor, container, false)
        initView(view)
        initdata()
        speciality!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.Specialités)
        ) as SpinnerAdapter

        return view
    }

    private fun initdata() {
        mContext = requireContext()
    }

    private fun initView(view: View) {
        speciality = view.findViewById<Spinner>(R.id.Speciality_Doctor)
        bioDoctor = view.findViewById<EditText>(R.id.Bio_Doctor)
        buttonReturn = view.findViewById<Button>(R.id.ReturnButtonDoctorInscription)
        buttonFinish = view.findViewById<Button>(R.id.FinishInscriptionDoctor)

        buttonReturn!!.setOnClickListener {
            var ChoosePosition = ChoosePositionFragment()
            requireFragmentManager().beginTransaction()!!
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



                    var connexionpage = ConnexionFragment()
                    var bundle = Bundle()
                    connexionpage.arguments = bundle

                    var user: UserItem = requireArguments().get("datachooseposition") as UserItem


                    var userDao = UserDao()
                    user.speciality = speciality!!.selectedItem.toString()
                    user.bio = bioDoctor!!.text.toString()

                    bundle.putParcelable("dataDoctor", user)

                    println("mouadh" + user.toString())
////////////////////////////////////////////////////////////////////////////////////////////////////

                    var v = android.view.View.inflate(
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
////////////////////////////////////////////////////////////////////////////////////////////////////

                    userDao.signUpUser(requireActivity(), user, object : ResponseCallback {
                        override fun success() {
                            progressdialog.dismiss()

                            var v = android.view.View.inflate(
                                mContext,
                                R.layout.fragment_dialog,
                                null
                            )
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.findViewById<TextView>(R.id.TitleDialog).text =
                                "votre compte a été créé avec succès"
                            dialog.findViewById<ImageView>(R.id.CheckDialog)
                                .setBackgroundResource(R.drawable.ellipse_green)
                            dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                            dialog.findViewById<TextView>(R.id.msgdialog).visibility = android.view.View.GONE

                            dialog.findViewById<Button>(R.id.btn_confirm)
                                .setOnClickListener {
                                    requireFragmentManager().beginTransaction()
                                        .replace(R.id.ContainerFragmentLayout, connexionpage).commit()
                                    dialog.dismiss()
                                }
                        }

                        override fun failure() {
                            progressdialog.dismiss()
                            var v = android.view.View.inflate(
                                mContext,
                                R.layout.fragment_dialog,
                                null
                            )
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.findViewById<TextView>(R.id.TitleDialog).text =
                                "il y a une faute réessayez"

                            dialog.findViewById<Button>(R.id.btn_confirm)
                                .setOnClickListener {
                                    dialog.dismiss()
                                }

                        }
                    })



                }
            }
        }
    }



