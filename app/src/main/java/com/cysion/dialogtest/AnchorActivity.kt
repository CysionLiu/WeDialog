package com.cysion.dialogtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cysion.wedialog.WeDialog
import kotlinx.android.synthetic.main.activity_anchor.*

class AnchorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anchor)
        vTvLeftTop.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.3f)
                .setCancelableOutSide(true)
                .anchor(vTvLeftTop)
                .show { df, dialogView, bundle -> }
        }
        vTvLeftBottom.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.3f)
                .setCancelableOutSide(true)
                .anchor(vTvLeftBottom)
                .show { df, dialogView, bundle -> }
        }
        vTvRightTop.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.3f)
                .anchor(vTvRightTop)
                .setCancelableOutSide(true)
                .show { df, dialogView, bundle -> }
        }
        vTvRightBottom.setOnClickListener {
            //test pausing or finishing
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.3f)
                .anchor(vTvRightBottom)
                .setCancelableOutSide(true)
                .show { df, dialogView, bundle -> }
        }

    }
}
