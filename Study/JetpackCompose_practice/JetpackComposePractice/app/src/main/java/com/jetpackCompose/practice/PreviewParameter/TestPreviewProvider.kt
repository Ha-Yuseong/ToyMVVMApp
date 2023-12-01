package com.jetpackCompose.practice.PreviewParameter

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class TestPreviewProvider: PreviewParameterProvider<Test> {
    override val values = sequenceOf(
        Test("Hello"),
        Test("World"),
        Test("안녕"),
        Test("세상아!?"),
    )
}