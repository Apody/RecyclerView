package com.wym.demo.recyclerview.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wym.demo.recyclerview.R;
import com.wym.demo.recyclerview.activity.MainActivity;
import com.wym.demo.recyclerview.model.IMainModel;
import com.wym.demo.recyclerview.model.impl.MainModel;
import com.wym.demo.recyclerview.widget.RecyclerAdapter;

import java.util.Arrays;

public class MainPresenter
{
    MainActivity mActivity;
    IMainModel mModel;

    public MainPresenter(MainActivity activity)
    {
        mActivity = activity;
        mModel = new MainModel();
    }

    public RecyclerView.Adapter getAdapter()
    {
        RecyclerAdapter<String> adapter = new RecyclerAdapter<String>(mActivity, R.layout.item_recycler_view, mModel.initData(6))
        {
            @Override
            public void bind(RecyclerViewHolder holder, String item, int position)
            {
                TextView textView = holder.getView(R.id.textView);
                textView.setText(item);
            }
        };

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener<String>()
        {
            @Override
            public void onItemClick(String item, View itemView, int position)
            {
                Toast.makeText(mActivity, ++position + " clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(String item, View itemView, int position)
            {
                return true;
            }
        });

        adapter.addItems(Arrays.asList("7", "8"), 0);

        return adapter;
    }

    public RecyclerView.LayoutManager getLayoutManager()
    {
        return new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
    }
}
