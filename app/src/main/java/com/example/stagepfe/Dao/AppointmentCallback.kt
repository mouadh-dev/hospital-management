package com.example.stagepfe.Dao
import com.example.stagepfe.entite.Appointment

interface AppointmentCallback {
    fun success(appointment: Appointment)
    fun failure()
}