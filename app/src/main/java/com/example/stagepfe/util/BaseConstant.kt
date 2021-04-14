package com.example.stagepfe.util

//singleton
class BaseConstant {


    val userRef: String = "users"
    val appointments: String = "Appointment"
    


    companion object {
        private var self: BaseConstant? = null

        fun instance(): BaseConstant {
            if (self == null) {
                synchronized(BaseConstant::class.java) {
                    if (self == null) {
                        self = BaseConstant()
                    }
                }
            }
            return self!!
        }

    }

}