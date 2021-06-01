package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        userDao.getMessage(object : MessageCallback {
            override fun success(message: Message) {

                if (message.sender.equals(currentId)) {
                    userList.add(message.reciever.toString())
                }
                if (message.reciever.equals(currentId)) {
                    userList.add(message.sender.toString())
                }

                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(userDoctor: UserItem) {
                        listMessage.clear()
                        var idTocompare = ""
                       for (id in userList){

                           if (userDoctor.id.equals(id)){
                               if (id != idTocompare){
                                   var test = Message()
                                   test.message = message.message
                                   test.id = message.id
                                   test.reciever = message.reciever
                                   test.sender = message.sender
                                   test.timemsg = message.timemsg
                                   listMessage.add(message)
//                                   idTocompare = id
                                   adapter!!.notifyDataSetChanged()
                               }

                           }

                       }

                    }

                    override fun failure() {
                    }
                })

            }

            override fun failure(error: DatabaseError) {
            }
        })


//                                listMessagePatient!!.setOnItemClickListener { parent, view, position, id ->
//
////                    userDao.getMessage(object : MessageCallback {
////                        override fun success(message: Message) {
//////                            if (message.sender.equals(userItem.id)) {
////
//////                finish()
////                                }
//////                            }
////                        }
////
////                        override fun failure(error: DatabaseError) {
////
////                        }
////                    })
//                                    requireActivity().run {
//                                        var ss = adapter!!.getItem(position)
//                                        var intent =
//                                            Intent(this, ChatPtientActivity::class.java)
//                                        intent.putExtra("id", ss.nameReciever)
//                                        startActivity(intent)
//                                    }
//                                }
//                            }
//                }


//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
//        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))


    }

    private fun readChat() {

    }

//    private fun readMessage(list: MutableList<String>) {
//        var userDao = UserDao()
//        userDao.getMessage(object : MessageCallback {
//            override fun success(message: Message) {
//                var userCompare = ""
//                for (item in this@MessagePatientFragment.list) {
//
//                    if (userCompare != item) {
//                        userCompare = item
//                        listMessage.add(message)
//                        this@MessagePatientFragment.list.add(message.reciever!!)
//                        adapter!!.notifyDataSetChanged()
//                        empty!!.visibility = GONE
//
//                    }
//                }


//
//                for (user in list) {
//
//                    if (user != userCompare) {
//                            var test = Message()
//                            test.message = message.message
//                            test.id = message.id
//                            test.reciever = message.reciever
//                            test.sender = message.sender
//                            test.timemsg = message.timemsg


//                    }
//                    }
//            }

    private fun initAdapter() {

        adapter = MyAdapterMessagePatient(requireContext(), R.layout.message_patient, listMessage)
        listMessagePatient!!.adapter = adapter
    }


}