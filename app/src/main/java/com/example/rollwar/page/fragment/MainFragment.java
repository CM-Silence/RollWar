package com.example.rollwar.page.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.rollwar.page.activity.MainActivity.*;
import static com.example.rollwar.utils.AnimationUtil.alphaAppear;
import static com.example.rollwar.utils.AnimationUtil.alphaDisappear;
import static com.example.rollwar.utils.AnimationUtil.alphaRLBack;
import static com.example.rollwar.utils.AnimationUtil.alphaRLLeave;
import static com.example.rollwar.utils.AnimationUtil.alphaUDBack;
import static com.example.rollwar.utils.AnimationUtil.alphaUDLeave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rollwar.R;
import com.example.rollwar.bean.PlayerDataBean;
import com.example.rollwar.page.adapter.Vp2PlayerAdapter;
import com.example.rollwar.page.dialog.MyDialog;
import com.example.rollwar.view.SoundSettingButton;
import com.example.rollwar.view.Vp2OptionTransformer;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment implements View.OnClickListener {

    //各个控件
    private ViewPager2 mVp2Option;
    private TextView mTvPlayerName;
    private TextView mTvIntroduce;
    private Button mBtnPlay;
    private Button mBtnHelp;
    private Button mBtnAbout;
    private Button mBtnEnemy;
    private Button mBtnPlayer;
    private Button mBtnChoosePlayer;
    private Button mBtnBack;
    private SoundSettingButton mBtnSetting;
    private LinearLayout mLinerLayoutVp2;
    private LinearLayout mLinerLayoutLeftButton;
    private LinearLayout mLinerLayoutRightButton;



    //有关碎片的东西
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static Fragment enemyFragment;
    public static Fragment gameFragment;

    private Vp2PlayerAdapter vp2PlayerAdapter;

    //一些数据
    private int player; //角色
    public static int selectPlayer; //被选中的角色
    private int vp2Position = 0; //ViewPager2目前所处的页面

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initFragment();
        initView(view);
        initVp2();
        initClick();
        initAttribute();
    }


    private void initView(View view){
        mVp2Option = view.findViewById(R.id.main_vp2_option);
        mTvPlayerName = view.findViewById(R.id.main_tv_player);
        mTvIntroduce = view.findViewById(R.id.main_tv_introduce);
        mBtnPlay = view.findViewById(R.id.main_btn_play);
        mBtnHelp = view.findViewById(R.id.main_btn_help);
        mBtnAbout = view.findViewById(R.id.main_btn_about);
        mBtnEnemy = view.findViewById(R.id.main_btn_enemy);
        mBtnPlayer = view.findViewById(R.id.main_btn_player);
        mBtnChoosePlayer = view.findViewById(R.id.main_btn_choose);
        mBtnBack = view.findViewById(R.id.main_btn_back);
        mBtnSetting = view.findViewById(R.id.main_btn_setting);
        mLinerLayoutVp2 = view.findViewById(R.id.main_layout_vp2);
        mLinerLayoutLeftButton = view.findViewById(R.id.main_layout_left_btn);
        mLinerLayoutRightButton = view.findViewById(R.id.main_layout_right_btn);

    }

    private void initClick(){
        mBtnPlay.setOnClickListener(this);
        mBtnHelp.setOnClickListener(this);
        mBtnAbout.setOnClickListener(this);
        mBtnEnemy.setOnClickListener(this);
        mBtnPlayer.setOnClickListener(this);
        mBtnChoosePlayer.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    private void initAttribute(){
        mBtnBack.setEnabled(false);
        mBtnChoosePlayer.setEnabled(false);

        mBtnBack.setAlpha(0);
        mBtnChoosePlayer.setAlpha(0);
        mVp2Option.setAlpha(0);
        mTvPlayerName.setAlpha(0);
        mTvIntroduce.setAlpha(0);
    }

    private void initVp2(){

        //设置CompositePageTransformer
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        //添加边距Transformer
        compositePageTransformer.addTransformer(new MarginPageTransformer(50));
        //添加缩放效果的Transformer
        compositePageTransformer.addTransformer(new Vp2OptionTransformer());
        mVp2Option.setPageTransformer(compositePageTransformer);
        //预加载页面数量
        mVp2Option.setOffscreenPageLimit(1);

        ArrayList<PlayerDataBean> optionList = new ArrayList<>();
        optionList.add(new PlayerDataBean(R.drawable.player1,R.drawable.pen));
        optionList.add(new PlayerDataBean(R.drawable.player2,R.drawable.book));
        optionList.add(new PlayerDataBean(R.drawable.player3,R.drawable.club));
        optionList.add(new PlayerDataBean(R.drawable.player4,R.drawable.lighter));

        vp2PlayerAdapter = new Vp2PlayerAdapter(optionList);
        mVp2Option.setAdapter(vp2PlayerAdapter);


        //滑动监听
        //在用户滑动时设置文字透明度并在合适的时候切换文字
        mVp2Option.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                vp2Position = position;
                switch (optionList.get(position).getPlayerImageID()) {
                    case R.drawable.player1: {
                        mTvPlayerName.setText("佛系青年");
                        mTvIntroduce.setText("一名佛系青年,喜欢直接摆烂,只要不挂科就行。\n使用钢笔进行远程单体攻击,战斗力中规中矩\n使用技能后会进行临时泡佛脚,攻速大幅提升");
                        player = 1;
                        break;
                    }
                    case R.drawable.player2: {
                        mTvPlayerName.setText("卷王");
                        mTvIntroduce.setText("一名热爱学习的卷王,宁愿累死自己也要卷死同学。\n使用书本进行远程散射攻击,攻击力较高,但自身生命值较少\n使用技能后会运用知识的力量产生护盾抵挡敌人的进攻");
                        player = 2;

                        break;
                    }
                    case R.drawable.player3: {
                        mTvPlayerName.setText("运动达人");
                        mTvIntroduce.setText("一名运动狂人,喜欢打球,不喜欢学习。\n使用棒球棍进行近战,攻击力较高且自身生命值较高\n使用技能后的一段时间会用棒球棍打出棒球进行运程攻击");
                        player = 3;
                        break;
                    }
                    case R.drawable.player4: {
                        mTvPlayerName.setText("毁灭者");
                        mTvIntroduce.setText("学校中最为恐怖的存在,使用打火机摧毁前方的一切难题。\n使用打火机进行远程单体攻击,攻击力中等且自身生命值中等\n使用技能会进行中程喷火攻击,威力极大");
                        player = 4;
                        break;
                    }
                }
                if(player == selectPlayer){
                    mBtnChoosePlayer.setText("已选择");
                    mBtnChoosePlayer.setBackgroundResource(R.drawable.general_background_press);
                }
                else{
                    mBtnChoosePlayer.setText("选择");
                    mBtnChoosePlayer.setBackgroundResource(R.drawable.selector_btn);
                }
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffset > 0.5) { //左滑
                    mTvPlayerName.setAlpha((positionOffset - 0.5f) * 2.0f);
                    mTvIntroduce.setAlpha((positionOffset - 0.5f) * 2.0f);
                }
                else if (positionOffset > 0 && positionOffset < 0.5){ //右滑
                    mTvPlayerName.setAlpha((0.5f - positionOffset) * 2.0f);
                    mTvIntroduce.setAlpha((0.5f - positionOffset) * 2.0f);
                }

                if(positionOffset > 0.3 && positionOffset < 0.7){
                    //用四舍五入切换文字(移到一半的时候)
                    switch (optionList.get(position + Math.round(positionOffset)).getPlayerImageID()) {
                        case R.drawable.player1: {
                            mTvPlayerName.setText("佛系青年");
                            mTvIntroduce.setText("一名佛系青年,喜欢直接摆烂,只要不挂科就行。\n使用钢笔进行远程单体攻击,战斗力中规中矩\n使用技能后会进行临时泡佛脚,攻速大幅提升");
                            break;
                        }
                        case R.drawable.player2: {
                            mTvPlayerName.setText("卷王");
                            mTvIntroduce.setText("一名热爱学习的卷王,宁愿累死自己也要卷死同学。\n使用书本进行远程散射攻击,攻击力较高,但自身生命值较少\n使用技能后会运用知识的力量产生护盾抵挡敌人的进攻");
                            break;
                        }
                        case R.drawable.player3: {
                            mTvPlayerName.setText("运动达人");
                            mTvIntroduce.setText("一名运动狂人,喜欢打球,不喜欢学习。\n使用棒球棍进行近战,攻击力较高且自身生命值较高\n使用技能后的一段时间会用棒球棍打出棒球进行运程攻击");
                            break;
                        }
                        case R.drawable.player4: {
                            mTvPlayerName.setText("毁灭者");
                            mTvIntroduce.setText("学校中最为恐怖的存在,使用打火机摧毁前方的一切难题。\n使用打火机进行远程单体攻击,攻击力与自身生命值处于中等水平\n使用技能后会进行中程喷火攻击,威力极大");
                            break;
                        }
                    }
                }

            }

        });
    }

    //开始该活动的方法
    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainFragment.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_play:{
                play();
                break;
            }
            case R.id.main_btn_help:{
                MyDialog dialog = new MyDialog(requireActivity());
                dialog.setTitle("操作说明").setMessage("1.人物会移动到你的手指位置\n2.当能量足够时可以释放技能来消灭敌人或者保护自己\n不同的武器技能也不同").show();
                break;
            }
            case R.id.main_btn_about:{
                MyDialog dialog = new MyDialog(requireActivity());
                dialog.setTitle("关于").setMessage("Silence的红岩作业\n开始制作时间:2022年1月15日\n疯狂制作时间:2022年2月6日\n完工日期:2022年").show();
                break;
            }
            case R.id.main_btn_enemy:{
                enemy();
                break;
            }
            case R.id.main_btn_player:{
                choosePlayer();
                break;
            }
            case R.id.main_btn_choose:{
                choose();
                break;
            }
            case R.id.main_btn_back:{
                back();
                break;
            }
            default:{
                break;
            }
        }
    }

    private void initData(){
        sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        selectPlayer = sharedPreferences.getInt("player",1);
    }

    private void initFragment(){
        if(enemyFragment == null) {
            enemyFragment = new EnemyFragment();
        }
        if(gameFragment == null){
            gameFragment = new GameFragment();
        }
    }

    private void play(){
        replaceFragment(gameFragment);
        menuLeave();
        if(mBtnSetting.isMenuOpen()){
            mBtnSetting.menuClose();
        }
    }

    private void choosePlayer(){
        menuLeave();
        alphaUDBack(mBtnBack,1,0);
        alphaUDBack(mBtnChoosePlayer,1,0);
        alphaUDBack(mVp2Option,-1,0);
        alphaAppear(mTvPlayerName,1.0f,0);
        alphaAppear(mTvIntroduce,1.0f,0);
        if(mBtnSetting.isMenuOpen()){
            mBtnSetting.menuClose();
        }
        //mVp2Option.getFocusedChild().findViewById(R.id.main_vp2_iv_player).setEnabled(true);
    }

    private void choose(){
        selectPlayer = player;
        editor.putInt("player",selectPlayer);
        mBtnChoosePlayer.setText("已选择");
        mBtnChoosePlayer.setBackgroundResource(R.drawable.general_background_press);
    }

    private void enemy(){
        replaceFragment(enemyFragment);
        menuLeave();
        if(mBtnSetting.isMenuOpen()){
            mBtnSetting.menuClose();
        }
    }


    public void menuBack(){
        alphaRLBack(mBtnPlay,-1);
        alphaRLBack(mBtnEnemy,-1);
        alphaRLBack(mBtnAbout,-1);
        alphaRLBack(mBtnHelp,1);
        alphaRLBack(mBtnSetting,1);
        alphaRLBack(mBtnPlayer,1);

        mBtnSetting.getBtnSetting().setEnabled(true);
        mLinerLayoutLeftButton.setEnabled(true);
        mLinerLayoutRightButton.setEnabled(true);
    }

    public void menuLeave(){
        alphaRLLeave(mBtnPlay,-1);
        alphaRLLeave(mBtnEnemy,-1);
        alphaRLLeave(mBtnAbout,-1);
        alphaRLLeave(mBtnHelp,1);
        alphaRLLeave(mBtnSetting,1);
        alphaRLLeave(mBtnPlayer,1);

        mBtnSetting.getBtnSetting().setEnabled(false);
        mLinerLayoutLeftButton.setEnabled(false);
        mLinerLayoutRightButton.setEnabled(false);
    }

    private void back(){
        menuBack();
        alphaUDLeave(mBtnBack,1,0);
        alphaUDLeave(mBtnChoosePlayer,1,0);
        alphaUDLeave(mVp2Option,-1,0);
        alphaDisappear(mTvPlayerName,0);
        alphaDisappear(mTvIntroduce,0);

        //mVp2Option.getFocusedChild().findViewById(R.id.main_vp2_iv_player).setEnabled(false);
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

}