package com.example.stagepfe.Dao


interface ResponseCallback {
    fun success(medicament: String)
    fun success()
    fun failure()

}