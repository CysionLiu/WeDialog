package com.cysion.wedialog;

import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public interface CustomViewHandler extends Serializable {
    void handle(DialogFragment dialogFragment, View dialogView,DParams params);
}
