package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import static com.github.vignesh_iopex.confirmdialog.NullRenderer.getNullRenderer;

public class Confirm implements DialogEventListener {
  public final static int POSITIVE_BUTTON = 1;
  public final static int NEGATIVE_BUTTON = -1;

  static final String CONFIRM_TAG_HOLDER = "confirm_tag_holder";

  private Activity activity;
  private String confirmPhrase;
  private View askView;
  private OnClickListener onConfirm;
  private OnClickListener onCancel;
  private OnDismissListener onDismissListener;
  DialogRenderer dialogRenderer;

  public Confirm(Activity activity, String confirmPhase, View askView, OnClickListener onConfirm,
                 OnClickListener onCancel, OnDismissListener onDismissListener) {
    this.activity = activity;
    this.confirmPhrase = confirmPhase;
    this.askView = askView;
    this.onDismissListener = onDismissListener;
    this.onConfirm = onConfirm;
    this.onCancel = onCancel;
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
          overlay, overlay.findViewById(R.id.overlay), parent, getViewBinder(R.layout.dialog_fragment));
    } else {
      return null;
    }
  }

  ViewBinder getViewBinder(int layoutId) {
    return new DefaultViewBinder(layoutId, confirmPhrase, askView, onConfirm, onCancel, this);
  }

  @Override public void dismiss() {
    dialogRenderer.dismissDialog();
    if (onDismissListener != null)
      onDismissListener.onDismiss(this);
  }

  /**
   * to be used to retrieve object from the view overlay tag.
   * @param activity
   * @return
   */
  static DialogRenderer getDialogRenderer(Activity activity, Bundle args, Object fragment) {
    int overlayTagId = args.getInt(CONFIRM_TAG_HOLDER, -1);
    View overlay = activity.findViewById(overlayTagId);
    if (overlay != null) {
      return (DialogRenderer) overlay.getTag();
    }
    Log.i("check", "overlay is null -- " + fragment.getClass().getSimpleName());
    return getNullRenderer(fragment);
  }

  public static class Builder {
    Activity activity;
    private String confirmPhrase;
    private View askView;
    private OnDismissListener onDismissListener;
    private List<OnClickListener> dialogActions = new LinkedList<>();
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

    public Builder onConfirmListener(OnClickListener onClickListener) {
      this.onConfirm = onClickListener;
      return this;
    }

    public Builder onCancelListener(OnClickListener onClickListener) {
      this.onCancel = onClickListener;
      return this;
    }

    public Builder onDismiss(OnDismissListener onDismissListener) {
      this.onDismissListener = onDismissListener;
      return this;
    }

    public Confirm build() {
      return new Confirm(activity, confirmPhrase, askView, onConfirm, onCancel, onDismissListener);
    }
  }
}
