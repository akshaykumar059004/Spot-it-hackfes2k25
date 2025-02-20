package com.developersunit.spot_it_hackfes2k25;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private final List<SearchHistory> searchHistoryList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String query);
    }

    public SearchHistoryAdapter(List<SearchHistory> searchHistoryList, OnItemClickListener listener) {
        this.searchHistoryList = searchHistoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistory history = searchHistoryList.get(position);
        holder.queryTextView.setText(history.query);
        holder.dateTextView.setText(history.date);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(history.query));
    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView queryTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            queryTextView = itemView.findViewById(R.id.search_query);
            dateTextView = itemView.findViewById(R.id.search_date);
        }
    }
}
