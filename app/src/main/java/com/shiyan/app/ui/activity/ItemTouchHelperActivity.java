package com.shiyan.app.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiyan.app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemTouchHelperActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_helper);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter();
        //先实例化Callback
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<ItemTouchHelperActivity.HomeAdapter.MyViewHolder> implements ItemTouchHelperActivity.ItemTouchHelperAdapter
    {

        @Override
        public ItemTouchHelperActivity.HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            ItemTouchHelperActivity.HomeAdapter.MyViewHolder holder = new ItemTouchHelperActivity.HomeAdapter.MyViewHolder(LayoutInflater.from(
                    ItemTouchHelperActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemTouchHelperActivity.HomeAdapter.MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            //交换位置
            Collections.swap(mDatas,fromPosition,toPosition);
            notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemDissmiss(int position) {
            //移除数据
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }

    public interface ItemTouchHelperAdapter {
        //数据交换
        void onItemMove(int fromPosition,int toPosition);
        //数据删除
        void onItemDissmiss(int position);
    }


    public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback{

        private ItemTouchHelperActivity.ItemTouchHelperAdapter mAdapter;

        public SimpleItemTouchHelperCallback(ItemTouchHelperActivity.ItemTouchHelperAdapter adapter){
            mAdapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
        }
    }
}
