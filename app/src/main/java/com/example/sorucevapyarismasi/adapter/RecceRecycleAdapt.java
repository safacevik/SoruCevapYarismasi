package com.example.sorucevapyarismasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecceRecycleAdapt extends RecyclerView.Adapter<RecceRecycleAdapt.VH>
{
    private String TAG = getClass().getSimpleName();
    private ArrayList<?> arrayList;
    private Context context;
    private @LayoutRes
    int layout;
    private @Nullable
    int viewType;

    public static int LAYOUT1_CARDVIEW_1IMG_2TXT = 1;
    public static int LAYOUT2_SPINNER_1IMG_1TXT = 2;

    private void Call(){ this.BindListener = null; }
    public RecceRecycleAdapt() { Call(); }

    public RecceRecycleAdapt(ArrayList<?> arrayList, Context context, int viewType)
    {
        Call();
        this.arrayList = arrayList;
        this.context = context;
        this.viewType = viewType;
    }

    public RecceRecycleAdapt(ArrayList<?> arrayList, Context context, @LayoutRes int layout, int viewType)
    {
        Call();
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,null);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position)
    {
        if (BindListener != null)
        {
            BindListener.onBindView(holder, position);
        }
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder
    {
        public VH(@NonNull View itemView)
        {
            super(itemView);
        }
    }

    private RecceRecycleAdapt.OnBindListener BindListener;

    public interface OnBindListener
    {
        void onBindView(VH v, int position);
    }

    public void setOnBindViewListener(@Nullable RecceRecycleAdapt.OnBindListener listener)
    {
        BindListener = listener;
    }

    @Nullable
    public final RecceRecycleAdapt.OnBindListener getOnBindListener()
    {
        return BindListener;
    }
}
