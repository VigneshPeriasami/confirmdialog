package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static com.github.vignesh_iopex.confirmdialog.Confirm.getDialogRenderer;

/**
 * Hack to enable the pop backstack animation.
 */
public class DummyFragment extends Fragment {
  private DialogRenderer dialogRenderer;

  @Override public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    ViewGroup viewGroup = new LinearLayout(inflater.getContext());
    viewGroup.addView(inflater.inflate(dialogRenderer.getLayoutId(), container, false));
    viewGroup.setVisibility(View.INVISIBLE);
    return viewGroup;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    dialogRenderer.bindView(view);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    dialogRenderer = getDialogRenderer(activity, getArguments(), this);
  }
}
