package com.example.stagepfe.Adapters.Administrateur

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.*

class NewUsersAdapterAdmin (
    var mCtx: Context,
    var resources: Int,
    var items: ArrayList<UserItem>
): ArrayAdapter<UserItem>(mCtx, resources, items) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
            var view: View = layoutInflater.inflate(resources, null)

            var imageList: de.hdodenhof.circleimageview.CircleImageView =
                view.findViewById(R.id.new_user_image)
            var nameUtilisateur: TextView = view.findViewById(R.id.new_user_name)
            var userRole: TextView = view.findViewById(R.id.new_user_role)
            var acceptButton: Button = view.findViewById(R.id.accept_button)
            var declineButton: Button = view.findViewById(R.id.decline_button)


            var mItem: UserItem = items[position]
            nameUtilisateur.text = mItem.nom + " " + mItem.prenom
            userRole.text = mItem.demande

            var userDao = UserDao()
            userDao.populateSearch(object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    if (userItem.id.equals(mItem.id)){
                        if (mItem.profilPhotos != null){
                            Glide
                                .with(mCtx)
                                .load(mItem.profilPhotos)
                                .into(imageList)
                        }
                    }
                }

                override fun failure() {
                }
            })
            acceptButton.setOnClickListener {
                var v = View.inflate(mCtx, R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(mCtx)
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.TextRapportView).visibility = View.VISIBLE
                dialog.findViewById<EditText>(R.id.TextRapport).visibility = View.GONE
                dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.TextRapportView)
                    .setText("Voulez vous vraiment accepter cette utilisateur ?")
                dialog.findViewById<Button>(R.id.btn_confirm).setText("Oui")
                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            if (userItem.id.equals(mItem.id)) {
                                if (mItem.demande != null) {
                                    mItem.role!!.add(mItem.demande!!)
                                    userDao.addRole(mItem.id!!, mItem.role!!)
                                    userDao.supprDemande(mItem.id!!, object : UserCallback {
                                        override fun onSuccess(userItem: UserItem) {
                                            items.remove(mItem)
                                        }

                                        override fun failure() {
                                        }
                                    })
                                }
                            }
                        }

                        override fun failure() {
                        }
                    })
                }
//                var toast = Toast.makeText(mCtx,"test", Toast.LENGTH_SHORT)
//                toast.show()
            }
            declineButton.setOnClickListener {
                var v = View.inflate(mCtx, R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(mCtx)
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.TextRapportView).visibility = View.VISIBLE
                dialog.findViewById<EditText>(R.id.TextRapport).visibility = View.GONE
                dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.TextRapportView)
                    .setText("Voulez vous vraiment rejeter cette utilisateur ?")
                dialog.findViewById<Button>(R.id.btn_confirm).setText("Oui")
                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            if (userItem.id.equals(mItem.id)) {
                                if (mItem.demande != null) {
                                    userDao.supprDemande(mItem.id!!, object : UserCallback {
                                        override fun onSuccess(userItem: UserItem) {
                                            items.remove(mItem)
                                        }

                                        override fun failure() {
                                        }
                                    })
                                }
                            }
                        }

                        override fun failure() {
                        }
                    })
                }
            }


            return view
        }
        override fun getCount(): Int {
            return items.size
        }
        override fun getItem(position: Int): UserItem {
            return items[position]
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
