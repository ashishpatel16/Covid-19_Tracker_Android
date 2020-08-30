package com.example.covid_19tracker2020.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;
import com.example.covid_19tracker2020.R;
import com.example.covid_19tracker2020.State_Info;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<StatewiseItem> list;
    Context context;

    public MyAdapter(Context ct, List<StatewiseItem> s) {
        context = ct;
        list = s;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if(!list.get(position).getState().equalsIgnoreCase("State Unassigned")) {
            holder.state.setText(list.get(position).getState());

            holder.rec.setText(list.get(position).getRecovered());
            holder.moreRec.setText("+" + list.get(position).getDeltarecovered());

            holder.act.setText(list.get(position).getActive());

            holder.dec.setText(list.get(position).getDeaths());
            holder.moreDec.setText("+" + list.get(position).getDeltadeaths());

            holder.conf.setText(list.get(position).getConfirmed());
            holder.moreConf.setText("+" + list.get(position).getDeltaconfirmed());
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent stateInfoActivity = new Intent(context, State_Info.class);
                stateInfoActivity.putExtra("State_List", (Serializable) list);
                stateInfoActivity.putExtra("State_Index",position);
                context.startActivity(stateInfoActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView conf, rec, dec, act, moreConf, moreRec, moreDec, state;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            conf = itemView.findViewById(R.id.confirmedTv);
            moreConf = itemView.findViewById(R.id.moreConfirmedTv);

            dec = itemView.findViewById(R.id.decreasedTv);
            moreDec = itemView.findViewById(R.id.moreDecreasedTv);

            act = itemView.findViewById(R.id.activeTv);

            rec = itemView.findViewById(R.id.recoveredTv);
            moreRec = itemView.findViewById(R.id.moreRecoveredTv);

            state = itemView.findViewById(R.id.stateName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
