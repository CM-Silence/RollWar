package com.example.rollwar.page.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.rollwar.R;
import com.example.rollwar.bean.EnemyDataBean;
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.adapter.RvEnemyAdapter;
import com.example.rollwar.view.MyImage;

import java.util.ArrayList;

public class EnemyFragment extends Fragment implements View.OnClickListener, RvEnemyAdapter.InnerHolder.EnemyDataListener {

    private RecyclerView mRvEnemy;
    private Button mBtnBack;
    private TextView mTvEnemyName;
    private TextView mTvEnemyIntroduce;
    private MyImage mIvEnemyImage;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ArrayList<EnemyDataBean> enemyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enemy, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initRv(view);
        initClick();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        int id = enter ? R.anim.anim_alpha_in : R.anim.anim_alpha_out;
        return AnimationUtils.loadAnimation(getActivity(), id);
    }

    private void initData(){
        enemyList = new ArrayList<>();
        enemyList.add(new EnemyDataBean(R.drawable.enemy1,"普通学生","虽然学习一般般,不过有时问出的问题也难以解答,战斗力较弱,但数量很多。主要通过碰撞来进行攻击,只要保持距离就没什么威胁"));
        enemyList.add(new EnemyDataBean(R.drawable.enemy2,"奋斗学生","充满学习动力的学生,学习还算可以,有时会钻研一些难题,战斗力一般,但数量较多。攻击方式为直线单体远程攻击"));
        enemyList.add(new EnemyDataBean(R.drawable.enemy3,"学霸","学习较好的学生,问出的问题不是一般人能解答的,战斗力较高,有一定的威胁性。攻击方式为单体远程瞄准攻击,即为朝着你所在的方向攻击"));
        enemyList.add(new EnemyDataBean(R.drawable.enemy4,"学神","学习非常好的学生,功底十分扎实,但如果有学习上的问题基本没几个人能帮他解决,战斗力很高,需要尽快解决,否则将会产生很大的威胁。攻击方式为单体远程特殊攻击,这种特殊攻击具有跟踪能力。"));
        enemyList.add(new EnemyDataBean(R.drawable.boss,"卷怪","???"));
        for(int i = 0; i < 23; i++){
            enemyList.add(new EnemyDataBean(R.drawable.unknown_enemy,"???","尚未发现"));
        }
    }

    private void initView(View view){
        mRvEnemy = view.findViewById(R.id.login_enemyfragment_rv_enemy);
        mBtnBack = view.findViewById(R.id.login_enemyfragment_btn_back);
        mTvEnemyName = view.findViewById(R.id.login_enemyfragment_tv_name);
        mTvEnemyIntroduce = view.findViewById(R.id.login_enemyfragment_tv_introduce);
        mIvEnemyImage = view.findViewById(R.id.login_enemyfragment_iv_enemy);
    }

    private void initClick(){
        mBtnBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_enemyfragment_btn_back:{
                requireActivity().onBackPressed();
                MainFragment mainFragment = (MainFragment) MainActivity.mainFragment;
                mainFragment.menuBack();
            }
        }
    }

    @Override
    public void getEnemyData(String enemyName, String enemyIntroduce, int ImageID, int position) {
        mTvEnemyName.setText(enemyName);
        mTvEnemyIntroduce.setText(enemyIntroduce);
        mIvEnemyImage.setImageResource(ImageID);
        for(int i = 0; i < mRvEnemy.getChildCount(); i++){
            if(i != position) {
                FrameLayout layout = (FrameLayout) mRvEnemy.getChildAt(i);
                layout.findViewById(R.id.login_enemyfragment_rv_iv).setBackgroundResource(R.drawable.general_background);
            }
        }
    }

    protected void initRv(View view) {
        RecyclerView mRvLesson = view.findViewById(R.id.login_enemyfragment_rv_enemy);
        RvEnemyAdapter rvEnemyAdapter = new RvEnemyAdapter(enemyList);
        mRvLesson.setAdapter(rvEnemyAdapter);
        mRvLesson.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
    }
}
