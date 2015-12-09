package com.github.vignesh_iopex.confirmdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

class ConfirmView extends RelativeLayout {
  private final static int CLR_TRANSPARENT = Color.parseColor("#00000000");
  private final Context context;
  private final View content;
  private Button btnPositive;
  private Button btnNegative;

  public ConfirmView(Context context, View content) {
    super(context);
    this.context = context;
    this.content = content;
    init();
  }

  @SuppressWarnings("deprecation")
  private void init() {
    RelativeLayout.LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    layoutParams.addRule(ALIGN_PARENT_BOTTOM);
    addView(getContent(), layoutParams);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(new ColorDrawable(CLR_TRANSPARENT));
    } else {
      setBackgroundDrawable(new ColorDrawable(CLR_TRANSPARENT));
    }
  }

  private View getContent() {
    View contentView = LayoutInflater.from(context).inflate(R.layout.view_confirm, this, false);
    ViewGroup cntViewGroup = (ViewGroup) contentView.findViewById(R.id.alert_content);
    cntViewGroup.removeAllViews();
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(WRAP_CONTENT,
        WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER;
    cntViewGroup.addView(content, layoutParams);
    btnPositive = (Button) contentView.findViewById(R.id.btn_confirm);
    btnNegative = (Button) contentView.findViewById(R.id.btn_cancel);
    return contentView;
  }

  public void onPositive(OnClickListener onClickListener) {
    btnPositive.setOnClickListener(onClickListener);
  }

  public void onNegative(OnClickListener onClickListener) {
    btnNegative.setOnClickListener(onClickListener);
  }
}
