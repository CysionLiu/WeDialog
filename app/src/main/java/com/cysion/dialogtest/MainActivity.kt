package com.cysion.dialogtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cysion.wedialog.CustomViewHandler
import com.cysion.wedialog.DParams
import com.cysion.wedialog.WeDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vTvShowloading.setOnClickListener {
            WeDialog.loading(this)
        }
        vTvCloseloading.setOnClickListener {
            for (i in 1..5) {
                Thread {
                    Thread.sleep((1000 * i).toLong())
                    runOnUiThread {
                        WeDialog.loading(this)
                    }
                }.start()
                Thread {
                    Thread.sleep((1500 * i).toLong())
                    runOnUiThread {
                        WeDialog.dismiss()
                    }
                }.start()
            }
        }

        vTvShowCustom1.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.activity_main)
                .params(DParams().addParam("KEY", "VALUE"))
                .show(CustomViewHandler { dialogFragment, dialogView, params ->
                    Log.d("--","1")
                })
        }
    }
}
