package pe.edu.upc.prestasim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;

public class HistoryFragment extends Fragment {

    private View mView;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MenuActivity) getActivity()).setActionBarTitle("Mi Informacion");
        mView = inflater.inflate(R.layout.fragment_history, container, false);
        return mView;
    }

}
