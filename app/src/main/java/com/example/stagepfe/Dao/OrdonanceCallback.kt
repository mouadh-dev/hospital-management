package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Ordonance

interface OrdonanceCallback {
    fun successOrdonance(ordonance: Ordonance)
    fun failureOrdonance()
}