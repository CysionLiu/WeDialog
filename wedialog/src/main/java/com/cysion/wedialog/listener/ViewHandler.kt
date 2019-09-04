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

