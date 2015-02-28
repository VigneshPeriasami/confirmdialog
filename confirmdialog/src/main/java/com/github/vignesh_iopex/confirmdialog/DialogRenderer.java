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

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

abstract class DialogRenderer {
  public static final String TAG = DialogRenderer.class.getSimpleName();
  View overlay;
  private ViewGroup parent;
  protected ViewBinder viewBinder;
  protected View overlayTagHolder;

  public DialogRenderer(View overlay, View overlayTagHolder, ViewGroup parent, ViewBinder viewBinder) {
    this.overlay = overlay;
    this.overlayTagHolder = overlayTagHolder;
    this.parent = parent;
    this.viewBinder = viewBinder;
  }

  protected Bundle getBundleArgs() {
    Bundle args = new Bundle();
    args.putInt(Confirm.CONFIRM_TAG_HOLDER, overlayTagHolder.getId());
    return args;
  }

  public void render(int contentId) {
    overlay.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismissDialog();
      }
    });
    overlayTagHolder.setTag(this);
    parent.addView(overlay);
  }

  public void bindView(View layout) {
    viewBinder.bindView(layout);
  }

  public int getLayoutId() {
    return viewBinder.getLayoutId();
  }

  public void dismissDialog() {
    // viewBinder.dismissView();
    overlay.setTag(null);
    parent.removeView(overlay);
  }
}
