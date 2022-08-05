package com.itsfrz.authentication.data.utils

import android.util.Log

object ContactLog {
        private const val SHOW_LOGS = true

        fun infoLog(tag : String,msg : String){
              if(SHOW_LOGS) Log.i(tag, "infoLog: ${msg}")
        }

        fun debugLog(tag : String,msg : String){
            if (SHOW_LOGS) Log.d(tag, "debugLog: ${msg}")
        }

        fun errorLog(tag : String,msg : String){
            if (SHOW_LOGS) Log.e(tag, "errorLog: ${msg}")
        }
}