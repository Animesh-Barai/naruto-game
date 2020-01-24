package com.gutotech.narutogame.ui.playing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.Requirement;
import com.gutotech.narutogame.ui.adapter.RequirementsAdapter;

import java.util.List;

public class RequirementDialogFragment extends DialogFragment {
    private Context mContext;
    private List<Requirement> mRequirements;

    public RequirementDialogFragment(Context context, List<Requirement> requirements) {
        mContext = context;
        mRequirements = requirements;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View root = requireActivity().getLayoutInflater().inflate(R.layout.dialog_requirements, null);
        builder.setView(root);

        RecyclerView requirementsRecyclerView = root.findViewById(R.id.requirementsRecyclerView);
        requirementsRecyclerView.setHasFixedSize(true);
        requirementsRecyclerView.setAdapter(new RequirementsAdapter(getContext(), mRequirements));

        return builder.create();
    }
}
