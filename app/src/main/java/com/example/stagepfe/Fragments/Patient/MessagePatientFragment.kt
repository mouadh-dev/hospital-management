package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
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
    var list = mutableListOf<String>()

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

        var userDao = UserDao()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getMessage(object : MessageCallback {
                    override fun success(message: Message) {
                        if (userItem.id.equals(message.sender)
                            || message.reciever.equals(userItem.id)
                        ) {
                            if (message.sender.equals(userItem.id)) {
                                list.add(message.reciever!!)
                            }
                            if (message.reciever.equals(userItem.id)) {
                                list.add(message.sender!!)
                            }

//                            var test = Message()
//                            test.message = message.message
//                            test.id = message.id
//                            test.reciever = message.reciever
//                            test.sender = message.sender
//                                test.nameReciever = message.nameReciever
//                                test.nameSender = message.nameSender
//                            test.timemsg = message.timemsg
//
//                            list.add(test)
//                                idDoctor =
                            adapter!!.notifyDataSetChanged()
                            empty!!.visibility = GONE
//                        }

//                        }




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
                        }
                    }

                    override fun failure(error: DatabaseError) {
                        println("mouadh $error")
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


    }

    private fun initAdapter() {

//        adapter = MyAdapterMessagePatient(requireContext(), R.layout.message_patient, list)
//        listMessagePatient!!.adapter = adapter
    }


}