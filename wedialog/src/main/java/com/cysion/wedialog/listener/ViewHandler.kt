package com.cysion.wedialog.listener

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import java.io.Serializable

typealias OnViewHandler = (df: DialogFragment, dialogView: View, bundle: Bundle) -> Unit
typealias OnYesHandler = (df: DialogFragment) -> Unit
typealias OnNoHandler = (df: DialogFragment) -> Unit

data class ListenerHolder(
    var aYesHandler: OnYesHandler? = null,
    var aNoHandler: OnNoHandler? = null,
    var aViewHandler: OnViewHandler? = null
) : Serializable
