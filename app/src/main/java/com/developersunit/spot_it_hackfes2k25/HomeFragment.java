package com.developersunit.spot_it_hackfes2k25;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private SearchView searchView;
    private TextView resultText;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.search_view);
        resultText = view.findViewById(R.id.resultText);

        // Set listener for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    new FetchWordDefinition().execute(query);
                }
                return true; // Consume event (prevents duplicate actions)
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false; // We only fetch data when the user submits
            }
        });

        return view;
    }

    private class FetchWordDefinition extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String word = params[0];
            String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject firstEntry = jsonArray.getJSONObject(0);
                    JSONArray meanings = firstEntry.getJSONArray("meanings");

                    StringBuilder definition = new StringBuilder();
                    for (int i = 0; i < meanings.length(); i++) {
                        JSONObject meaning = meanings.getJSONObject(i);
                        JSONArray definitions = meaning.getJSONArray("definitions");
                        definition.append("• ").append(definitions.getJSONObject(0).getString("definition")).append("\n\n");
                    }

                    resultText.setText(definition.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    resultText.setText("Error parsing data.");
                }
            } else {
                resultText.setText("Word not found.");
            }
        }
    }
}
