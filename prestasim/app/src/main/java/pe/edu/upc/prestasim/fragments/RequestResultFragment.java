package pe.edu.upc.prestasim.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;
import pe.edu.upc.prestasim.adapters.RequestResultAdapter;
import pe.edu.upc.prestasim.models.LoanType;
import pe.edu.upc.prestasim.models.Request;
import pe.edu.upc.prestasim.models.RequestTax;
import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.services.RequestService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestResultFragment extends Fragment {

    private View mView;
    private Integer id_request;
    private MasterService masterService;
    private RequestService requestService;
    private List<RequestTax> requestTaxList;
    private RecyclerView resultsRecyclerView;
    private RequestResultAdapter resultAdapter;
    private RecyclerView.LayoutManager resultsLayoutManager;
    private TextView requestTypeTV, requestAmountTV, requestInstallmentsTV;


    public static RequestResultFragment newInstance() {return new RequestResultFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MenuActivity) getActivity()).setActionBarTitle(getString(R.string.new_request));
        id_request = getArguments().getInt("id_request");
        mView = inflater.inflate(R.layout.fragment_request_result, container, false);

        // Initialize
        requestService = ((PrestasimApplication) getActivity().getApplication()).getRequestService();
        masterService = ((PrestasimApplication) getActivity().getApplication()).getMasterService();
        requestTypeTV = (TextView) mView.findViewById(R.id.requestTypeTV);
        requestAmountTV = (TextView) mView.findViewById(R.id.requestAmountTV);
        requestInstallmentsTV = (TextView) mView.findViewById(R.id.requestInstallmentsTV);
        resultsRecyclerView = (RecyclerView) mView.findViewById(R.id.resultsRecyclerView);
        resultAdapter = new RequestResultAdapter();
        resultsLayoutManager = new LinearLayoutManager(mView.getContext());

        // Values
        Request request = requestService.obtainRequestById(id_request);
        LoanType loanType = masterService.obtainLoanTypeById(request.getIdLoanType());
        requestTypeTV.setText(loanType.getName());
        requestAmountTV.setText(String.format("%.2f", request.getAmount()));
        requestInstallmentsTV.setText(request.getInstallments() + " meses");
        requestTaxList = requestService.obtainResultsByRequestId(id_request);
        resultAdapter.setRequestTaxes(requestTaxList);
        resultsRecyclerView.setLayoutManager(resultsLayoutManager);
        resultsRecyclerView.setAdapter(resultAdapter);
        return mView;
    }

}
