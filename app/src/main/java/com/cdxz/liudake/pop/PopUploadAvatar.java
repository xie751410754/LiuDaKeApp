package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;

public class PopUploadAvatar extends BottomPopupView {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PIC = 2;
    TextView tvTakePhoto;
    TextView tvChoosePic;
    //    TextView tvCancel;
    private OnSelectListener onSelectListener;

    public PopUploadAvatar(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_upload_avatar;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tvTakePhoto = findViewById(R.id.tvTakePhoto);
        tvChoosePic = findViewById(R.id.tvChoosePic);
//        tvCancel = findViewById(R.id.tvCancel);

        tvTakePhoto.setOnClickListener(v -> {
            onSelectListener.onSelect(TAKE_PHOTO);
            dismiss();
        });
        tvChoosePic.setOnClickListener(v -> {
            onSelectListener.onSelect(CHOOSE_PIC);
            dismiss();
        });
//        tvCancel.setOnClickListener(v -> {
//            dismiss();
//        });
    }

    public interface OnSelectListener {
        void onSelect(int i);
    }
}
