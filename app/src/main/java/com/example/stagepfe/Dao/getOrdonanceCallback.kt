package com.example.stagepfe.Dao

import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance

interface getOrdonanceCallback {
    fun successOrdonance(ordonance: Ordonance,med:MedicamentOrdonance)
    fun failureOrdonance()
}