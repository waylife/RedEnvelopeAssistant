package com.nearucenterplaza.redenvelopeassistant.utils;

import java.io.File;

public class FileUtils {
	/**
	 * 递归删除文件和文件夹
	 *
	 * @param file
	 *            要删除的根目录
	 */
	public static void recursionDeleteFile(File file) {
		if (file.isFile()) {
			boolean b = file.delete();
			//XLog.i("file=" + file.getAbsolutePath() + "result=" + b);
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				recursionDeleteFile(f);
			}
			file.delete();
		}
	}
}
