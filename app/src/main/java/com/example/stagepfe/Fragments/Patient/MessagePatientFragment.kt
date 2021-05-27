package com.example.stagepfe.Fragments.Patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.MyAdapterMessagePatient
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelMessagePatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem


class MessagePatientFragment : Fragment() {
    var listMessagePatient: ListView? = null
    var list = mutableListOf<ModelMessagePatient>()
    var adapter: MyAdapterMessagePatient? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_message_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listMessagePatient = view.findViewById<ListView>(R.id.Message__Patient)
        initAdapter()
        listMessagePatient!!.visibility = VISIBLE

       var userDao = UserDao()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getMessage(object : MessageCallback {
                    override fun success(message: Message) {
                        if (userItem.id.equals(message.sender) || userItem.id.equals(message.reciever)){
                            list.add(ModelMessagePatient(message.message!!, message.message!!, "12:44", R.drawable.doctor_ic))
                            adapter!!.notifyDataSetChanged()
                        }
                    }

                    override fun failure() {
                    }
                })
            }

            override fun failure() {
            }
        })

//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))


        listMessagePatient!!.setOnItemClickListener { parent, view, position, id ->
            requireActivity().run {
                var intent =
                    Intent(this, ChatPtientActivity::class.java)
                startActivity(intent)
//                finish()
            }
        }
    }

    private fun initAdapter() {

        adapter = MyAdapterMessagePatient(requireContext(), R.layout.message_patient, list)
        listMessagePatient!!.adapter = adapter
    }


}