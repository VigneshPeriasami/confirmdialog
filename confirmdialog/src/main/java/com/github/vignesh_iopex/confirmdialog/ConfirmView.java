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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.github.vignesh_iopex.confirmdialog.Confirm.NEGATIVE;
import static com.github.vignesh_iopex.confirmdialog.Confirm.POSITIVE;

@SuppressLint("ViewConstructor")
class ConfirmView extends RelativeLayout implements View.OnClickListener {
  private final static int CLR_TRANSPARENT = Color.parseColor("#00000000");
  private final Context context;
  private final Dialog dialog;
  private final View vwContent;
  private final String positive;
  private final String negative;
  private final Dialog.OnClickListener lstnPositive;
  private final Dialog.OnClickListener lstnNegative;
  private Button btnPositive;
  private Button btnNegative;

  public ConfirmView(Context context, Dialog dialog, View vwContent,
                     String positive, String negative, Dialog.OnClickListener lstnPositive,
                     Dialog.OnClickListener lstnNegative) {
    super(context);
    this.context = context;
    this.dialog = dialog;
    this.vwContent = vwContent;
    this.positive = positive;
    this.negative = negative;
    this.lstnPositive = lstnPositive;
    this.lstnNegative = lstnNegative;
    init();
  }

  @SuppressWarnings("deprecation")
  private void init() {
    RelativeLayout.LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    layoutParams.addRule(ALIGN_PARENT_BOTTOM);
    addView(getVwContent(), layoutParams);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(new ColorDrawable(CLR_TRANSPARENT));
    } else {
      setBackgroundDrawable(new ColorDrawable(CLR_TRANSPARENT));
    }
  }

  private View getVwContent() {
    View contentView = LayoutInflater.from(context).inflate(R.layout.view_confirm, this, false);
    ViewGroup cntViewGroup = (ViewGroup) contentView.findViewById(R.id.alert_content);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(WRAP_CONTENT,
        WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER;
    cntViewGroup.addView(vwContent, layoutParams);
    btnPositive = (Button) contentView.findViewById(R.id.btn_confirm);
    btnNegative = (Button) contentView.findViewById(R.id.btn_cancel);
    btnPositive.setText(positive);
    btnNegative.setText(negative);
    btnPositive.setOnClickListener(this);
    btnNegative.setOnClickListener(this);

    if (TextUtils.isEmpty(positive)) {
      btnPositive.setVisibility(View.GONE);
    }
    if (TextUtils.isEmpty(negative)) {
      btnNegative.setVisibility(View.GONE);
    }
    return contentView;
  }

  @Override public void onClick(View view) {
    if (view == btnPositive) {
      lstnPositive.onClick(dialog, POSITIVE);
    } else if (view == btnNegative) {
      lstnNegative.onClick(dialog, NEGATIVE);
    }
    dialog.dismissDialog();
  }
}
