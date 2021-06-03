package com.example.stagepfe.Fragments.Patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.MyAdapterMessagePatient
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DatabaseError


class MessagePatientFragment : Fragment() {
    var listMessagePatient: ListView? = null
    var adapter: MyAdapterMessagePatient? = null
    var userList = mutableListOf<String>()
    var timeList = mutableListOf<String>()
    var listMessage = mutableListOf<String>()
    var listPatient = mutableListOf<UserItem>()

    var currentId: String? = null
    var userDao = UserDao()
    var idDoctor: String? = null

    var empty: TextView? = null
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
        empty = view.findViewById<TextView>(R.id.aucun_Message)
        initAdapter()
        listMessagePatient!!.visibility = VISIBLE
//

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                currentId = userItem.id
            }

            override fun failure() {
            }
        })

        userDao.getMessage(object : MessageCallback {
            override fun success(message: Message) {

                if (message.sender.equals(currentId)) {
                    userList.add(message.reciever.toString())
                }
                if (message.reciever.equals(currentId)) {
                    userList.add(message.sender.toString())
                }
            }

            override fun failure(error: DatabaseError) {
            }
        })
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var idCompare = ""
                for(id in userList){
                    if (userItem.id.equals(id) && idCompare != id){
                        idCompare = userItem.id.toString()
                        listPatient.add(userItem);
                        adapter!!.notifyDataSetChanged()
                        empty!!.visibility = GONE
                    }
                }


            }

            override fun failure() {
            }
        })

        listMessagePatient!!.setOnItemClickListener { parent, view, position, id ->
            requireActivity().run {
                var intent =
                    Intent(this, ChatPtientActivity::class.java)
                var PatientId = adapter!!.getItem(position)
                intent.putExtra("id", PatientId.id)
                startActivity(intent)
            }
        }
    }
    private fun initAdapter() {
        adapter = MyAdapterMessagePatient(requireContext(), R.layout.message_patient, listPatient)
        listMessagePatient!!.adapter = adapter
    }


}

