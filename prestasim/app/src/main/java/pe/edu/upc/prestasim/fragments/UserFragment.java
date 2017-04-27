package pe.edu.upc.prestasim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;

public class UserFragment extends Fragment {

    private View mView;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MenuActivity) getActivity()).setActionBarTitle("Nueva Solicitud");
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        return mView;
    }

}
