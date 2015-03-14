package com.nearucenterplaza.redenvelopeassistant.ui.fragmant;

import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.ui.activity.HomeActivity;
import com.nearucenterplaza.redenvelopeassistant.utils.AppUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AboutFragment newInstance(int sectionNumber) {
		AboutFragment fragment = new AboutFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AboutFragment() {
	}

	TextView mGoGithubTv;
	TextView mVersionTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container, false);
		initView(rootView);
		return rootView;
	}

	void initView(View view) {
		// find views
		mGoGithubTv = (TextView) view.findViewById(R.id.about_go_github_tv);
		mVersionTv=(TextView)view.findViewById(R.id.about_version_tv);

		// set listeners
		mGoGithubTv.setOnClickListener(this);

		// set values
		mVersionTv.setText(getString(R.string.ui_about_version)+AppUtil.getVersionName(getActivity()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_go_github_tv:
			goWeb(getActivity(),"https://github.com/waylife/RedEnvelopeAssistant");
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
	
	public void goWeb(Context context,String url){
		if(context==null)
			return;
		Uri uri=Uri.parse(url);
		Intent intent=new Intent(Intent.ACTION_VIEW,uri);
		context.startActivity(intent);
	}
}
