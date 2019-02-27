package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class CargoShipCloseupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cargoship_closeup, container, false);

        view.findViewById(R.id.cargo_ship_closeup_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AutonomousFragment)getParentFragment()).switchView(THUMBNAIL);
            }
        });

        return view;
    }
}
