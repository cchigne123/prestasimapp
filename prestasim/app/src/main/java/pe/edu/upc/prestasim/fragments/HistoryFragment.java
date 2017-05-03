package pe.edu.upc.prestasim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;
import pe.edu.upc.prestasim.adapters.RequestAdapter;
import pe.edu.upc.prestasim.adapters.RequestResultAdapter;
import pe.edu.upc.prestasim.models.PaymentRank;
import pe.edu.upc.prestasim.models.Request;
import pe.edu.upc.prestasim.models.RequestTax;
import pe.edu.upc.prestasim.models.User;
import pe.edu.upc.prestasim.network.BackendApi;
import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.services.RequestService;
import pe.edu.upc.prestasim.services.UserService;

public class HistoryFragment extends Fragment {

    private View mView;
    private MasterService masterService;
    private RequestService requestService;
    private UserService userService;
    private List<Request> requests;
    private RecyclerView requestsRecyclerView;
    private RequestAdapter requestAdapter;
    private RecyclerView.LayoutManager requestLayoutManager;
    private static String TAG = "Prestasim - History";

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_history, container, false);
        ((MenuActivity) getActivity()).setActionBarTitle(getString(R.string.history_request_desc));

        // Initialize
        userService = ((PrestasimApplication) getActivity().getApplication()).getUserService();
        requestService = ((PrestasimApplication) getActivity().getApplication()).getRequestService();
        masterService = ((PrestasimApplication) getActivity().getApplication()).getMasterService();
        requestsRecyclerView = (RecyclerView) mView.findViewById(R.id.requestsRecyclerView);
        requestAdapter = new RequestAdapter();
        requestLayoutManager = new LinearLayoutManager(mView.getContext());

        // Values
        User user = userService.obtainCurrentUser();
        requests = requestService.obtainRequestsByUserId(user.getIdUser());
        if(requests == null || requests.isEmpty()){
            AndroidNetworking.get(BackendApi.generateRequestsUrl(user.getIdUser()))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, "response: " + response);
                            try {
                                if(BackendApi.API_OK_CODE.equals(response.getString("coderesult"))){
                                    List<Request> requests = Request.build
                                            (response.getJSONArray("requests"));
                                    for (Request request : requests) {
                                        requestService.registerRequest(request);
                                    }
                                    fillRequests(requests);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "error: " + anError.toString());
                        }
                    });
        } else {
            initializeRecyclerView();
        }
        return mView;
    }

    private void fillRequests(List<Request> requests) {
        this.requests = requests;
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        requestAdapter.setRequests(requests);
        requestsRecyclerView.setLayoutManager(requestLayoutManager);
        requestsRecyclerView.setAdapter(requestAdapter);
    }

}
