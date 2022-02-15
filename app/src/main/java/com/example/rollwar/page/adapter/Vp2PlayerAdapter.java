package com.example.rollwar.page.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rollwar.R;
import com.example.rollwar.bean.PlayerDataBean;
import com.example.rollwar.page.dialog.MyDialog;
import com.example.rollwar.view.MyImage;

import java.util.ArrayList;

public class Vp2PlayerAdapter extends RecyclerView.Adapter<Vp2PlayerAdapter.InnerHolder>{
    private static ArrayList<PlayerDataBean> playerList; //用于储存选项按钮的列表

    public Vp2PlayerAdapter(ArrayList<PlayerDataBean> playerList){ //构造方法传入数据
        Vp2PlayerAdapter.playerList = playerList;
    }

    @NonNull
    @Override
    public Vp2PlayerAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_vp2_player, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vp2PlayerAdapter.InnerHolder holder, int position) {
        holder.mIvPlayer.setImageResource(playerList.get(position).getPlayerImageID());
        holder.mIvPlayer.setImageID(playerList.get(position).getPlayerImageID());
        holder.mIvPlayer.setEnabled(false);
        holder.mIvWeapon.setImageResource(playerList.get(position).getWeaponImageID());
        holder.mIvWeapon.setImageID(playerList.get(position).getWeaponImageID());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        private MyImage mIvPlayer;
        private MyImage mIvWeapon;

        public MyImage getIvPlayer() {
            return mIvPlayer;
        }

        public MyImage getIvWeapon() {
            return mIvWeapon;
        }

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mIvPlayer = itemView.findViewById(R.id.main_vp2_iv_player);
            mIvWeapon = itemView.findViewById(R.id.login_vp2_iv_weapon);
            initOnClickListener();
        }

        private void initOnClickListener(){
            mIvPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if () {
                        MyDialog dialog = new MyDialog(view.getContext());
                        switch (mIvPlayer.getImageID()) {
                            case R.drawable.player1: {
                                dialog.setTitle("详细数据").setMessage("生命值:100\n攻击力:20\n攻速:0.4秒/次\n技能:临时泡佛脚\n技能消耗能量:30\n技能效果:自身攻速提升50%,持续10秒").show();
                                break;
                            }
                            case R.drawable.player2: {
                                dialog.setTitle("详细数据").setMessage("生命值:50\n攻击力:15x5\n攻速:0.6秒/次\n技能:知识的力量(获得护盾)\n技能消耗能量:45\n技能效果:获得一个能够抵挡敌人子弹的护盾,持续12秒").show();
                                break;
                            }
                            case R.drawable.player3: {
                                dialog.setTitle("详细数据").setMessage("生命值:180\n攻击力:45\n攻速:0.5秒/次\n技能:强力击球\n技能消耗能量:5\n技能效果:射出一个棒球\n技能伤害:60").show();
                                break;
                            }
                            case R.drawable.player4: {
                                dialog.setTitle("详细数据").setMessage("生命值:80\n攻击力:25\n攻速:0.4秒/次\n技能:哪里不会点哪里\n技能消耗能量:50\n技能效果:攻击方式改为中程喷火,持续10秒\n技能伤害:40\n技能攻速:0.2秒/次").show();
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    //}
                }
            });
        }
    }
}
