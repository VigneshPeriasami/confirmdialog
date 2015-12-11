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
import android.view.View;

import static com.github.vignesh_iopex.confirmdialog.QuestionViewFactory.DEFAULT;

public class Confirm implements Dialog {
  public static final int POSITIVE = 1;
  public static final int NEGATIVE = -1;
  private WindowDelegate confirmWindow;

  public Confirm(Activity activity, View questionView,
                 String positiveText, String negativeText,
                 OnClickListener lstnPositive, OnClickListener lstnNegative,
                 OnDismissListener dismissListener) {
    ConfirmView confirmView = new ConfirmView(activity, this, questionView, positiveText,
        negativeText, lstnPositive, lstnNegative);
    this.confirmWindow = new ConfirmWindow(activity, this, confirmView, dismissListener);
  }

  @Override public void show() {
    confirmWindow.showDialog();
  }

  @Override public boolean isShowing() {
    return confirmWindow.isShowing();
  }

  void cleanup() {
    confirmWindow = WindowDelegate.NONE;
  }

  public static Builder using(Activity activity) {
    return new Builder(activity);
  }

  @Override public void dismiss() {
    confirmWindow.dismissDialog();
  }

  public static class Builder {
    Activity activity;
    private View askView;
    private OnDismissListener onDismissListener;
    private String positiveText;
    private String negativeText;
    private OnClickListener onConfirm;
    private OnClickListener onCancel;

    private Builder(Activity activity) {
      this.activity = activity;
    }

    public Builder ask(String confirmPhrase) {
      this.askView = DEFAULT.getQuestionView(activity, confirmPhrase);
      return this;
    }

    public Builder askView(View askView) {
      this.askView = askView;
      return this;
    }

    public Builder onPositive(String btnText, OnClickListener onClickListener) {
      this.positiveText = btnText;
      onConfirm = onClickListener;
      return this;
    }

    public Builder onNegative(String btnText, OnClickListener onClickListener) {
      this.negativeText = btnText;
      onCancel = onClickListener;
      return this;
    }

    public Builder onDismiss(OnDismissListener onDismissListener) {
      this.onDismissListener = onDismissListener;
      return this;
    }

    private OnClickListener getNonNullListener(OnClickListener listener) {
      if (listener != null)
        return listener;
      return OnClickListener.NONE;
    }

    private OnDismissListener getNonNullListener(OnDismissListener listener) {
      if (listener != null)
        return listener;
      return OnDismissListener.NONE;
    }

    public Dialog build() {
      return new Confirm(activity, askView, positiveText, negativeText,
          getNonNullListener(onConfirm), getNonNullListener(onCancel),
          getNonNullListener(onDismissListener));
    }
  }
}
