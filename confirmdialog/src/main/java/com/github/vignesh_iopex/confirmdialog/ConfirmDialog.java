/*
 * Copyright 2014 Vignesh Periasami
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Confirm dialog can be used on events that requires User confirmation.
 */
public final class ConfirmDialog extends DialogFragment implements DialogEventListener {
  public final static int POSITIVE_BUTTON = 1;
  public final static int NEGATIVE_BUTTON = -1;

  private OnDismissListener onDialogDismissListener;
  private View contentView;
  private String contentText;

  // Views on confirm dialog.
  private ViewGroup dialogHolderView;
  private ViewGroup dialogContent;
  private ViewGroup dialogOverlay;
  private TextView contentTextView;
  private Button btnConfirm;
  private Button btnCancel;
  private String confirmBtnText;
  private String cancelBtnText;

  private OnClickListener onConfirmClickListener;
  private OnClickListener onCancelClickListener;

  private static ConfirmDialog build(Builder builder) {
    ConfirmDialog dialog = new ConfirmDialog();
    dialog.activity = builder.activity;
    dialog.contentView = builder.contentView;
    dialog.contentText = builder.contentText;
    dialog.onDialogDismissListener = builder.onDismissListener;
    dialog.onConfirmClickListener = builder.confirmBtnClickListener;
    dialog.onCancelClickListener = builder.cancelBtnClickListener;
    dialog.confirmBtnText = builder.confirmBtnText;
    dialog.cancelBtnText = builder.cancelBtnText;
    return dialog;
  }

  protected void injectViews(View view) {
    this.dialogContent = (ViewGroup) view.findViewById(R.id.alert_content);
    this.contentTextView = (TextView) view.findViewById(R.id.confirm_text);
    this.btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
    this.btnCancel = (Button) view.findViewById(R.id.btn_cancel);
    this.dialogContent.setOnClickListener(null);
  }

  @Override
  public void onDestroyView() {
    ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
    parent.removeView(dialogHolderView);
    super.onDestroyView();
  }

  protected void injectListeners() {
    // set button listeners.
    this.btnConfirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onConfirmClickListener != null)
          onConfirmClickListener.onClick(ConfirmDialog.this, POSITIVE_BUTTON);
        dismissDialogContent();
      }
    });

    this.dialogOverlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onDialogDismissListener != null)
          onDialogDismissListener.onDismiss(ConfirmDialog.this);
        dismissDialogContent();
      }
    });

    this.btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onCancelClickListener != null)
          onCancelClickListener.onClick(ConfirmDialog.this, NEGATIVE_BUTTON);
        dismissDialogContent();
      }
    });
  }

  @Override
  protected void constructViewElements() {
    if (contentView != null) {
      dialogContent.removeAllViews();
      dialogContent.addView(contentView);
    } else if (!TextUtils.isEmpty(contentText)) {
      contentTextView.setText(contentText);
    }

    // populate views
    if (!TextUtils.isEmpty(confirmBtnText)) {
      btnConfirm.setText(confirmBtnText);
    } else {
      btnConfirm.setVisibility(View.GONE);
    }
    if (!TextUtils.isEmpty(cancelBtnText)) {
      btnCancel.setText(cancelBtnText);
    } else {
      btnCancel.setVisibility(View.GONE);
    }
  }

  @Override
  public void show() {
    LayoutInflater inflater = activity.getLayoutInflater();
    dialogHolderView = (ViewGroup) inflater.inflate(R.layout.confirm_overlay, null);
    this.dialogOverlay = (ViewGroup) dialogHolderView.findViewById(R.id.confirm_overlay);
    this.dialogContentContainer = dialogHolderView.findViewById(R.id.dialog_content_holder);
    ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
    parent.addView(dialogHolderView);

    renderDialogContent(R.id.dialog_content_holder);
  }

  @Override
  protected int getDialogViewId() {
    return R.layout.dialog_fragment;
  }

  /**
   * Builder that helps to construct the ConfirmDialog window.
   */
  public static class Builder {
    private View contentView;
    private OnDismissListener onDismissListener;
    private Activity activity;
    private String contentText;
    private String confirmBtnText;
    private OnClickListener confirmBtnClickListener;
    private String cancelBtnText;
    private OnClickListener cancelBtnClickListener;

    public Builder(Activity activity) {
      this.activity = activity;
    }

    /**
     * To set the custom view as the main content of the ConfirmDialog.
     * @param view will be used as the main content of the dialog.
     * @return Builder instance for chaining
     */
    public Builder setContentView(View view) {
      this.contentView = view;
      return this;
    }

    /**
     * To use the content text as the confirm dialog content.
     * @param contentText string will be used as the content of the dialog.
     * @return Builder instance for chaining
     */
    public Builder setContextText(String contentText) {
      this.contentText = contentText;
      return this;
    }

    /**
     * To listen to the ConfirmDialog dismiss/cancel event.
     * @param onDismissListener will be invoked on ConfirmDialog dismiss event.
     * @return Builder instance for chaining
     */
    public Builder setOnDismissListener(OnDismissListener onDismissListener) {
      this.onDismissListener = onDismissListener;
      return this;
    }

    /**
     * To set the confirm button name and event listener for the ConfirmDialog.
     * @param btnText name of the confirm button.
     * @param onConfirmListener to be invoked on confirm click.
     * @return Builder instance for chaining.
     */
    public Builder setConfirmButton(String btnText, OnClickListener onConfirmListener) {
      this.confirmBtnText = btnText;
      this.confirmBtnClickListener = onConfirmListener;
      return this;
    }

    /**
     * To rename the default cancel button of the ConfirmDialog.
     * @param btnText name of the cancel button.
     * @param onCancelListener to be invoked on negative click.
     * @return Builder instance for chaining.
     */
    public Builder setCancelButton(String btnText, OnClickListener onCancelListener) {
      this.cancelBtnText = btnText;
      this.cancelBtnClickListener = onCancelListener;
      return this;
    }

    /**
     * To create the ConfirmDialog instance using the current instance of Builder configuration.
     * @return the created ConfirmDialog.
     */
    public ConfirmDialog create() {
      return ConfirmDialog.build(this);
    }
  }

}
