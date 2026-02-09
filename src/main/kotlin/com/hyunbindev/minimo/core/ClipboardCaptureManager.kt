package com.hyunbindev.minimo.core

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.clipboard.ClipType
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
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

            val fullPath = String(buffer).trim { it <= ' ' }
            val file = File(fullPath)

            val appName = FileSystemView.getFileSystemView().getSystemDisplayName(file);

            println("앱 이름: " + appName)
        }

        Kernel32.INSTANCE.CloseHandle(processHandle)
    }
}