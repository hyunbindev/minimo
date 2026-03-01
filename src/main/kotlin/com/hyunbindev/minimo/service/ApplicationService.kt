package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.application.ApplicationData
import javafx.scene.image.Image

interface ApplicationService {
    fun getApplication(pid:Long): ApplicationData?
    fun getApplicationIcon(applicationData: ApplicationData): Image?
}