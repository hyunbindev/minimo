package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.application.ApplicationData
import javafx.scene.image.Image

object ApplicationServiceImpl : ApplicationService {
    override fun getApplication(pid: Long): ApplicationData? {
        TODO("Not yet implemented")
    }

    override fun getApplicationIcon(applicationData: ApplicationData): Image? {
        TODO("Not yet implemented")
    }
}