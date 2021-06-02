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
    var listDoctor = mutableListOf<Message>()
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
        var idcompare = ""

        userDao.getMessage(object : MessageCallback {
            override fun success(message: Message) {
//                listMessage.clear()
                if (message.sender.equals(currentId) && (message.reciever!=idcompare)) {

                    userList.add(message.reciever.toString())
                    idcompare = message.reciever.toString()
                    var test = Message()
//                    test.message = message.message
//                    test.id = message.id
                    test.reciever = message.reciever
//                    test.sender = message.sender
                    test.timemsg = message.timemsg
                    listDoctor.add(test)
                    adapterMessageDoctor!!.notifyDataSetChanged()
                    emptyDoctor!!.visibility = View.GONE
                }
                if (message.reciever.equals(currentId) && (message.sender!=idcompare)) {

                    userList.add(message.sender.toString())
                    idcompare = message.sender.toString()
                    var test = Message()
//                    test.message = message.message
//                    test.id = message.id
//                    test.reciever = message.reciever
                    test.sender = message.sender
                    test.timemsg = message.timemsg
                    listDoctor.add(test)
                    adapterMessageDoctor!!.notifyDataSetChanged()
                    emptyDoctor!!.visibility = View.GONE
                }

                listMessageDoctor!!.setOnItemClickListener { parent, view, position, id ->
                    var item = listDoctor[position]


                    requireActivity().run {
                        var intent =
                            Intent(this, ChatPtientActivity::class.java)
//                        intent.putExtra("id",item.reciever)
                        intent.putExtra("id",userList[position] )
                        startActivity(intent)
                    }
                }

            }
            override fun failure(error: DatabaseError) {
            }
        })


        initAdapter()
    }

    private fun initAdapter() {
        adapterMessageDoctor = MyAdapterShowMessageListDoctor(requireContext(), R.layout.message_patients_list_for_doctor, listDoctor)
        listMessageDoctor!!.adapter = adapterMessageDoctor
    }

}
