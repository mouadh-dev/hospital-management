package com.example.stagepfe.Dao
import com.example.stagepfe.entite.Appointment
import java.util.HashMap

interface AppointmentCallback {
    fun successAppointment(appointment: Appointment)
    fun failureAppointment()
}