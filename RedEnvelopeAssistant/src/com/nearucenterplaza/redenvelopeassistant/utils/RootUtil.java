package com.nearucenterplaza.redenvelopeassistant.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import android.util.Log;
/**
 * reference:http://itmanito.com/geolo/?p=67<br/> 
 * */
public class RootUtil {
	private final static String TAG = "RootUtil";

	/** Check weather the phone is root or not, without a root bypass popup dialog */
	public static boolean isRoot() {
		String binPath = "/system/bin/su";
		String xBinPath = "/system/xbin/su";
		if (new File(binPath).exists() && isExecutable(binPath))
			return true;
		if (new File(xBinPath).exists() && isExecutable(xBinPath))
			return true;
		return false;
	}

	private static boolean isExecutable(String filePath) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("ls -l " + filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String str = in.readLine();
			Log.i(TAG, str);
			if (str != null && str.length() >= 4) {
				char flag = str.charAt(3);
				if (flag == 's' || flag == 'x')
					return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(p!=null){
				p.destroy();
			}
		}
		return false;
	}
}
