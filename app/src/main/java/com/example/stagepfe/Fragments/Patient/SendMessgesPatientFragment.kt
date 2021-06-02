package com.example.stagepfe.Fragments.Patient

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.AdapterMessageText
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SendMessgesPatientFragment : Fragment() {
    var recyclerViewMessages: RecyclerView? = null
    var mMsg = ArrayList<Message>()
    var adapterChat: AdapterMessageText? = null
    var userDao = UserDao()
    var messageText: EditText? = null
    var sendMessage: ImageView? = null
    var test:String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messges_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        @RequiresApi(Build.VERSION_CODES.O)
        recyclerViewMessages = view.findViewById<RecyclerView>(R.id.Messages)
        sendMessage = view.findViewById(R.id.sendMessageIcon)
        messageText = view.findViewById(R.id.Message_text_Patient)

        recyclerViewMessages!!.setHasFixedSize(true)
        val linearLayoutManager: LinearLayoutManager =  LinearLayoutManager(Application())
        linearLayoutManager.stackFromEnd = true
        recyclerViewMessages!!.layoutManager = linearLayoutManager


        val activity: ChatPtientActivity? =
            requireActivity() as ChatPtientActivity?
        val idReciever: String = activity!!.getIDReciever()
        println("mouadh ! $idReciever")

//        initAdapter()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                readMessage(userItem.id!!,idReciever)

            }

            override fun failure() {
            }
        })






        sendMessage!!.setOnClickListener {
            val text = messageText!!.text.toString()
            val message = Message()

            userDao.populateSearch(object : UserCallback {

                override fun onSuccess(user: UserItem) {
                    if(user.id.equals(idReciever)){
                    message.reciever = idReciever
                        userDao.retrieveCurrentDataUser(object : UserCallback {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onSuccess(userItem: UserItem) {

                                if (text != "") {


                                    message.sender = userItem.id

//                            message.nameSender = userItem.prenom + " " + userItem.nom
//
                                    message.timemsg = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                                    message.message = messageText!!.text.toString()
                                    messageText!!.text.clear()
                                    userDao.sendMesage(message)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "vous ne pouvez pas envoyer un message vide",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun failure() {

                            }

                        })

                    }
                }

                override fun failure() {
                }
            })



        }

    }

    private fun sendReciver(text: String, message: Message) {

    }

    private fun initAdapter() {
        adapterChat = AdapterMessageText(requireContext(), mMsg)
        recyclerViewMessages!!.adapter = adapterChat
    }
private fun readMessage(myid: String, userid: String) {
    mMsg = ArrayList()
    initAdapter()
    val reference = FirebaseDatabase.getInstance().getReference("Message")
    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            mMsg.clear()
            for (snapshot in dataSnapshot.children) {
                val msg = snapshot.getValue(Message::class.java)
                if (
                    msg!!.reciever.equals(myid) && msg.sender.equals(userid)
                    ||
                    msg.reciever.equals(userid) && msg.sender.equals(myid)
                ) {
                    mMsg.add(msg)
                }
 adapterChat!!.notifyDataSetChanged()
//                adapterChat = AdapterMessageText(requireContext(), mMsg)
//        recyclerViewMessages!!.adapter = adapterChat
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    })
}
}