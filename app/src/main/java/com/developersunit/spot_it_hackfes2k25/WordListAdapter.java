package com.developersunit.spot_it_hackfes2k25;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developersunit.spot_it_hackfes2k25.Module.WordModel;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class WordListAdapter extends BaseAdapter {
    private Activity context_1;
    private List<WordModel> wordModels;
    public WordListAdapter(Activity context,List<WordModel> wordModels){
        context_1 = context;
        this.wordModels = wordModels;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView lblMeaning1,lblEnglish,lblMeaning2;
        return null;
    }
}
