package com.example.stagepfe.Dao

import com.example.stagepfe.entite.*

interface IGestionUser{


    fun insertUser(userItem: UserItem)
    fun insertappointment(appointment: Appointment,uid: String,notification:Notification,responseCallback: AppointmentCallback)
    fun insertReclamation(reclamation: Reclamation)
    fun insertordonance(idDoctor:String, idPatient:String, ordonance: Ordonance,userItem: UserItem,notification:Notification,ordonanceCallback: OrdonanceCallback)
    fun insertRapport(rapports: Rapports, userItem: UserItem, idPatientRapport:String, idDoctorRapport: String,notification:Notification,responseCallback: ResponseCallback)
}