package saarland.cispa.trust.cispabay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import saarland.cispa.trust.cispabay.MainActivity;
import saarland.cispa.trust.cispabay.R;

public class BuyFragment extends Fragment {
    private static int itemId;

    public BuyFragment() {}


    public static BuyFragment newInstance(int id) {
        itemId = id;
        return new BuyFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        final Button confirmButton = view.findViewById(R.id.confirmBtn);
        View.OnClickListener onConfirmButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "The item is bought!", Toast.LENGTH_LONG).show();
                confirmButton.setEnabled(false);
            }
        };
        confirmButton.setOnClickListener(onConfirmButtonClickedListener);

        final Button cancelButton = view.findViewById(R.id.cancelBtn);
        View.OnClickListener onCancelButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        };
        cancelButton.setOnClickListener(onCancelButtonClickedListener);

        return view;
    }
}