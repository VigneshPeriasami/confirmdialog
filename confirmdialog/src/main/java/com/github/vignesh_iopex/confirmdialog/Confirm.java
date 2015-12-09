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

import com.github.vignesh_iopex.confirmdialog.Dialog.OnClickListener;
import com.github.vignesh_iopex.confirmdialog.Dialog.OnDismissListener;

public class Confirm {
  private final Activity activity;
  private ConfirmWindow confirmWindow;

  public Confirm(Activity activity) {
    this.activity = activity;
  }

  public void show() {
    confirmWindow = new ConfirmWindow(activity);
    confirmWindow.showDialog();
  }

  public static Builder using(Activity activity) {
    return new Builder(activity);
  }

  public static class Builder {
    Activity activity;
    private String confirmPhrase;
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
      this.confirmPhrase = confirmPhrase;
      return this;
    }

    public Builder askView(View askView) {
      this.askView = askView;
      return this;
    }

    public Builder onPositive(String btnText, OnClickListener onClickListener) {
      this.positiveText = btnText;
      this.onConfirm = onClickListener;
      return this;
    }

    public Builder onNegative(String btnText, OnClickListener onClickListener) {
      this.negativeText = btnText;
      this.onCancel = onClickListener;
      return this;
    }

    public Builder onDismiss(OnDismissListener onDismissListener) {
      this.onDismissListener = onDismissListener;
      return this;
    }

    public Confirm build() {
      return new Confirm(activity);
    }
  }
}
