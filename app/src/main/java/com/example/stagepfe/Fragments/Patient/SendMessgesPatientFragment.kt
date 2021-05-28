package com.example.stagepfe.Fragments.Patient

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.adapterMessageText
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem


class SendMessgesPatientFragment : Fragment() {
    var recyclerViewMessages: RecyclerView? = null
    var listMessage = ArrayList<Message>()
    var adapterChat: adapterMessageText? = null
    var userDao = UserDao()
    var messageText: EditText? = null
    var sendMessage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_messges_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        recyclerViewMessages = view.findViewById<RecyclerView>(R.id.Messages)
        sendMessage = view.findViewById(R.id.sendMessageIcon)
        messageText = view.findViewById(R.id.Message_text_Patient)

        recyclerViewMessages!!.setHasFixedSize(true)
        var linearLayoutManager:LinearLayoutManager =  LinearLayoutManager(Application())
        linearLayoutManager.stackFromEnd = true
        recyclerViewMessages!!.setLayoutManager(linearLayoutManager)


//        initAdapter()



        var message = Message()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getMessage(object : MessageCallback {
                    override fun success(msg: Message) {

                        if (msg.sender.equals(userItem.id) || msg.reciever.equals(userItem.id)) {
                            adapterChat = adapterMessageText(requireContext())
                            recyclerViewMessages!!.adapter = adapterChat

//                            listMessage.add(msg)
//                            listViewMessages!!.adapter = adapterMessageText(requireContext(), R.layout.chat_item_right, listMessage)
//                            Collections.sort(listMessage,
//                                Comparator<Any?> { o1, o2 ->
//                                    o1.getDateTime().compareTo(o2.getDateTime())
//                                })
//                            adapter!!.notifyDataSetChanged()

                        }

                    }

                    override fun failure() {
                    }
                })
            }

            override fun failure() {
            }
        })

//
//        if (messageText!!.text.toString() != "") {
//            sendMessage!!.visibility = VISIBLE
//        }else {
//            sendMessage!!.visibility = INVISIBLE
//        }

        val activity: ChatPtientActivity? =
            requireActivity() as ChatPtientActivity?
        val id: String = activity!!.getID()

        sendMessage!!.setOnClickListener {
            var text = messageText!!.text.toString()

                userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {

                        if (!text.equals("")) {
                            var message = Message()
                            message.sender = userItem.id
                            message.reciever = id
                            message.message = messageText!!.text.toString()
                            messageText!!.text.clear()
                            userDao.sendMesage(message)
                        }else {
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

//    private fun initAdapter() {
//        adapter = adapterMessageText(requireContext(), R.layout.chat_item_right, listMessage)
//        listViewMessages!!.adapter = adapter
//    }

}