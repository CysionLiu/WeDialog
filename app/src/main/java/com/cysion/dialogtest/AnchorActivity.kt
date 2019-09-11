package com.cysion.dialogtest

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cysion.wedialog.WeDialog
import kotlinx.android.synthetic.main.activity_anchor.*

class AnchorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anchor)
        vBtnShowAnchor.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.2f)
                .setDim(0f)
                .anchor(vTvLeftTop)
                .show { df, dialogView, bundle -> }

            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.2f)
                .setDim(0f)
                .anchor(vTvLeftBottom)
                .show { df, dialogView, bundle -> }

            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.2f)
                .setDim(0f)
                .anchor(vTvRightTop)
                .show { df, dialogView, bundle -> }

            Handler().postDelayed(Runnable {
                //test pausing or finishing
                WeDialog.custom(this)
                    .layout(R.layout.dialog_custom0)
                    .setWidthRatio(0.2f)
                    .setDim(0f)
                    .anchor(vTvRightBottom)
                    .show { df, dialogView, bundle -> }
            }, 1500)

        }
    }
}
