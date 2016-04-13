package com.wym.demo.recyclerview.model.impl;

import com.wym.demo.recyclerview.model.IMainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 Model 中发送请求获取数据
 */
public class MainModel implements IMainModel
{
    public List<String> mData;

    @Override
    public List<String> initData(int count)
    {
        mData = new ArrayList<>(count);

        for(int i = 0; i < count; )
        {
            mData.add(String.valueOf(++i));
        }

        return mData;
    }
}
