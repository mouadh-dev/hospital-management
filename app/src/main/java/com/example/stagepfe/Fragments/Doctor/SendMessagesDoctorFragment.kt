package com.example.stagepfe.Fragments.Doctor

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Activity.Doctors.chat.ChatDoctorActivity
import com.example.stagepfe.Adapters.Doctor.AdapterMessageTextDoctor
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


class SendMessagesDoctorFragment : Fragment() {
    var recyclerViewMessagesDoctor: RecyclerView? = null
    var mMsgDoctor = ArrayList<Message>()
    var adapterChatDoctor: AdapterMessageTextDoctor? = null
    var userDao = UserDao()
    var messageTextDoctor: EditText? = null
    var sendMessageDoctor: ImageView? = null
    var test:String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_send_messages_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        @RequiresApi(Build.VERSION_CODES.O)
        recyclerViewMessagesDoctor = view.findViewById<RecyclerView>(R.id.MessagesDoctorRV)
        sendMessageDoctor = view.findViewById(R.id.sendMessageIconDoctor)
        messageTextDoctor = view.findViewById(R.id.Message_text_Doctor)
        recyclerViewMessagesDoctor!!.setHasFixedSize(true)
        val linearLayoutManager: LinearLayoutManager =  LinearLayoutManager(Application())
        linearLayoutManager.stackFromEnd = true
        recyclerViewMessagesDoctor!!.layoutManager = linearLayoutManager

        val activity: ChatDoctorActivity? =
            requireActivity() as ChatDoctorActivity?
        val idReciever: String = activity!!.getIDReciever()

        initAdapter()

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                readMessageDoctor(userItem.id!!,idReciever)

            }

            override fun failure() {
            }
        })

        sendMessageDoctor!!.setOnClickListener {
            val text = messageTextDoctor!!.text.toString()
            val message = Message()
            userDao.populateSearch(object : UserCallback {

                override fun onSuccess(user: UserItem) {
                    if(user.id.equals(idReciever)){
//                        message.nameReciever = user.prenom + " " + user.nom
                        message.reciever = idReciever
                    }
                }

                override fun failure() {
                }
            })
            userDao.retrieveCurrentDataUser(object : UserCallback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(userItem: UserItem) {

                    if (text != "") {


                        message.sender = userItem.id

//                        message.nameSender = userItem.prenom + " " + userItem.nom
//
                        message.timemsg = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                        message.message = messageTextDoctor!!.text.toString()
                        messageTextDoctor!!.text.clear()
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

    private fun initAdapter() {
        adapterChatDoctor = AdapterMessageTextDoctor(requireContext(), mMsgDoctor)
        recyclerViewMessagesDoctor!!.adapter = adapterChatDoctor
    }


    private fun readMessageDoctor(myid: String, userid: String) {
        mMsgDoctor = ArrayList()
        initAdapter()
        val reference = FirebaseDatabase.getInstance().getReference("Message")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mMsgDoctor.clear()
                for (snapshot in dataSnapshot.children) {
                    val msg = snapshot.getValue(Message::class.java)
                    if (
                        msg!!.reciever.equals(myid) && msg.sender.equals(userid)
                        ||
                        msg.reciever.equals(userid) && msg.sender.equals(myid)
                    ) {
                        mMsgDoctor.add(msg)
                    }
                    adapterChatDoctor!!.notifyDataSetChanged()
//                adapterChat = AdapterMessageText(requireContext(), mMsg)
//        recyclerViewMessages!!.adapter = adapterChat
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}