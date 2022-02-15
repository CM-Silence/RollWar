package com.example.rollwar.page.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rollwar.R;
import com.example.rollwar.bean.EnemyDataBean;
import com.example.rollwar.page.fragment.MainFragment;
import com.example.rollwar.view.MyImage;

import java.util.ArrayList;

public class RvEnemyAdapter extends RecyclerView.Adapter<RvEnemyAdapter.InnerHolder>{
    private static ArrayList<EnemyDataBean> enemyList; //用于储存课程的列表

    public RvEnemyAdapter(ArrayList<EnemyDataBean> enemyList) {
        RvEnemyAdapter.enemyList = enemyList; //通过构造方法传入数据
    }

    @NonNull
    @Override
    public RvEnemyAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_enemy_rv, parent, false));
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RvEnemyAdapter.InnerHolder holder, int position) {
        holder.mIvEnemy.setImageResource(enemyList.get(position).getEnemyImageID());
        holder.mIvEnemy.setImageID(enemyList.get(position).getEnemyImageID());
        holder.initData(enemyList.get(position).getEnemyName(),enemyList.get(position).getEnemyIntroduce(),enemyList.get(position).isChoose());
    }

    @Override
    public int getItemCount() {
        return enemyList.size(); //获取数据个数(要生产多少个View)
    }

    //内部静态类
    public static class InnerHolder extends RecyclerView.ViewHolder
    {
        private MyImage mIvEnemy;
        private String enemyName;
        private String enemyIntroduce;

        public MyImage getIvEnemy() {
            return mIvEnemy;
        }

        public interface EnemyDataListener
        {
            void getEnemyData(String enemyName, String enemyIntroduce, int ImageID, int position);
        }

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mIvEnemy = itemView.findViewById(R.id.login_enemyfragment_rv_iv);
            initClickListener();
        }

        public void initData(String enemyName, String enemyIntroduce,boolean choose){
            this.enemyName = enemyName;
            this.enemyIntroduce = enemyIntroduce;
            if(choose) {
                this.mIvEnemy.setBackgroundResource(R.drawable.general_background_press);
            }
            else{
                this.mIvEnemy.setBackgroundResource(R.drawable.general_background);
            }
        }

        private void initClickListener(){
            mIvEnemy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //接口回调将数据传给MainFragment
                    EnemyDataListener enemyDataListener = (EnemyDataListener) MainFragment.enemyFragment;
                    enemyDataListener.getEnemyData(enemyName,enemyIntroduce,mIvEnemy.getImageID(),getAdapterPosition());
                    mIvEnemy.setBackgroundResource(R.drawable.general_background_press);
                    for(int i = 0; i < enemyList.size(); i++){
                        enemyList.get(i).setChoose(false);
                    }
                    enemyList.get(getAdapterPosition()).setChoose(true);
                }
            });
        }
    }
}

