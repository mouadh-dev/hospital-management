package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Patients.adapterMessageText
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import java.util.*


class MessgesPatientFragment : Fragment() {
    var listViewMessages:ListView? = null
    var listMessage = mutableListOf<Message>()
    var adapter: adapterMessageText? = null

    var userDao = UserDao()
    var messageText:EditText? = null
    var sendMessage:ImageView? = null




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
        listViewMessages = view.findViewById<ListView>(R.id.Messages)
        sendMessage = view.findViewById(R.id.sendMessageIcon)
        messageText = view.findViewById(R.id.Message_text_Patient)
        initAdapter()

//        val linearLayoutManager: LineareLayoutManager =
//            LinearLayoutManager(ApplicationProvider.getApplicationContext<Context>())
//        linearLayoutManager.setStackFormEnd(true)

//        var message = Message()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getMessage(object : MessageCallback {
                    override fun success(msg: Message) {
                        if (msg.sender.equals(userItem.id)) {
                            messageText!!.setText(msg.message)
                            listMessage.add(msg)
//                            Collections.sort(listMessage,
//                                Comparator<Any?> { o1, o2 ->
//                                    o1.getDateTime().compareTo(o2.getDateTime())
//                                })
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





        sendMessage!!.setOnClickListener {
            var message = Message()
            userDao.retrieveCurrentDataUser(object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    message.sender = userItem.id
                    message.reciever = "test"
                    message.message = messageText!!.text.toString()
                    userDao.sendMesage(message)
                }

                override fun failure() {

                }
            })

            messageText!!.text.clear()
            }
    }

    private fun initAdapter() {
        adapter = adapterMessageText(requireContext(), R.layout.chat_item_right, listMessage)
        listViewMessages!!.adapter = adapter
    }

}