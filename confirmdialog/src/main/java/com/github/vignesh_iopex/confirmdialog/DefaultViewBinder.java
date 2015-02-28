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

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.github.vignesh_iopex.confirmdialog.DialogEventListener.OnClickListener;

class DefaultViewBinder implements ViewBinder {
  private int layoutId;
  private String askPhrase;
  private View askView;
  private OnClickListener onConfirm;
  private OnClickListener onCancel;
  private DialogEventListener dialogEventListener;
  private String positiveText;
  private String negativeText;

  public DefaultViewBinder(int layoutId, String askPhrase, View askView, String positiveText, String negativeText,
                           OnClickListener onConfirm, OnClickListener onCancel,
                           DialogEventListener dialogEventListener) {
    this.layoutId = layoutId;
    this.askPhrase = askPhrase;
    this.askView = askView;
    this.onConfirm = onConfirm;
    this.onCancel = onCancel;
    this.dialogEventListener = dialogEventListener;
    this.positiveText = positiveText;
    this.negativeText = negativeText;
  }

  private ViewHolder getViewHolder(View contentView) {
    return new DefaultViewHolder(contentView);
  }

  @Override public void bindView(View view) {
    ViewHolder viewHolder = getViewHolder(view);
    viewHolder.construct();
  }

  @Override public int getLayoutId() {
    return layoutId;
  }

  @Override public void dismissView() {
    dialogEventListener.dismiss();
  }

  public class DefaultViewHolder implements ViewHolder {
    private ViewGroup dialogContent;
    private TextView tvAskPhrase;
    private Button btnOnConfirm;
    private Button btnOnCancel;

    public DefaultViewHolder(View view) {
      tvAskPhrase = (TextView) view.findViewById(R.id.confirm_text);
      btnOnConfirm = (Button) view.findViewById(R.id.btn_confirm);
      btnOnCancel = (Button) view.findViewById(R.id.btn_cancel);
      dialogContent = (ViewGroup) view.findViewById(R.id.alert_content);
      btnOnConfirm.setVisibility(View.GONE);
      btnOnCancel.setVisibility(View.GONE);
    }

    @Override public void construct() {
      if (onConfirm != null) {
        btnOnConfirm.setVisibility(View.VISIBLE);
        btnOnConfirm.setText(positiveText);
        btnOnConfirm.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            onConfirm.onClick(dialogEventListener, Confirm.POSITIVE_BUTTON);
            dialogEventListener.dismiss();
          }
        });
      }
      if (onCancel != null) {
        btnOnCancel.setVisibility(View.VISIBLE);
        btnOnCancel.setText(negativeText);
        btnOnCancel.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            onCancel.onClick(dialogEventListener, Confirm.NEGATIVE_BUTTON);
            dialogEventListener.dismiss();
          }
        });
      }

      if (askView != null) {
        dialogContent.removeAllViews();
        Log.i(Confirm.TAG, "Ask view is not null");
        if (askView.getParent() != null) {
          ((ViewGroup) askView.getParent()).removeView(askView);
        }
        dialogContent.addView(askView);
      } else {
        tvAskPhrase.setText(askPhrase);
      }
    }
  }
}
