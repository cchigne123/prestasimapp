package pe.edu.upc.prestasim.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;
import pe.edu.upc.prestasim.models.Request;

/**
 * Created by Cesar on 03/05/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<Request> requests;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_request, parent, false);
        RequestAdapter.ViewHolder viewHolder = new RequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.requestNumberTV.setText(requests.get(position).getIdRequest().toString());
        holder.requestDateTV.setText(requests.get(position).getRegisterDate());
        holder.requestTypeTV.setText(requests.get(position).getLoanTypeName());
        holder.requestInstallmentsTV.setText(requests.get(position).getInstallments().toString());
        holder.requestAmountTV.setText(String.format("%.2f", requests.get(position).getAmount()));
        holder.requestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity) v.getContext()).openResultFragment
                        (requests.get(position).getIdRequest());
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView requestCV;
        TextView requestNumberTV, requestDateTV, requestTypeTV;
        TextView requestInstallmentsTV, requestAmountTV;
        public ViewHolder(View itemView) {
            super(itemView);
            requestCV = (CardView) itemView.findViewById(R.id.requestCV);
            requestNumberTV = (TextView) itemView.findViewById(R.id.requestNumberTV);
            requestDateTV = (TextView) itemView.findViewById(R.id.requestDateTV);
            requestTypeTV = (TextView) itemView.findViewById(R.id.requestTypeTV);
            requestInstallmentsTV = (TextView) itemView.findViewById(R.id.requestInstallmentsTV);
            requestAmountTV = (TextView) itemView.findViewById(R.id.requestAmountTV);
        }
    }
}
