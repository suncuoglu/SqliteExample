package com.suncuoglu.sqlite.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.suncuoglu.sqlite.R;
import com.suncuoglu.sqlite.services.BottomSheetClick;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/*
12.01.2021 by Şansal Uncuoğlu
*/


public class EditBottomSheet extends BottomSheetDialogFragment {

    LinearLayout delete_btn;
    LinearLayout edit_btn;

    BottomSheetClick bottomSheetClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        setViews(view);
        setClick();

        return view;
    }

    void setViews(View view) {
        delete_btn = view.findViewById(R.id.delete_btn);
        edit_btn = view.findViewById(R.id.edit_btn);
    }

    void setClick() {
        delete_btn.setOnClickListener(MyListener);
        edit_btn.setOnClickListener(MyListener);
    }

    private View.OnClickListener MyListener = new View.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.delete_btn:

                    bottomSheetClick.delete();

                    break;

                case R.id.edit_btn:

                    bottomSheetClick.edit();

                    break;

                default:
                    break;
            }
        }
    };

    public void setOnFragmentAddListener(BottomSheetClick bottomSheetClick) {
        this.bottomSheetClick = bottomSheetClick;
    }

    public static EditBottomSheet newInstance(BottomSheetClick bottomSheetClick) {
        EditBottomSheet editBottomSheet = new EditBottomSheet();
        editBottomSheet.setOnFragmentAddListener(bottomSheetClick);
        return editBottomSheet;
    }
}
