package by.kleban.dogdairy.ui.onepost

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.kleban.dogdairy.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject


class DeletePostDialogFragment @Inject constructor() : DialogFragment() {

    var onClickButtonListener: OnClickButtonListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Do you really want to delete post?")
            .setPositiveButton("Delete", null)
            .setNeutralButton(R.string.cancel, null)
            .create()
    }

    fun interface OnClickButtonListener {
        fun onClickPositiveButton()
    }

    override fun onStart() {
        super.onStart()

        val alert = dialog as AlertDialog
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            onClickButtonListener?.onClickPositiveButton()
            dismiss()
        }
    }

    companion object {
        const val TAG = "DeletePostDialogFragment"
    }
}