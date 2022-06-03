package com.example.mvvmdemo.refresh;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.list.InfoListAdapter;
import com.example.mvvmdemo.list.ItemModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DemoObjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public DemoObjectFragment() {
        // Required empty public constructor
    }
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_demo_object, container, false);
        SmartRefreshLayout sm = (SmartRefreshLayout)view.findViewById(R.id.refresh);
        InfoListAdapter adapter =  new InfoListAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ArrayList arrayList  = new ArrayList();
        for(int i =0;i<20;i++){
            ItemModel model1 = new ItemModel(i+"_", "cat");
            arrayList.add(model1);
        }
        adapter.submitList(arrayList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
    }
}