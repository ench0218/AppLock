package com.ench_wu.applock.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ench_wu.applock.R;
import com.ench_wu.applock.db.Dao.AppLockDao;
import com.ench_wu.applock.engin.AppInfo;
import com.ench_wu.applock.engin.AppInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要android.permission.READ_PHONE_STATE权限
 * A placeholder fragment containing a simple view.
 */
public class UnLockFragment extends Fragment {
    private ListView lvUnlock;
    private TextView tvSize;
    private View view;
    private UnlockAdapter adapter;
    private ViewHolder holder;
    private AppLockDao appLockDao;
    private ArrayList<AppInfo> unLockInfo;
    private List<AppInfo> appInfos;
    private RelativeLayout pgb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unlock_fragment, container, false);
        initUI();
        return view;
    }

    public void initUI() {
        lvUnlock = (ListView) view.findViewById(R.id.lv_unLock);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        pgb = (RelativeLayout) view.findViewById(R.id.pgb);
        appInfos = AppInfos.getAppInfos(getActivity());


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter = new UnlockAdapter();
            lvUnlock.setAdapter(adapter);
            lvUnlock.setLayoutAnimation(getListAnim());
            pgb.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        pgb.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                appInfos = AppInfos.getAppInfos(getActivity());
                appLockDao = new AppLockDao(getActivity());


                unLockInfo = new ArrayList<AppInfo>();

                for (AppInfo appInfo : appInfos) {
                    String PackageName = appInfo.getApkPackageName();
                    boolean query = appLockDao.query(PackageName);
                    if (query) {

                    } else {
                        unLockInfo.add(appInfo);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }.start();


    }

    private class UnlockAdapter extends BaseAdapter {

        @Override

        public int getCount() {
            tvSize.setText("应用程序(" + unLockInfo.size() + ")个");
            return unLockInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return unLockInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = convertView.inflate(getActivity(), R.layout.item_unlock, null);
                holder = new ViewHolder();
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
                holder.ivLock = (Button) convertView.findViewById(R.id.iv_lock);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            Drawable icon = unLockInfo.get(position).getIcon();
            final String apkName = unLockInfo.get(position).getApkName();

            holder.ivIcon.setImageDrawable(icon);
            holder.tvAppName.setText(apkName);


            holder.ivLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    scaleAnimation.setDuration(1000);
//                    finalConvertView.setAnimation(scaleAnimation);
                    appLockDao.add(unLockInfo.get(position).getApkPackageName());
                    unLockInfo.remove(position);
                    // 刷新界面
                    adapter.notifyDataSetChanged();

                }


            });
            return convertView;
        }
    }


    static class ViewHolder {
        ImageView ivIcon;
        Button ivLock;
        TextView tvAppName;
    }

    private LayoutAnimationController getListAnim() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(100);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        return controller;
    }
}



