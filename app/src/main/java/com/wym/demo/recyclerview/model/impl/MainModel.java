package com.wym.demo.recyclerview.model.impl;

import com.wym.demo.recyclerview.model.IMainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VenomWay
 * @date 16.3.2
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
