package com.cysion.wedialog.listener

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import java.io.Serializable

interface ViewHandler : Serializable {
    fun handle(dialogFragment: DialogFragment, dialogView: View, bundle: Bundle)
}

typealias EventHander<T> = (obj: T, flag: Int) -> Unit

abstract class BaseViewHandler<T>(val eventHander: EventHander<T>) : ViewHandler

interface YesHandler : Serializable {
    fun onConfirm(dialogFragment: DialogFragment)
}
interface NoHandler : Serializable {
    fun onCancel(dialogFragment: DialogFragment)
}

typealias OnViewHandler = (df: DialogFragment, dialogView: View, bundle: Bundle)  -> Unit

typealias OnYesHandler = (df: DialogFragment)  -> Unit
typealias OnNoHandler = (df: DialogFragment)  -> Unit

data class EventHolder(var handler: OnYesHandler?,var noHandler: OnNoHandler?) :Serializable