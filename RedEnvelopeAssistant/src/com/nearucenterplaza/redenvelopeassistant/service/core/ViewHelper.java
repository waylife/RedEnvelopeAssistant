package com.nearucenterplaza.redenvelopeassistant.service.core;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHelper {
	public  static String getTitleText(ViewGroup contentView) {
		return getText(contentView, new int[] { 0, 1 }); // Empirically
															// determined
	}

	public  static  String getBodyText(ViewGroup contentView) {
		return getText(contentView, new int[] { 1, 0 });
	}

	public  static  String getText(ViewGroup contentView, int[] path) {
		View view = getView(contentView, path);
		if (!(view instanceof TextView)) {
			return "";
		}

		return ((TextView) view).getText().toString();
	}

	public  static  View getView(ViewGroup root, int[] path) {
		ViewGroup current = root;
		for (int i = 0; i < path.length; i++) {
			if (current.getChildCount() <= path[i]) {
				return null;
			}
			View child = current.getChildAt(path[i]);
			if (i == path.length - 1) {
				return child;
			}
			if (!(child instanceof ViewGroup)) {
				return null;
			}
			current = (ViewGroup) child;
		}
		return null;
	}
}
