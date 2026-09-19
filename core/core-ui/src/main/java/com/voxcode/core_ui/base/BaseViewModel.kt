package com.voxcode.core_ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State: Any> : ViewModel() {

    // initial destination set for View model
    private var _navGraphDestination: Int? = null
    val navGraphDestination: Int?
        get() = _navGraphDestination

    private val mutableState by lazy { MutableStateFlow(getInitialState()) }
    // state is exposed as instance of mutableState
    val state: StateFlow<State> by lazy { mutableState }

    abstract fun getInitialState(): State

    fun reduceState(action: (State) -> State) {
        mutableState.update {
            action(it)
        }
    }
}
