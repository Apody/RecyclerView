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

import java.util.List;

public abstract class RecyclerAdapter<I> extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>
{
    Context mContext;
    int mLayoutRes;
    List<I> mItemList;

    OnItemClickListener<I> mOnItemClickListener;

    public void setOnItemClickListener(@Nullable OnItemClickListener<I> listener)
    {
        mOnItemClickListener = listener;
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
        }
    }

    @Override
    public int getItemCount()
    {
        return mItemList.size();
    }

    public abstract void bind(RecyclerViewHolder holder, I item, int position);

    public interface OnItemClickListener<I>
    {
        void onItemClick(I item, View view, int position);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public RecyclerViewHolder(View convertView)
        {
            super(convertView);
        }

        @SuppressWarnings("unchecked")
        public <V extends View> V getView(int viewId)
        {
            SparseArray<View> viewHolder = (SparseArray<View>)itemView.getTag();
            if(viewHolder == null)
            {
                viewHolder = new SparseArray<>();
                itemView.setTag(viewHolder);
            }
            View view = viewHolder.get(viewId);
            if(view == null)
            {
                view = itemView.findViewById(viewId);
                viewHolder.put(viewId, view);
            }
            return (V)view;
        }
    }
}
