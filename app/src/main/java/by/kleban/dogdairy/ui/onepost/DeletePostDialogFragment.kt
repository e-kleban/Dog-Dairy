package by.kleban.dogdairy.ui.onepost

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.kleban.dogdairy.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject


class DeletePostDialogFragment @Inject constructor() : DialogFragment() {

    var onClickButtonListener: OnClickButtonListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_dialog_title)
            .setPositiveButton((R.string.delete)) { _, _ ->
                onClickButtonListener?.onClickPositiveButton()
                dismiss()
            }
            .setNeutralButton(R.string.cancel, null)
            .create()
    }

    fun interface OnClickButtonListener {
        fun onClickPositiveButton()
    }

    companion object {
        const val TAG = "DeletePostDialogFragment"
    }
}