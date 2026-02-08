package com.hyunbindev.minimo.component.memo

import com.hyunbindev.minimo.model.memo.MemoData
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.text.TextFlow


class MemoElement(val memoData: MemoData) : VBox() {
    @FXML
    private lateinit var content: TextFlow
    @FXML
    private lateinit var updatedAt: Label

    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/memo/MemoElement.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Any>()


        val textNode = Text(memoData.content).apply {
            // 부모인 TextFlow의 스타일에 맞추기 위해
            // 폰트나 색상을 명시적으로 지정하거나 CSS를 사용합니다.
            styleClass.add("memo-text")
        }

        this.content.children.add(textNode)
        this.updatedAt.text = memoData.updatedAt.toString()

        // 5줄 제한 설정 (예: 14px 폰트 기준 약 100px)
        // 실제 폰트 크기에 맞춰 조정하세요.
        this.content.maxHeight = 100.0
    }
}