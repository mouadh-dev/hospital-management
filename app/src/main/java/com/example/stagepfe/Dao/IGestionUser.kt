package com.example.stagepfe.Dao

import com.example.stagepfe.entite.*

interface IGestionUser{


    fun insertUser(userItem: UserItem)
    // fun insertPhoto(profilPhoto: ProfilPhoto, userItem: UserItem,responseCallback: ResponseCallback )
    fun insertappointment(appointment: Appointment,userItem: UserItem,uid: String,responseCallback: AppointmentCallback)
    fun insertReclamation(reclamation: Reclamation)
    fun insertordonance(idDoctor:String, idPatient:String, ordonance: Ordonance,userItem: UserItem,ordonanceCallback: OrdonanceCallback)
    fun insertRapport(rapports: Rapports, userItem: UserItem, idPatientRapport:String, idDoctorRapport: String,responseCallback: ResponseCallback)
}