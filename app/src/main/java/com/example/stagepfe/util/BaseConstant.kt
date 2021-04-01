package com.example.stagepfe.util

//singleton
class BaseConstant {


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