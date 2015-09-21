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

public class LockFragment extends Fragment {

    private AppLockDao appLockDao;
    private List<AppInfo> appInfos;
    private ListView lvLock;
    private AppLockAdapter adapter;
    private ArrayList<AppInfo> lockInfos;
    private Drawable icon;
    private ViewHolder holder;
    private TextView tvSize;
    private RelativeLayout pgb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lock_fragment, container, false);
        lvLock = (ListView) view.findViewById(R.id.lv_Lock);
        tvSize = (TextView) view.findViewById(R.id.tv_lock_size);
        pgb = (RelativeLayout) view.findViewById(R.id.pgb_lock);

        return view;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            pgb.setVisibility(View.INVISIBLE);
            adapter = new AppLockAdapter();
            lvLock.setAdapter(adapter);
            lvLock.setLayoutAnimation(getListAnim());

        }
    };

    @Override
    public void onStart() {
        super.onStart();
        pgb.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {
                appLockDao = new AppLockDao(getActivity());

                appInfos = AppInfos.getAppInfos(getActivity());

                lockInfos = new ArrayList<AppInfo>();

                for (AppInfo appInfo :
                        appInfos) {
                    String PackageName = appInfo.getApkPackageName();

                    if (appLockDao.query(PackageName)) {
                        lockInfos.add(appInfo);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    private class AppLockAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            tvSize.setText("应用程序(" + lockInfos.size() + ")个");
            return lockInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return lockInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = convertView.inflate(getActivity(), R.layout.item_lock, null);
                holder = new ViewHolder();
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv1_icon);
                holder.tvAppName = (TextView) convertView.findViewById(R.id.tv_app1_name);
                holder.ivUnLock = (Button) convertView.findViewById(R.id.iv_unlock);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ivIcon.setImageDrawable(lockInfos.get(position).getIcon());
            holder.tvAppName.setText(lockInfos.get(position).getApkName());

            holder.ivUnLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    appLockDao.delete(lockInfos.get(position).getApkPackageName());

                    lockInfos.remove(position);
                    adapter.notifyDataSetChanged();

                }
            });

            return convertView;
        }

    }

    static class ViewHolder {
        ImageView ivIcon;
        Button ivUnLock;
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
