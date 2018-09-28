package com.shaary.a10000hours.view;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.presenter.CreateHobbyFragmentPresenter;
import com.shaary.a10000hours.presenter.MainActivityViewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateHobbyFragment extends DialogFragment {

    interface OnOkButtonListener {
        void okClicked(String name);
    }

    OnOkButtonListener listener;

    @BindView(R.id.ok_button) Button okButton;
    @BindView(R.id.cancel_button) Button cancelButton;
    @BindView(R.id.hobby_edit_text) EditText hobbyText;

    public CreateHobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_hobby, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listener = (MainActivity) getActivity();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.okClicked(hobbyText.getText().toString());
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
