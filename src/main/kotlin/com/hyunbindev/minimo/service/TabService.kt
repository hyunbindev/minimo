package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.model.tab.TabTable
import com.hyunbindev.minimo.repository.TabRepository
import org.slf4j.LoggerFactory

object TabService {
    private val log = LoggerFactory.getLogger(TabService::class.java)
    fun createNewTab(idx:Int): TabData?{
        return try {
            TabRepository.createNewTab(idx)
        } catch (e: Exception) {
            log.error(e.message, e)
            null
        }
    }
}