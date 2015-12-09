package com.github.vignesh_iopex.confirmdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

class ConfirmView extends RelativeLayout {
  private final static int CLR_TRANSPARENT = Color.parseColor("#00000000");
  private final Context context;
  private Button btnPositive;
  private Button btnNegative;
  private TextView txtConfirm;

  public ConfirmView(Context context) {
    super(context);
    this.context = context;
    init();
  }

  @SuppressWarnings("deprecation")
  private void init() {
    RelativeLayout.LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    layoutParams.addRule(ALIGN_PARENT_BOTTOM);
    addView(getContentView(), layoutParams);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(new ColorDrawable(CLR_TRANSPARENT));
    } else {
      setBackgroundDrawable(new ColorDrawable(CLR_TRANSPARENT));
    }
  }

  private View getContentView() {
    View contentView = LayoutInflater.from(context).inflate(R.layout.view_confirm, this, false);
    btnPositive = (Button) contentView.findViewById(R.id.btn_confirm);
    btnNegative = (Button) contentView.findViewById(R.id.btn_cancel);
    txtConfirm = (TextView) contentView.findViewById(R.id.txt_confirm);
    return LayoutInflater.from(context).inflate(R.layout.view_confirm, this, false);
  }

  public void onPositive(OnClickListener onClickListener) {
    btnPositive.setOnClickListener(onClickListener);
  }

  public void setConfirmText(String confirmText) {
    txtConfirm.setText(confirmText);
  }

  public void onNegative(OnClickListener onClickListener) {
    btnNegative.setOnClickListener(onClickListener);
  }
}
