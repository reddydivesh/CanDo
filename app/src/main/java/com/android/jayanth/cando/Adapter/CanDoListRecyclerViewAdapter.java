package com.android.jayanth.cando.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.jayanth.cando.Database.CandoDbHelper;
import com.android.jayanth.cando.Fragment.CanDoDetailsFragment;
import com.android.jayanth.cando.R;
import com.android.jayanth.cando.model.CanDoDataModel;

import java.util.ArrayList;

import static com.android.jayanth.cando.Constants.Constants.PRIORITY_HIGH;
import static com.android.jayanth.cando.Constants.Constants.PRIORITY_LOW;
import static com.android.jayanth.cando.Constants.Constants.PRIORITY_MID;

/**
 * Created by Jayanth Devulapally on 8/3/17.
 *Custom recycler view adapter.
 *This class will configure the rows of the list view.
 */
public class CanDoListRecyclerViewAdapter extends RecyclerView.Adapter<CanDoListRecyclerViewAdapter.CanDoListRowViewHolder> {

    //List of the model
    private ArrayList<CanDoDataModel> mCanDoData;

    private Context mContext;
    private FragmentManager mFragmentManager;

    public CanDoListRecyclerViewAdapter(Context context, int status, FragmentManager fragmentManager){

        mContext = context;
        mFragmentManager = fragmentManager;
        getUpdatedData(status);
    }

    //getting the data from the SQLiteHelper class as model list
    private void getUpdatedData(int status) {
        CandoDbHelper mCandoDbHelper = new CandoDbHelper(mContext);
        mCanDoData = mCandoDbHelper.getCanDoList(status);
    }

    @Override
    public CanDoListRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //setting the layout of the row in the list view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.can_do_list_row,parent,false);
        return new CanDoListRowViewHolder(view);
    }

    //returning the number of rows in the list view
    @Override
    public int getItemCount() {
        return mCanDoData.size();
    }

    //Binding the model data to the layout holder
    @Override
    public void onBindViewHolder(CanDoListRowViewHolder holder, final int position) {

        if (mCanDoData.size()>0) {

            final CanDoDataModel data = mCanDoData.get(holder.getAdapterPosition());
            holder.canDotitle.setText(data.getTitle());
            holder.canDoPriority.setText(getPriorityString(data.getPriority()));
            holder.canDoDueDate.setText(data.getDate());

            //displaying the detail fragment on row click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initilazing the detail fragment with model
                    CanDoDetailsFragment detailFragment = CanDoDetailsFragment.newInstance(data);

                    //setting the action item click listener
                    detailFragment.setOnActionItemClickListener(new CanDoDetailsFragment.OnActionItemClickListener() {
                        @Override
                        public void onActionItemClick() {
                            getUpdatedData(data.getStatus());
                            notifyDataSetChanged();
                        }
                    });

                    //showing the fragment
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.add(android.R.id.content, detailFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    //helper method to get the priority text
    private String getPriorityString(int priority){

        switch (priority){
            case PRIORITY_LOW:
                return "Low";
            case PRIORITY_MID:
                return "Medium";
            case PRIORITY_HIGH:
                return "High";
            default:
                return "High";
        }
    }

    //Holder class to configure the view from the layout assigneds
    public static class CanDoListRowViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView canDotitle;
        TextView canDoDueDate;
        TextView canDoPriority;
        public CanDoListRowViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            canDotitle = (TextView) itemView.findViewById(R.id.tv_can_do_title);
            canDoDueDate = (TextView) itemView.findViewById(R.id.tv_due_date);
            canDoPriority = (TextView) itemView.findViewById(R.id.tv_can_do_priority);
        }
    }
}
