package com.js.smart.common.util.downloader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 一个业务类
 */
public class DownloaderDao {
	private static DownloaderDao dao=null;
	private Context context;
	private DownloaderDao(Context context) {
		this.context=context;
	}
	public static  DownloaderDao getInstance(Context context){
		if(dao==null){
			dao=new DownloaderDao(context);
		}
		return dao;
	}
	public SQLiteDatabase getConnection() {
		SQLiteDatabase sqliteDatabase = null;
		try { 
			sqliteDatabase= new DownloaderDBHelper(context).getReadableDatabase();
		} catch (Exception e) {
		}
		return sqliteDatabase;
	}

	/**
	 * 查看数据库中是否有数据
	 */
	public synchronized boolean isHasInfo(String url) {
		SQLiteDatabase database = getConnection();
		int count = -1;
		Cursor cursor = null;
		try {
			String sql = "select count(*)  from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { url });
			if (cursor.moveToFirst()) {
				count = cursor.getInt(0);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return count == 0;
	}

	/**
	 * 保存 下载的具体信息
	 */
	public synchronized void saveInfo(List<DownloadInfo> info) {
		SQLiteDatabase database = getConnection();
		try {
			for (DownloadInfo download : info) {
				String sql = "insert into download_info(thread_id,start_pos, end_pos,complete_size,url) values (?,?,?,?,?)";
				Object[] bindArgs = { download.getThreadId(), download.getStartPos(),
						download.getEndPos(), download.getCompleteSize(),
						download.getUrl() };
				database.execSQL(sql, bindArgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 得到下载具体信息
	 */
	public synchronized List<DownloadInfo> getInfo(String url) {
		List<DownloadInfo> list = new ArrayList<>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try {
			String sql = "select thread_id, start_pos, end_pos,complete_size,url from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { url });
			while (cursor.moveToNext()) {
				DownloadInfo download = new DownloadInfo(cursor.getInt(0),
						cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
						cursor.getString(4));
				list.add(download);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 更新数据库中的下载信息
	 */
	public synchronized void updateInfo(int threadId, int complete, String url) {
		SQLiteDatabase database = getConnection();
		try {
			String sql = "update download_info set complete_size=? where thread_id=? and url=?";
			Object[] bindArgs = { complete, threadId, url };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 下载完成后删除数据库中的数据
	 */
	public synchronized void delete(String url) {
		SQLiteDatabase database = getConnection();
		try {
			database.delete("download_info", "url=?", new String[] { url });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}
}