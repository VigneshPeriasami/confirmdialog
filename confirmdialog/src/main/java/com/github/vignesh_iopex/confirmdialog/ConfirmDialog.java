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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConfirmDialog {
  private static final String TAG = ConfirmDialog.class.getSimpleName();

  private Activity activity;
  private View.OnClickListener positiveListener;
  private View.OnClickListener negativeListener;
  private View contentView;

  private ConfirmDialog(Activity activity) {
    this.activity = activity;
  }

  private static ConfirmDialog build(Builder builder) {
    ConfirmDialog dialog = new ConfirmDialog(builder.activity);
    dialog.positiveListener = builder.positiveListener;
    dialog.negativeListener = builder.negativeListener;
    dialog.contentView = builder.contentView;
    return dialog;
  }

  public void show() {
    LayoutInflater inflater = activity.getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.confirm_dialog, null);

    ViewGroup dialogContent = (ViewGroup) dialogView.findViewById(R.id.alert_content);
    dialogContent.removeAllViews();
    dialogContent.addView(contentView);

    ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
    parent.addView(dialogView);
  }

  public static class Builder {
    private View contentView;
    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;
    private Activity activity;

    public Builder(Activity activity) {
      this.activity = activity;
    }

    public Builder setContentView(View view) {
      this.contentView = view;
      return this;
    }

    public Builder setPositiveListener(View.OnClickListener listener) {
      this.positiveListener = listener;
      return  this;
    }

    public Builder setNegativeListener(View.OnClickListener listener) {
      this.negativeListener = listener;
      return this;
    }

    public ConfirmDialog build() {
      return ConfirmDialog.build(this);
    }
  }

}
