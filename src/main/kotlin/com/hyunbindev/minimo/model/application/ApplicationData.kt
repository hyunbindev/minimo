package com.hyunbindev.minimo.model.application

import javafx.scene.image.Image

data class ApplicationData(
    val pid:Long,
    val applicationName:String?,
    val descriptionName:String?,
    val iconImage: Image?
)