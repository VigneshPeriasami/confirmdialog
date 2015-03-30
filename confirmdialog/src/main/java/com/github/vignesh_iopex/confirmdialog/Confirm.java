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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import static com.github.vignesh_iopex.confirmdialog.NullRenderer.getNullRenderer;

public class Confirm implements DialogEventListener {
  final static String TAG = Confirm.class.getSimpleName();
  public final static int POSITIVE_BUTTON = 1;
  public final static int NEGATIVE_BUTTON = -1;

  static final String CONFIRM_TAG_HOLDER = "confirm_tag_holder";
  static final int ANIMATION_TIMER = 300;

  private Activity activity;
  private String confirmPhrase;
  private View askView;
  private OnClickListener onConfirm;
  private OnClickListener onCancel;
  private OnDismissListener onDismissListener;
  private String positiveText;
  private String negativeText;
  private AnimationResources animationResources;

  DialogRenderer dialogRenderer;

  public Confirm(Activity activity, String confirmPhase, View askView, String positiveText,
                 String negativeText, OnClickListener onConfirm, OnClickListener onCancel,
                 OnDismissListener onDismissListener, AnimationResources animationResources) {
    this.activity = activity;
    this.confirmPhrase = confirmPhase;
    this.askView = askView;
    this.onDismissListener = onDismissListener;
    this.onConfirm = onConfirm;
    this.onCancel = onCancel;
    this.positiveText = positiveText;
    this.negativeText = negativeText;
    this.animationResources = animationResources;
  }

  public static Builder using(Activity activity) {
    return new Builder(activity);
  }

  public void show() {
    dialogRenderer = getDialogRenderer();
    dialogRenderer.render(R.id.dialog_content_holder);
  }

  DialogRenderer getDialogRenderer() {
    ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
    View overlay = activity.getLayoutInflater().inflate(R.layout.confirm_overlay, parent, false);

    if (activity instanceof ActionBarActivity) {
      FragmentManager fragmentManager = ((ActionBarActivity) activity).getSupportFragmentManager();
      return new SupportDialogRenderer(fragmentManager, new DgFragment(),
          overlay, overlay.findViewById(R.id.overlay), parent,
          getViewBinder(R.layout.dialog_fragment), animationResources);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      android.app.FragmentManager fragmentManager = activity.getFragmentManager();
      return new AppDialogRenderer(fragmentManager, new AppDgFragment(), overlay,
          overlay.findViewById(R.id.overlay), parent, getViewBinder(R.layout.dialog_fragment),
          animationResources);
    } else {
      throw new UnsupportedOperationException("Use ActionBarActivity for API below 11");
    }
  }

  ViewBinder getViewBinder(int layoutId) {
    return new DefaultViewBinder(layoutId, confirmPhrase, askView, positiveText, negativeText,
        onConfirm, onCancel, onDismissListener, this);
  }

  @Override public void dismiss() {
    dialogRenderer.dismissDialog();
  }

  /**
   * to be used to retrieve object from the view overlay tag.
   *
   * @param activity
   * @return
   */
  static DialogRenderer getDialogRenderer(Activity activity, Bundle args, Object fragment) {
    int overlayTagId = args.getInt(CONFIRM_TAG_HOLDER, -1);
    View overlay = activity.findViewById(overlayTagId);
    if (overlay != null) {
      return (DialogRenderer) overlay.getTag();
    }
    Log.i(TAG, "overlay is null -- " + fragment.getClass().getSimpleName());
    return getNullRenderer(fragment);
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
    private AnimationResources animationResources;

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

    public Builder useAnimation(int enter, int exit, int animDelay) {
      this.animationResources = new AnimationResources(enter, exit, animDelay);
      return this;
    }

    public Confirm build() {
      if (animationResources == null) {
        animationResources = new AnimationResources(R.anim.slide_from_bottom, R.anim.slide_to_bottom,
            ANIMATION_TIMER);
      }
      return new Confirm(activity, confirmPhrase, askView, positiveText, negativeText,
          onConfirm, onCancel, onDismissListener, animationResources);
    }
  }
}
