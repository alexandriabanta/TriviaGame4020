package com.alexandriabanta.triviagame4020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        recyclerView = findViewById(R.id.scoreboardRecyclerView);
        recyclerView.setHasFixedSize(true);


    }

    interface RecyclerViewClickListener {
        public void onClick(View view, int position);
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private TextView view;
        private RecyclerViewClickListener listener;

        public ScoreViewHolder(final TextView view, final ScoreAdapter listener) {
            super(view);
            this.view = view;
            this.listener = listener;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, getAdapterPosition());
                }
            });
        }
    }

    class ScoreAdapter extends RecyclerView.Adapter<ScoreViewHolder> implements RecyclerViewClickListener{

        @NonNull
        @Override
        public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //return null;
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.score_view, parent, false);

            ScoreViewHolder viewHolder = new ScoreViewHolder(textView, this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
            holder.view.setText(scores.get(position) + "\n" + scores.get(position));
        }

        @Override
        public int getItemCount() {
            if (scores != null) {
                return scores.size();
            } else { return 0; }
        }

        @Override
        public void onClick(View view, int position) {

            //City city = cities.get(getAdapterPosition()); nmj

            String brandNameString = "";

            /*
            if (scores.get(position).getDataType().equals("Branded")) { brandNameString = "by " + foods.get(position).getBrandOwner(); }

            AlertDialog.Builder builder = new AlertDialog.Builder(FoodItemsRecyclerView.this);
            builder.setMessage(Html.fromHtml("<html>" +
                            brandNameString +
                            "<p><b>Data Type: </b> " + foods.get(position).getDataType() + "</p>" +
                            "<p><b>ID: </b> " + foods.get(position).getId() + "</p>" +
                            "<p><b>Description: </b> " + foods.get(position).getDescription() + "</p>" +
                            "<html>"

            ));


            ID = foods.get(position).getId();

            Log.i("Position", "" + position);
            Log.i("ID", "" + foods.get(position).getId());

            builder.setTitle("Food Info").setPositiveButton("Get Nutrition Info", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int choice) {
                    Intent i = new Intent(getApplicationContext(), GetNutritionInfo.class);
                    getApplicationContext().startActivity(i);
                }
            });

            builder.setTitle("Food Info").setNegativeButton("Go Back", null);
            builder.create().show();

             */
        }
    }
}
