package com.hyunbindev.minimo.service

import javafx.scene.image.Image
import java.util.concurrent.ConcurrentHashMap

object ApplicationIconService {
    private val iconCache = ConcurrentHashMap<String, Image>()

    fun getIconImage(key:String):Image?{
        return this.iconCache[key]
    }

    fun addNewIconImage(key: String, image: Image){
        this.iconCache[key] = image
    }

}