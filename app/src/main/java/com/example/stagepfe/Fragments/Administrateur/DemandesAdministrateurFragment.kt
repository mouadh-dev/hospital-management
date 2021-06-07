package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Administrateur.NewUsersAdapterAdmin
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Reclamation
import com.example.stagepfe.entite.UserItem


class AddUserAdmin : Fragment() {
    var listViewUsers: ListView? = null
    var listUsers = ArrayList<UserItem>()
    var userDao = UserDao()
    var adapterNewUser:NewUsersAdapterAdmin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_demandes_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listViewUsers = view.findViewById(R.id.listDemandesAdministrateurs)
        initAdapter()

        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.demande != null && userItem.role!!.contains("Patient")){
                    listUsers.add(userItem)
                    adapterNewUser!!.notifyDataSetChanged()
                }
            }

            override fun failure() {
            }
        })


    }

    private fun initAdapter() {
        adapterNewUser = NewUsersAdapterAdmin(requireContext(),R.layout.new_user_admin,listUsers)
        listViewUsers!!.adapter = adapterNewUser
    }


}