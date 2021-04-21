package com.example.stagepfe.Dao
import com.example.stagepfe.entite.Appointment

interface AppointmentCallback {
    fun successAppointment(appointment: Appointment)
    fun failureAppointment()
}