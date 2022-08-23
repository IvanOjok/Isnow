package com.mcash.isnow.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.mcash.isnow.databinding.LayoutProgressBarBinding

class ProgressUtils(val context: Context) {
    private var dialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        builder.setView(LayoutProgressBarBinding.inflate(LayoutInflater.from(context)).root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showDialog() {
        if (!dialog.isShowing) dialog.show()
    }

    fun hideDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }

    fun destroyDialog() = dialog.cancel()
}