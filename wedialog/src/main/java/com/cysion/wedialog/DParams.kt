package com.cysion.wedialog

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

class DParams {
    val bundle: Bundle = Bundle()
    fun addParam(key: String, value: Serializable): DParams {
        bundle.putSerializable(key, value)
        return this
    }

    fun addParam(key: String, value: Parcelable): DParams {
        bundle.putParcelable(key, value)
        return this
    }
}
