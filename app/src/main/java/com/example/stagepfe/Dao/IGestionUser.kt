package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem

interface IGestionUser{


    fun insertUser(userItem: UserItem)
    fun insertappointment(appointment: Appointment,userItem: UserItem)

}