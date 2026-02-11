package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.clipboard.ApplicationData

interface ApplicationService {
    fun isRegistered(applicationName:String):Boolean
    fun getApplicationData(applicationName:String): ApplicationData
}