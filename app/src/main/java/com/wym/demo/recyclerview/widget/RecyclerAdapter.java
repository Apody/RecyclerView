package com.wym.demo.recyclerview.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于快速构建 RecyclerView 的适配器，省去重复创建 Adapter 类的过程。
 * 业务逻辑在 bind 方法中实现。
 *
 * @param <I> RecyclerView 中的 item 的类型
 */
public abstract class RecyclerAdapter<I> extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>
{
    Context mContext;
    int mLayoutRes;
    List<I> mItemList;

    public RecyclerAdapter(@NonNull Context context, @LayoutRes int layoutRes)
    {
        this(context, layoutRes, new ArrayList<I>());
    }

    public RecyclerAdapter(@NonNull Context context, @LayoutRes int layoutRes, @NonNull List<I> data)
    {
        mContext = context;
        mLayoutRes = layoutRes;
        mItemList = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position)
    {
        final I item = mItemList.get(position);

        bind(holder, item, position);

        // 绑定 item 的点击事件
        if(mOnItemClickListener == null) holder.itemView.setOnClickListener(null);
        else
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(item, v, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    return mOnItemClickListener.onItemLongClick(item, v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mItemList.size();
    }

    /**
     * 实现这个方法以控制 item 的显示效果
     *
     * @param holder   ViewHolder
     * @param item     item 对象
     * @param position item 的位置
     */
    public abstract void bind(RecyclerViewHolder holder, I item, int position);

    //region 设置 item 的点击事件
    OnItemClickListener<I> mOnItemClickListener;

    public void setOnItemClickListener(@Nullable OnItemClickListener<I> listener)
    {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener<I>
    {
        void onItemClick(I item, View itemView, int position);

        boolean onItemLongClick(I item, View itemView, int position);
    }
    //endregion

    //region 对 mItemList 进行的操作
    //region 添加
    public void addItem(I item)
    {
        mItemList.add(mItemList.size(), item);
    }

    public void addItem(I item, int index)
    {
        mItemList.add(index, item);
        notifyItemInserted(index);
    }

    public void addItems(List<I> itemList)
    {
        addItems(itemList, mItemList.size());
    }

    public void addItems(List<I> itemList, int index)
    {
        mItemList.addAll(index, itemList);
        notifyItemRangeInserted(0, itemList.size());
    }
    //endregion

    //region 删除
    public void removeItem(int index)
    {
        mItemList.remove(index);
        notifyItemRemoved(index);
    }

    public void removeItem(I item)
    {
        int index = mItemList.indexOf(item);
        removeItem(index);
    }

    public void removeItems(List<I> items)
    {
        mItemList.removeAll(items);
        notifyItemRangeRemoved(0, items.size());
    }
    //endregion

    //region 重置
    public void reset(List<I> itemList)
    {
        mItemList = new ArrayList<>(itemList);
        notifyDataSetChanged();
    }
    //endregion
    //endregion

    /**
     * 使用该类可以省去重复创建 ViewHolder 类的过程。
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        SparseArray<View> views = new SparseArray<>();

        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
        }

        /**
         * 用控件的 id 来获取 RecyclerView 的 item 中的控件。
         *
         * @param viewId 控件的 id
         * @param <V>    控件的类型
         * @return viewId 对应的控件
         */
        @SuppressWarnings("unchecked")
        public <V extends View> V getView(int viewId)
        {
            View view = views.get(viewId);
            if(view == null)
            {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (V)view;
        }
    }
}
