package com.wym.demo.recyclerview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.wym.demo.recyclerview.R;
import com.wym.demo.recyclerview.presenter.MainPresenter;

/**
 * 在 Activity 中，只进行 view 的相关操作，其他的操作都放到 Presenter 中。
 */
public class MainActivity extends FragmentActivity
{
    //region 定义控件
    private RecyclerView mRecyclerView;
    //endregion

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 初始化控件
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //endregion

        mPresenter = new MainPresenter(this);

        showList();
    }

    /** 加载列表 */
    void showList()
    {
        mRecyclerView.setAdapter(mPresenter.getAdapter());
        mRecyclerView.setLayoutManager(mPresenter.getLayoutManager());
    }
}
