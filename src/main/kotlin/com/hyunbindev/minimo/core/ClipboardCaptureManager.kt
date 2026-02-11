package com.hyunbindev.minimo.core

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.clipboard.ClipType
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import com.sun.jna.Memory
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.input.Clipboard
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.filechooser.FileSystemView


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
            cb.hasString() -> { ClipBoardData.getDataFactory(cb.string,ClipType.STRING)}
            else -> {null}
        }

        if(lastContent == clipBoardData || clipBoardData == null) return

        val focusedPid:Long = getFocusedProcessId()

        if(WindowManager.PID == focusedPid) return;

        getAppDetail(focusedPid)

        MemoViewModel.createMemoByClip(clipBoardData)
        lastContent=clipBoardData
    }

    fun getFocusedProcessId(): Long {
        val user32 = User32.INSTANCE

        val hwnd: WinDef.HWND = user32.GetForegroundWindow()

        val pidReference = IntByReference()

        user32.GetWindowThreadProcessId(hwnd, pidReference)

        return pidReference.value.toLong()
    }

    fun getAppDetail(pid:Long){
        val processHandle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ, false, pid.toInt())
        if (processHandle != null) {
            val buffer = CharArray(1024)

            val len = Psapi.INSTANCE.GetModuleFileNameExW(processHandle, null, buffer, buffer.size)

            if (len > 0) {
                val fullPath = String(buffer, 0, len)

                val appDescriptionName = getAppDescriptionName(fullPath)


                log.info("파일 경로: $fullPath")
                log.info("앱 설명: $appDescriptionName")
            } else {
                log.warn("경로를 가져오는 데 실패했습니다. PID: $pid")
            }
        }

        Kernel32.INSTANCE.CloseHandle(processHandle)
    }

    fun getAppDescriptionName(filePath: String):String{
        val dwLen = Version.INSTANCE.GetFileVersionInfoSize(filePath, null)
        if (dwLen <= 0) return "정보 없음"

        val lpData = Memory(dwLen.toLong())
        if (!Version.INSTANCE.GetFileVersionInfo(filePath, 0, dwLen, lpData)) return "정보 읽기 실패"
        val lpBuffer = PointerByReference()
        val dwBytes = IntByReference()

        if (Version.INSTANCE.VerQueryValue(lpData, "\\VarFileInfo\\Translation", lpBuffer, dwBytes)) {
            val p = lpBuffer.value
            val language = String.format("%04x", p.getShort(0))
            val codePage = String.format("%04x", p.getShort(2))

            val subBlock = "\\StringFileInfo\\$language$codePage\\FileDescription"
            if (Version.INSTANCE.VerQueryValue(lpData, subBlock, lpBuffer, dwBytes)) {
                return lpBuffer.value.getWideString(0)
            }
        }

        return "설명 없음"
    }

    fun getAppIcon(filePath: String): Image?{
        val file = File(filePath)
        if (!file.exists()) return null

        val icon = FileSystemView.getFileSystemView().getSystemIcon(file)

        val bufferedImage = BufferedImage(
            icon.iconWidth,
            icon.iconHeight,
            BufferedImage.TYPE_INT_ARGB
        )
        val g = bufferedImage.createGraphics()
        icon.paintIcon(null, g, 0, 0)
        g.dispose()

        return SwingFXUtils.toFXImage(bufferedImage, null)
    }
}