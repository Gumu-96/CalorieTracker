package com.gumu.core_ui.util

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
}
