package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowMessageListDoctor
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DatabaseError


class DoctorMessageFragment : Fragment() {
    var listMessageDoctor: ListView? = null
    var adapterMessageDoctor: MyAdapterShowMessageListDoctor? = null
    var listDoctor = mutableListOf<UserItem>()
    var emptyDoctor: TextView? = null
    var userList = mutableListOf<String>()
    var currentId: String? = null
    var userDao = UserDao()
    var idDoctor: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
       var view=  inflater.inflate(R.layout.fragment_doctor_message, container, false)
        initView(view)
        return view

    }

    private fun initView(view: View) {
        listMessageDoctor = view.findViewById<ListView>(R.id.Message__docteur)
        emptyDoctor = view.findViewById<TextView>(R.id.aucun_Message_doctor)
        initAdapter()
        listMessageDoctor!!.visibility = View.VISIBLE

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
                        listDoctor.add(userItem);
                        adapterMessageDoctor!!.notifyDataSetChanged()
                        emptyDoctor!!.visibility = View.GONE
                    }
                }
            }

            override fun failure() {
            }
        })

        listMessageDoctor!!.setOnItemClickListener { parent, view, position, id ->
            requireActivity().run {
                var intent =
                    Intent(this, ChatPtientActivity::class.java)
                var DoctorId = adapterMessageDoctor!!.getItem(position)
                intent.putExtra("id", DoctorId.id)
                startActivity(intent)
            }
        }


    }

    private fun initAdapter() {
        adapterMessageDoctor = MyAdapterShowMessageListDoctor(requireContext(), R.layout.message_patients_list_for_doctor, listDoctor)
        listMessageDoctor!!.adapter = adapterMessageDoctor
    }

}
