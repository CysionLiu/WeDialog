package com.cysion.wedialog

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

class WeParams {
    val bundle: Bundle = Bundle()
    fun addParam(key: String, value: Serializable): WeParams {
        bundle.putSerializable(key, value)
        return this
    }

    fun addParam(key: String, value: Parcelable): WeParams {
        bundle.putParcelable(key, value)
        return this
    }
}
