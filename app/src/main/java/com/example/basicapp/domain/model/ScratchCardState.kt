package com.example.basicapp.domain.model

sealed class ScratchCardState {
    object Unscratched : ScratchCardState()
    data class Scratched(val code: String) : ScratchCardState()
    object Activated : ScratchCardState()
    data class Error(val message: String) : ScratchCardState()
}