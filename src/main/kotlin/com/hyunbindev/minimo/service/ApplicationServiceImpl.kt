package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.application.ApplicationData
import com.hyunbindev.minimo.repository.ApplicationRepository


object ApplicationServiceImpl : ApplicationService {
    override fun getApplicationData(applicationName: String): ApplicationData? {
        TODO("Not yet implemented")
    }

    override fun saveApplicationData(applicationData: ApplicationData) {
        ApplicationRepository.createApplication(applicationData)
    }
}