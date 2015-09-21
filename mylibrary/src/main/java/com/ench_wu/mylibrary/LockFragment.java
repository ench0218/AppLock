package com.ench_wu.mylibrary;

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

import java.util.ArrayList;
import java.util.List;
/**
 * ============================================================
 * 
 * �� Ȩ �� �������Ա�������� ��Ȩ���� (c) 2015
 * 
 * �� �� : ��ΰ��
 * 
 * �� �� �� 1.0
 * 
 * �������� �� 2015-3-7 ����11:08:54
 * 
 * �� �� ��
 * 
 *         �Ѿ���������
 * �޶���ʷ ��
 * 
 * ============================================================
 **/
public class LockFragment extends Fragment {

	private ListView list_view;
	private TextView tv_lock;
	private List<AppInfo> lockLists;
	private AppLockDao dao;
	private LockAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.item_lock_fragment, null);
		
		list_view = (ListView) view.findViewById(R.id.list_view);
		
		tv_lock = (TextView) view.findViewById(R.id.tv_lock);
		
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//�õ����е�Ӧ�ó���
		List<AppInfo> appInfos = AppInfoParser.getAppInfos(getActivity());
		//��ʼ��һ�������ļ���
		
		lockLists = new ArrayList<AppInfo>();
	
		
		dao = new AppLockDao(getActivity());
		for (AppInfo appInfo : appInfos) {
			//������ҵ���ǰ�İ���˵���ڳ����������ݿ�����
			if(dao.find(appInfo.getPackname())){
				lockLists.add(appInfo);
			}else{
				
			}
		}
		adapter = new LockAdapter();
		list_view.setAdapter(adapter);	
	}
	
	
	private class LockAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			tv_lock.setText("�Ѽ���(" + lockLists.size() +")��");
			return lockLists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lockLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view;
			ViewHolder holder = null;
			if(convertView == null){
				view = View.inflate(getActivity(), R.layout.item_lock, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holder.iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
				view.setTag(holder);
			}else{
				view = convertView;
				
				holder = (ViewHolder) view.getTag();
			}
			final AppInfo appInfo = lockLists.get(position);
			holder.iv_icon.setImageDrawable(appInfo.getIcon());
			holder.tv_name.setText(appInfo.getName());
			
			holder.iv_lock.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f, 
							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
					translateAnimation.setDuration(5000);
				    view.startAnimation(translateAnimation);
				    
				    new Thread(){
				    	public void run() {
				    		
				    		SystemClock.sleep(5000);
				    		
				    		getActivity().runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									dao.delete(appInfo.getPackname());
								    lockLists.remove(position);
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
	
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_name;
		ImageView iv_lock;
	}
	
	
}
