package com.hyunbindev.minimo.core

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.clipboard.ClipType
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import com.sun.jna.Memory
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference
import javafx.scene.input.Clipboard
import org.slf4j.LoggerFactory
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
            cb.hasString() -> {ClipBoardData.getDataFactory(cb.string,ClipType.STRING)}
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
        val buffer = CharArray(1024)

        val processHandle = Kernel32.INSTANCE.OpenProcess(
            WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ, false, pid.toInt()
        )

        if (processHandle != null) {
            val buffer = CharArray(1024)
            Psapi.INSTANCE.GetModuleFileNameExW(processHandle, null, buffer, buffer.size)
            val len = Psapi.INSTANCE.GetModuleFileNameExW(processHandle, null, buffer, buffer.size)

            if(len <= 0) return

            val fullPath = String(buffer,0,len).trim { it <= ' ' }
            val file = File(fullPath)

            val appName = FileSystemView.getFileSystemView().getSystemDisplayName(file);

            val appDescriptionName = getAppDescription(fullPath)

            log.info("app description name : {}",appDescriptionName)
        }

        Kernel32.INSTANCE.CloseHandle(processHandle)
    }

    fun getAppDescription(filePath: String): String {
        val dwLen = Version.INSTANCE.GetFileVersionInfoSize(filePath, null)
        if (dwLen <= 0) return "정보 없음 (Size 0)"

        val lpData = Memory(dwLen.toLong())
        if (!Version.INSTANCE.GetFileVersionInfo(filePath, 0, dwLen, lpData)) {
            return "정보 읽기 실패"
        }

        val lpBuffer = PointerByReference()
        val dwBytes = IntByReference()

        // 1. 먼저 파일의 언어/코드페이지 정보를 가져옵니다.
        if (Version.INSTANCE.VerQueryValue(lpData, "\\VarFileInfo\\Translation", lpBuffer, dwBytes)) {
            val translation = lpBuffer.value.getByteArray(0, 4)
            // 041204b0 형태로 변환 (Little Endian 주의)
            val langCode = String.format("%02x%02x%02x%02x",
                translation[1], translation[0], translation[3], translation[2])

            val subBlock = "\\StringFileInfo\\$langCode\\FileDescription"

            if (Version.INSTANCE.VerQueryValue(lpData, subBlock, lpBuffer, dwBytes)) {
                return lpBuffer.value.getWideString(0)
            }
        }

        // 2. 실패 시 기본 한국어/영어로 재시도 (Fallback)
        val fallbacks = listOf("\\StringFileInfo\\041204b0\\FileDescription", "\\StringFileInfo\\040904b0\\FileDescription")
        for (path in fallbacks) {
            if (Version.INSTANCE.VerQueryValue(lpData, path, lpBuffer, dwBytes)) {
                return lpBuffer.value.getWideString(0)
            }
        }

        return "설명 없음"
    }
}