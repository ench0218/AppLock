package com.ench_wu.applock.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ench_wu.applock.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView tv_lock_tag;
    private TextView tv_unLock_tag;
    private FragmentManager fm;
    private UnLockFragment unLockFragment;
    private FrameLayout unlock_Fragment;
    private FragmentTransaction mTransaction;
    private FrameLayout lock_Fragment;
    private LockFragment lockFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        tv_lock_tag.setOnClickListener(this);
        tv_unLock_tag.setOnClickListener(this);

    }

    public void initUI() {
        lock_Fragment = (FrameLayout) findViewById(R.id.lock_Fragment);
        tv_lock_tag = (TextView) findViewById(R.id.tv_lock_tag);
        tv_unLock_tag = (TextView) findViewById(R.id.tv_unLock_tag);



        fm = getSupportFragmentManager();

        mTransaction = fm.beginTransaction();

        unLockFragment = new UnLockFragment();
        lockFragment = new LockFragment();

        mTransaction.replace(R.id.lock_Fragment, unLockFragment).commit();

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {

            //未锁定
            case R.id.tv_unLock_tag:
                tv_unLock_tag.setBackgroundResource(R.mipmap.tab_left_pressed);
                tv_lock_tag.setBackgroundResource(R.mipmap.tab_left_default);
                //切换到unlockFragment
                fragmentTransaction.replace(R.id.lock_Fragment, unLockFragment);
                System.out.println("tv_unLock_tag");
                break;
            //已锁定
            case R.id.tv_lock_tag:
                tv_lock_tag.setBackgroundResource(R.mipmap.tab_right_pressed);
                tv_unLock_tag.setBackgroundResource(R.mipmap.tab_left_default);

                //切换到lockFragment
                fragmentTransaction.replace(R.id.lock_Fragment, lockFragment);
                System.out.println("tv_lock_tag");
                break;
        }
        fragmentTransaction.commit();
    }
}
