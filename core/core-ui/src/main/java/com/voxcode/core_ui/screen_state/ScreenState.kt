package com.voxcode.core_ui.screen_state

sealed interface ScreenState {
    object Loading : ScreenState
    object Success : ScreenState
    data class Failure(val message: String) : ScreenState
}
