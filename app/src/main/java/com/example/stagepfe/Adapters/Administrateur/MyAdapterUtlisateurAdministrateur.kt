package com.example.stagepfe.Adapters.Administrateur

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.*
import com.example.stagepfe.R
import com.example.stagepfe.entite.*
import com.google.firebase.database.DatabaseError

class MyAdapterUtlisateurAdministrateur(
    var mCtx: Context,
    var resources: Int,
    var items: List<UserItem>
) : ArrayAdapter<UserItem>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: de.hdodenhof.circleimageview.CircleImageView =
            view.findViewById(R.id.image_list_utilisateur)
        var nameUtilisateur: TextView = view.findViewById(R.id.name_utilisateur_list)
        var natureUtilisateur: TextView = view.findViewById(R.id.nature_utilisateur)
        var buttonDelete: Button = view.findViewById(R.id.SupprimerButtom)


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
                .setText("Voulez vous vraiment supprimer cette utilisateur ?")
            dialog.findViewById<Button>(R.id.btn_confirm).setText("Oui")
            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                dialog.dismiss()
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
                                    if (mItem.id.equals(message.reciever) || mItem.id.equals(
                                            message.sender
                                        )
                                    ) {
                                        userDao.supperMessage(message.id!!, mItem,
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

                            userDao.retrieveNotification(object : NotificationCallback {
                                override fun successNotification(notification: Notification?) {
                                    if (mItem.id.equals(notification!!.idDoctor) || mItem.id.equals(
                                            notification.idPatient
                                        )
                                    ) {
                                        userDao.supperNotification(notification.id!!, userItem,
                                            object : UserCallback {
                                                override fun onSuccess(userItem: UserItem) {

                                                }

                                                override fun failure() {
                                                }

                                            })
                                    }
                                }

                                override fun failureNotification() {
                                }
                            })

                            if (userItem.ordonance != null) {
                                for (entry in userItem.ordonance!!.entries) {
                                    var ordonance = entry.value
                                    if (ordonance.idDoctor.equals(mItem.id) || ordonance.idPatient.equals(
                                            mItem.id
                                        )
                                    ) {
                                        userDao.removeOrdonance(userItem.id!!,
                                            mItem.id!!,
                                            ordonance.id!!,
                                            object : ResponseCallback {
                                                override fun success(medicament: String) {

                                                }

                                                override fun success() {
                                                }

                                                override fun failure() {
                                                }
                                            })
                                    }
                                }
                            }
                            if (userItem.rapports != null) {
                                for (entryRapport in userItem.rapports!!.entries) {
                                    var rapport = entryRapport.value
                                    if (rapport.idDoctorRapport.equals(mItem.id) || rapport.idPatientRapport.equals(
                                            mItem.id
                                        )
                                    ) {
                                        userDao.removeRapport(userItem.id!!,
                                            mItem.id!!,
                                            rapport.id!!,
                                            object : ResponseCallback {
                                                override fun success(medicament: String) {

                                                }

                                                override fun success() {
                                                }

                                                override fun failure() {
                                                }
//
                                            })
                                    }

                                }
                            }
                            if (userItem.rendezVous != null) {
                                userDao.getAppointment(object : AppointmentCallback {
                                    override fun successAppointment(appointment: Appointment) {
                                        if (appointment.idPatient.equals(mItem.id) || appointment.idDoctor.equals(
                                                mItem.id
                                            )
                                        ) {
                                            userDao.supprAppointment(appointment.date!!,
                                                appointment.hour!!,
                                                userItem.id!!,
                                                mItem.id!!,
                                                object : AppointmentCallback {
                                                    override fun successAppointment(appointment: Appointment) {

                                                    }

                                                    override fun failureAppointment() {
                                                    }

                                                })
                                        }
                                    }

                                    override fun failureAppointment() {
                                    }

                                })

                            }

                            userDao.getReclamation(object : ReclamationCallback {
                                override fun success(reclamation: Reclamation) {
                                    if (mItem.id.equals(reclamation.idReclameur)) {
                                        userDao.supprReclamation(reclamation.id!!,
                                            object : ReclamationCallback {
                                                override fun success(reclamation: Reclamation) {

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

                    override fun failure() {
                    }
                })
            }

        }
        return view
    }
}