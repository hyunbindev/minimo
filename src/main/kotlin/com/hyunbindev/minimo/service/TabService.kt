package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.repository.TabRepository
import org.slf4j.LoggerFactory

object TabService {
    private val log = LoggerFactory.getLogger(TabService::class.java)

    fun createNewTab(idx:Int): TabData?{
        return try {
            TabRepository.createNewTab(idx)
        } catch (e: Exception) {
            log.error(e.message, e)
            throw RuntimeException(e)
        }
    }

    fun getAllTabs(): List<TabData>{
        return try {
            TabRepository.findAllTabs() // Repository에 구현되어 있다고 가정
        } catch (e: Exception) {
            log.error("탭 목록 로드 실패: ${e.message}", e)
            emptyList()
        }
    }

    fun deleteTab(tabId:Int){
        log.info("tabId: $tabId")
        try{
            TabRepository.deleteTab(tabId)
        }catch (e:Exception){
            log.error("탭 삭제 실패: ${e.message}",e)
        }
    }
}