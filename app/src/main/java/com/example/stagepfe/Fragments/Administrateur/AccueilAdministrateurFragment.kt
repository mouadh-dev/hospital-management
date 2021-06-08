package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Administrateur.MyAdapterPostAdmin
import com.example.stagepfe.Adapters.Doctor.MyAdapterPostDoctor
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication
import java.util.ArrayList


class AccueilAdministrateurFragment : Fragment() {
    var adapterPostAdmin : MyAdapterPostAdmin? = null
    var listViewPostAdmin: ListView? = null
    var listPostAdmin = ArrayList<Publication>()
    var userDao = UserDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listViewPostAdmin = view.findViewById(R.id.list_publication_admin)
        initAdapter()

        adapterPostAdmin!!.clear()
        userDao.getPost(object : PostCallback {
            override fun successPost(publication: Publication) {
                listPostAdmin.add(publication)
                adapterPostAdmin!!.notifyDataSetChanged()
            }

            override fun failurePost() {
            }
        })

    }

    private fun initAdapter() {
        adapterPostAdmin = MyAdapterPostAdmin(requireContext(),R.layout.list_publication_administrateur,listPostAdmin)
        listViewPostAdmin!!.adapter = adapterPostAdmin
    }

}