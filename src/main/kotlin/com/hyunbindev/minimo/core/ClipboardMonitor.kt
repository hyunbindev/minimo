package com.hyunbindev.minimo.core

import com.sun.jna.Native
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.win32.W32APIOptions
import javafx.application.Platform
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

object ClipboardMonitor {
    private const val WM_CLIPBOARDUPDATE = 0x031D
    private var running = true

    private val log = LoggerFactory.getLogger(ClipboardMonitor::class.java)

    // 1. User32 인터페이스 확장 (없는 함수 추가)
    interface ExtendedUser32 : User32 {
        companion object {
            val INSTANCE = Native.load("user32", ExtendedUser32::class.java, W32APIOptions.DEFAULT_OPTIONS) as ExtendedUser32
        }
        fun AddClipboardFormatListener(hwnd: WinDef.HWND): Boolean
        fun RemoveClipboardFormatListener(hwnd: WinDef.HWND): Boolean
    }

    fun start(onUpdate: () -> Unit) {
        thread(isDaemon = true) {
            val user32 = ExtendedUser32.INSTANCE

            // 2. 메시지 수신용 보이지 않는 창 생성
            val hwnd = user32.CreateWindowEx(
                0, "Static", "ClipWatcher", 0, 0, 0, 0, 0,
                null, null, null, null
            )

            if (user32.AddClipboardFormatListener(hwnd)) {
                log.info("Core: Clipboard Listener Registered via Win32 API.")
            }

            val msg = WinUser.MSG()
            while (running && user32.GetMessage(msg, hwnd, 0, 0) != 0) {
                if (msg.message == WM_CLIPBOARDUPDATE) {
                    Platform.runLater { onUpdate() }
                }
                user32.TranslateMessage(msg)
                user32.DispatchMessage(msg)
            }

            user32.RemoveClipboardFormatListener(hwnd)
        }
    }
}