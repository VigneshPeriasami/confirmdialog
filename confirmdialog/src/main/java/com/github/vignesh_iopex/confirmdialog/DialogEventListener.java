package com.github.vignesh_iopex.confirmdialog;

/**
 * Dialog events listener.
 */
public interface DialogEventListener {
  public interface OnClickListener {
    void onClick(DialogEventListener dialog, int which);
  }

  public interface OnDismissListener {
    void onDismiss(DialogEventListener dialog);
  }

  public interface OnDialogShowListener {
    void onShow(DialogEventListener dialog);
  }
}
