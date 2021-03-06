package com.example.rollwar.page.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rollwar.R;
import com.example.rollwar.gamedata.Enemy;
import com.example.rollwar.gamedata.EnemySpawn;
import com.example.rollwar.gamedata.Player;
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.dialog.GameDialog;
import com.example.rollwar.page.dialog.MyDialog;

import java.util.ArrayList;
import java.util.Objects;

public class GameFragment extends Fragment implements View.OnClickListener{
    private static Player mIvPlayer; //角色
    private static ProgressBar mPbHealth; //生命条
    private static TextView mTvPoint; //得分
    private static TextView mTvMaxPoint; //最高分
    private ImageButton mBtnPause; //暂停按钮
    private static ArrayList<Enemy> attackEnemyArrayList; //装可攻击的敌人列表(即在屏幕中的敌人)

    public static boolean gameOver; //游戏是否结束
    public static int point; //得分

    //怪物生成器
    private EnemySpawn enemySpawn1;
    private EnemySpawn enemySpawn2;
    private EnemySpawn enemySpawn3;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static androidx.constraintlayout.widget.ConstraintLayout layout;

    public static ProgressBar getPbHealth() {
        return mPbHealth;
    }

    public static Player getIvPlayer() {
        return mIvPlayer;
    }



    public static ArrayList<Enemy> getAttackEnemyArrayList() {
        return attackEnemyArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initClick();
        refreshView();
    }

    private void initClick(){
        mBtnPause.setOnClickListener(this);
    }

    private void initView(View view){
        mIvPlayer = view.findViewById(R.id.main_game_iv_player);
        mPbHealth = view.findViewById(R.id.main_game_pb_health);
        mTvPoint = view.findViewById(R.id.main_game_tv_point);
        mTvMaxPoint = view.findViewById(R.id.main_game_tv_maxpoint);
        enemySpawn1 = view.findViewById(R.id.main_game_spawn1);
        enemySpawn2 = view.findViewById(R.id.main_game_spawn2);
        enemySpawn3 = view.findViewById(R.id.main_game_spawn3);
        mBtnPause = view.findViewById(R.id.main_game_btn_pause);
        mPbHealth.setProgress(100);

        layout = view.findViewById(R.id.game_layout);
        int selectPlayer = MainFragment.selectPlayer;
        switch (selectPlayer){
            case 1 :{
                mIvPlayer.setAttribute(R.drawable.man1,100,20,0.4f,30,false,1,30,1,R.drawable.ammo1);
                break;
            }
            case 2 :{
                mIvPlayer.setAttribute(R.drawable.man2,50,15,0.6f,30,false,4,45,2,R.drawable.ammo2);
                break;
            }
            case 3 :{
                mIvPlayer.setAttribute(R.drawable.man3,180,45,0.5f,40,false,1,5,3,R.drawable.ammo3);
                break;
            }
            case 4 :{
                mIvPlayer.setAttribute(R.drawable.man4,80,25,0.2f,30,false,1,50,4,R.drawable.ammo4);
                break;
            }
            default:{
                MainFragment.selectPlayer = 1;
                mIvPlayer.setAttribute(R.drawable.man1,100,20,0.4f,30,false,1,30,1,R.drawable.ammo1);
                break;
            }
        }
        mIvPlayer.start();
        enemySpawn1.start();
        enemySpawn2.start();
        enemySpawn3.start();
    }

    private void initData(){
        gameOver = false;
        point = 0;
        attackEnemyArrayList = new ArrayList<>();
    }

    @SuppressLint("SetTextI18n")
    public static void refreshView(){
        mTvPoint.setText("得分:"+point);
        mTvMaxPoint.setText("最高分:"+MainActivity.maxPoint);
        mPbHealth.setProgress(100 * mIvPlayer.getHealth() / mIvPlayer.getMaxHealth());
    }

    //切换碎片的方法
    private void replaceFragment(Fragment fragment){
        fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(Objects.requireNonNull(requireActivity().getSupportFragmentManager().findFragmentById(R.id.activity_main)));
        fragmentTransaction.add(R.id.activity_main, fragment);
        fragmentTransaction.addToBackStack(null);   //添加进回退栈
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_game_btn_pause:{
                GameDialog myDialog = new GameDialog(requireContext());
                myDialog.setTitle("菜单").show();
            }
        }
    }

}
