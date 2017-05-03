package pe.edu.upc.prestasim.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.models.RequestTax;

/**
 * Created by Cesar on 03/05/2017.
 */

public class RequestResultAdapter extends RecyclerView.Adapter<RequestResultAdapter.ViewHolder> {

    private List<RequestTax> requestTaxes;

    public List<RequestTax> getRequestTaxes() {
        return requestTaxes;
    }

    public void setRequestTaxes(List<RequestTax> requestTaxes) {
        this.requestTaxes = requestTaxes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_request_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.entityNameTV.setText(requestTaxes.get(position).getEntityName());
        holder.installmentAmountTV.setText(String.format("%.2f",
                requestTaxes.get(position).getInstallmentAmount()));
    }

    @Override
    public int getItemCount() {
        return requestTaxes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView requestResultCV;
        TextView entityNameTV, installmentAmountTV;

        public ViewHolder(View itemView) {
            super(itemView);
            requestResultCV = (CardView) itemView.findViewById(R.id.requestResultCV);
            entityNameTV = (TextView) itemView.findViewById(R.id.entityNameTV);
            installmentAmountTV = (TextView) itemView.findViewById(R.id.installmentAmountTV);
        }
    }
}
