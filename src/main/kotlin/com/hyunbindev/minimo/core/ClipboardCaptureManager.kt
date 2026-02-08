package com.hyunbindev.minimo.core

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.clipboard.ClipType
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import org.slf4j.LoggerFactory
import java.security.MessageDigest

object ClipboardCaptureManager {
    private val log = LoggerFactory.getLogger(ClipboardCaptureManager::class.java)
    private var lastContent: ClipBoardData? = null

    fun initialize() {
        ClipboardMonitor.start{
            handleClipboardUpdate()
        }
    }

    private fun handleClipboardUpdate(){
        val cb: Clipboard = Clipboard.getSystemClipboard()
        val clipBoardData: ClipBoardData? = when{
            cb.hasString() -> {ClipBoardData.getDataFactory(cb.string,ClipType.STRING)}
            else -> {null}
        }

        if(lastContent == clipBoardData || clipBoardData == null) return


        MemoViewModel.createMemoByClip(clipBoardData)
    }
}