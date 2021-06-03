package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Notification

interface NotificationCallback {
    fun successNotification(notification: Notification?)
    fun failureNotification()
}