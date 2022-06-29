package com.example.sorucevapyarismasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class RecceBaseAdapt extends BaseAdapter
{
    private String TAG = getClass().getSimpleName();
    private ArrayList<?> arrayList;
    private Context context;
    private LayoutInflater li;
    private @LayoutRes
    int layout;
    private @Nullable
    int viewType;

    public static int LAYOUT1_CARDVIEW_1IMG_2TXT = 1;
    public static int LAYOUT2_SPINNER_1IMG_1TXT = 2;

    private ListView listView;

    public ListView setList()
    {
        this.listView = new ListView(context);
        this.listView.setAdapter(this);
        return this.listView;
    }

    private void Call()
    {
        this.ViewListener = null;
    }

    public RecceBaseAdapt()
    {
        Call();
    }

    public RecceBaseAdapt(ArrayList<?> arrayList, Context context, @LayoutRes int layout, int viewType)
    {
        Call();
        this.arrayList = arrayList;
        this.context = context;
        this.li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.viewType = viewType;
    }

    @Override
    public int getCount()
    {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = null;

        if(this.layout!=-1 && this.layout!=0)
        {
            v = this.li.inflate(this.layout, null);
            setView(v, position);
        }
        return v;
    }

    private void setView(View v, int position)
    {
        if (ViewListener != null)
        {
            ViewListener.onView(v, position);
        }
    }

    private void setFormView(View v, int position, HashMap<Integer,?> objects)
    {
        if (ViewListener != null)
        {
            ViewListener.onFormView(v, position,objects);
        }
    }

    private OnViewListener ViewListener;

    public interface OnViewListener
    {
        void onView(View v, int position);
        void onFormView(View v, int position, HashMap<Integer,?> objects);
    }

    public void setOnViewListener(@Nullable OnViewListener listener)
    {
        ViewListener = listener;
    }

    @Nullable
    public final OnViewListener getOnViewListener()
    {
        return ViewListener;
    }
}