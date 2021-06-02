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
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.MyAdapterMessagePatient
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MessagePatientFragment : Fragment() {
    var listMessagePatient: ListView? = null
    var adapter: MyAdapterMessagePatient? = null
    var userList = mutableListOf<String>()
    var listMessage = mutableListOf<Message>()
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
                    listMessage.add(test)
                    adapter!!.notifyDataSetChanged()
                    empty!!.visibility = GONE
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
                    listMessage.add(test)
                    adapter!!.notifyDataSetChanged()
                    empty!!.visibility = GONE
                }

                listMessagePatient!!.setOnItemClickListener { parent, view, position, id ->
                    var item = listMessage[position]


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

    }


    private fun initAdapter() {
        adapter = MyAdapterMessagePatient(requireContext(), R.layout.message_patient, listMessage)
        listMessagePatient!!.adapter = adapter
    }


}
