package com.ench_wu.mylibrary;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.db.dao.AppLockDao;
import com.itheima.mobileguard.domain.AppInfo;
import com.itheima.mobileguard.engine.AppInfoParser;

public class UnLockFragment extends Fragment {

	private View view;
	private TextView tv_unlock;
	private ListView list_view;
	private List<AppInfo> appInfos;
	private AppLockDao dao;
	private List<AppInfo> unLockLists;
	private UnLockAdapter adapter;

	/*
	 * ����activity�����setContentView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.item_unlock_fragment, null);

		list_view = (ListView) view.findViewById(R.id.list_view);

		tv_unlock = (TextView) view.findViewById(R.id.tv_unlock);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		appInfos = AppInfoParser.getAppInfos(getActivity());

		// ��ȡ����������dao

		dao = new AppLockDao(getActivity());
		// ��ʼ��һ��û�м����ļ���

		unLockLists = new ArrayList<AppInfo>();

		for (AppInfo appInfo : appInfos) {
			// �жϵ�ǰ��Ӧ���Ƿ��ڳ���������������
			if (dao.find(appInfo.getPackname())) {

			} else {
				// �����ѯ����˵��û���ڳ����������ݿ�����
				unLockLists.add(appInfo);
			}
		}

		adapter = new UnLockAdapter();
		list_view.setAdapter(adapter);
	}

	public class UnLockAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			tv_unlock.setText("δ����(" + unLockLists.size() + ")��");
			return unLockLists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return unLockLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			final View view;
			final AppInfo appInfo;
			if (convertView == null) {
				view = View.inflate(getActivity(), R.layout.item_unlock, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holder.iv_unlock = (ImageView) view
						.findViewById(R.id.iv_unlock);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();

			}
			// ��ȡ����ǰ�Ķ���
			appInfo = unLockLists.get(position);

			holder.iv_icon
					.setImageDrawable(unLockLists.get(position).getIcon());
			holder.tv_name.setText(unLockLists.get(position).getName());
			// �ѳ�����ӵ����������ݿ�����
			holder.iv_unlock.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// ��ʼ��һ��λ�ƶ���

					TranslateAnimation translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 1.0f,
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 0);
					// ���ö���ʱ��
					translateAnimation.setDuration(5000);
					// ��ʼ����
					view.startAnimation(translateAnimation);

					new Thread(){
						public void run() {
							SystemClock.sleep(5000);
							
							getActivity().runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									// ��ӵ����ݿ�����
									dao.add(appInfo.getPackname());
									// �ӵ�ǰ��ҳ���Ƴ�����
									unLockLists.remove(position);
									// ˢ�½���
									adapter.notifyDataSetChanged();
								}
							});
							
							
						};
					}.start();
					
					

				}
			});

			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		ImageView iv_unlock;
	}
}
