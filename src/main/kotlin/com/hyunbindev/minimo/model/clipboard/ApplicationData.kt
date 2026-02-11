package com.hyunbindev.minimo.model.clipboard

import javafx.scene.image.Image

data class ApplicationData(
    val pid:String,
    val descriptionName:String?,
    val iconImage: Image?
) {
}