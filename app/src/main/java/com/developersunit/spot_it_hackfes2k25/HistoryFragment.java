package com.developersunit.spot_it_hackfes2k25;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private RecyclerView recyclerView;
    private SearchHistoryAdapter adapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        TextView emptyTextView = view.findViewById(R.id.empty_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Load Dummy Data
        List<SearchHistory> searchHistory = getDummySearchHistory();

        if (searchHistory.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new SearchHistoryAdapter(searchHistory, this::showSearchResults);
            recyclerView.setAdapter(adapter);
        }
    }

    private List<SearchHistory> getDummySearchHistory() {
        List<SearchHistory> searchHistoryList = new ArrayList<>();

        searchHistoryList.add(new SearchHistory(1, "Material SearchBar", "2025-02-18"));
        searchHistoryList.add(new SearchHistory(2, "Floating Dialog Example", "2025-02-18"));
        searchHistoryList.add(new SearchHistory(3, "RecyclerView Adapter", "2025-02-17"));
        searchHistoryList.add(new SearchHistory(4, "Transparent Dialog Android", "2025-02-16"));

        return searchHistoryList;
    }

    private void showSearchResults(String query) {
        SearchResultDialog dialog = new SearchResultDialog(query);
        dialog.show(getParentFragmentManager(), "search_results");
    }
}