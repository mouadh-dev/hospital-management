package com.example.stagepfe.Adapters.Administrateur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Administrateur.ModelUtilisateursAdministrateur
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DatabaseError

class MyAdapterUtlisateurAdministrateur (var mCtx: Context, var resources:Int, var items:List<UserItem>): ArrayAdapter<UserItem>(mCtx, resources, items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.image_list_utilisateur)
        var nameUtilisateur: TextView = view.findViewById(R.id.name_utilisateur_list)
        var natureUtilisateur: TextView = view.findViewById(R.id.nature_utilisateur)
        var buttonDelete:Button = view.findViewById(R.id.SupprimerButtom)


        var mItem: UserItem = items[position]
        nameUtilisateur.text = mItem.nom + " " + mItem.prenom
        natureUtilisateur.text = mItem.role!![0]
        var userDao = UserDao()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                Glide
                    .with(mCtx)
                    .load(userItem.profilPhotos)
                    .into(imageList)
            }

            override fun failure() {

            }
        })
        buttonDelete.setOnClickListener {
            userDao.populateSearch(object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    if (userItem.id.equals(mItem.id)) {
                        userDao.supperUser(userItem.id!!, userItem, object : UserCallback {
                            override fun onSuccess(userItem: UserItem) {

                            }

                            override fun failure() {
                            }
                        })
                        userDao.getMessage(object : MessageCallback {
                            override fun success(message: Message) {
                                if (userItem.id.equals(message.reciever) || userItem.id.equals(message.sender)) {
                                    userDao.supperMessage(message.id!!, userItem,
                                        object : UserCallback {
                                            override fun onSuccess(userItem: UserItem) {

                                            }

                                            override fun failure() {
                                            }
                                        })
                                }
                            }

                            override fun failure(error: DatabaseError) {
                            }
                        })
                    }

                }

                override fun failure() {
                }
            })
        }
        return view
    }
}