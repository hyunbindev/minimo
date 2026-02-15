package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.application.ApplicationData

interface ApplicationService {
    fun getApplicationData(applicationName:String): ApplicationData?

    fun saveApplicationData(applicationData: ApplicationData)
}