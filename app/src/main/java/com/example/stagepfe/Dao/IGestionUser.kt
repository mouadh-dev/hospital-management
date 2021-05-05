package com.example.stagepfe.Dao

import com.example.stagepfe.entite.*

interface IGestionUser{


    fun insertUser(userItem: UserItem)
    fun insertappointment(appointment: Appointment,userItem: UserItem,uid: String,responseCallback: AppointmentCallback)
    fun insertReclamation(reclamation: Reclamation)
    fun insertordonance(ordonance: Ordonance,userItem: UserItem,ordonanceCallback: OrdonanceCallback)
    fun insertRapport(rapports: Rapports, userItem: UserItem, uid: String, responseCallback: ResponseCallback)
}