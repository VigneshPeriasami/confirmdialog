package com.github.vignesh_iopex.confirmdialog;

import android.view.View;
import android.view.ViewGroup;

public abstract class DialogRenderer {
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
    parent.removeView(overlay);
  }
}
