package com.sdu.dbUtils;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sdu.beans.AdBean;
import com.sdu.beans.AppBriefBean;
import com.sdu.beans.AppDetailBean;
import com.sdu.beans.CommentBeans;
import com.sdu.beans.SortItemBean;
import com.sdu.beans.ThemeBean;
import com.sdu.beans.UserBean;
import com.sdu.utils.JsonUtils;
import com.sdu.utils.StaticVar;

public class DBManager {

	public static JsonUtils json = new JsonUtils();

	/**
	 * 连接数据库，进行处理
	 * 
	 * @return Connection对象
	 */
	private Connection getConnection() {

		Connection con = null;

		try {
			Class.forName(StaticVar.DRIVER_NAME).newInstance();

			con = DriverManager.getConnection(StaticVar.DB_URL,
					StaticVar.USER_NAME, StaticVar.DB_PASSWD);

		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}

		return con;
	}

	/**
	 * 用于获取客户端首页的信息
	 * 
	 * @return 返回json代码，发送给客户端
	 */
	public String getHomeAppBriefInfo() {

		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "select * from db_home_ad";

			ResultSet res = state.executeQuery(sql);

			res.beforeFirst();

			while (res.next()) {
				String appID = res.getString("AppID");
				String imageUrl = res.getString("ImageURL");

				json.addAdverInfo(jo, new AdBean(appID, StaticVar.CURRENT_URL
						+ imageUrl));

			}

			state.close();

			state = con.createStatement();

			String sql1 = "select * from db_app limit 0,10";

			ResultSet res1 = state.executeQuery(sql1);

			res1.last();

			int resCount = res1.getRow();

			if (resCount == 0) {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NULL);
			} else {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);

				res1.beforeFirst();

				while (res1.next()) {
					String appID = res1.getString("AppID");
					String appIconAdd = res1.getString("AppIconAdd");
					String appName = res1.getString("AppName");
					String appDownCount = res1.getString("AppDownCount");
					String appSize = res1.getString("AppSize");
					String appBriefInfo = res1.getString("AppBriefInfo");
					String appAddress = res1.getString("AppAdd");

					json.addAppInfo(jo, new AppBriefBean(appID,
							StaticVar.CURRENT_URL + appIconAdd, appName,
							appDownCount, appSize, appBriefInfo,
							StaticVar.CURRENT_URL + appAddress));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	/**
	 * 用于获取数据库中的app的简要信息
	 * 
	 * @param page
	 *            表示偏移量
	 * @return 返回json代码，发送给客户端
	 */
	public String getMoreAppBriefInfo(long page) {

		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "select * from db_app limit " + (page - 1) + ",10";

			ResultSet res = state.executeQuery(sql);

			res.last();

			int resCount = res.getRow();

			if (resCount == 0) {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NULL);
			} else {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);

				res.beforeFirst();

				while (res.next()) {
					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					json.addAppInfo(jo, new AppBriefBean(appID,
							StaticVar.CURRENT_URL + appIconAdd, appName,
							appDownCount, appSize, appBriefInfo,
							StaticVar.CURRENT_URL + appAddress));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	/**
	 * 用于获取数据库中的app的简要信息
	 * 
	 * @param appID
	 *            表示app的唯一标识号
	 * @return 返回json代码，发送给客户端
	 */
	public String getAppDetailInfo(String appID) {

		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "select * from db_app where AppID = '" + appID + "'";

			ResultSet res = state.executeQuery(sql);

			res.last();

			int resCount = res.getRow();

			if (resCount == 0) {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NULL);
			} else {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);

				res.beforeFirst();
				res.next();

				String appName = res.getString("AppName");
				String appDownCount = res.getString("AppDownCount");
				String appSize = res.getString("AppSize");
				String appDetailInfo1 = res.getString("AppDetailInfo1");
				String appDetailInfo2 = res.getString("AppDetailInfo2");
				String appGrade = res.getString("AppGrade");
				String appExtraInfo = res.getString("AppExtralInfo");
				String appAddress = res.getString("AppAdd");
				String appPhotoAdds = res.getString("AppPhotoAdds");
				String appUpdateTime = res.getString("AppUpdateTime");

				// 注意这个
				String appAuthorID = res.getString("AppAuthorID");

				String appVersion = res.getString("AppVersion");

				String appIconAdd = res.getString("AppIconAdd");

				state.close();
				res.close();

				state = con.createStatement();

				sql = "select * from db_author where AppAuthorID = '"
						+ appAuthorID + "'";

				res = state.executeQuery(sql);
				res.beforeFirst();
				res.next();

				String authorName = res.getString("AuthorName");

				res.close();
				state.close();

				ArrayList<String> photosList = dealAppShootList(appPhotoAdds);

				ArrayList<String> extraList = dealAppExtra(appExtraInfo);

				json.addAppDetail(
						jo,
						new AppDetailBean(appID, StaticVar.CURRENT_URL
								+ appIconAdd, appName, appGrade, appSize,
								appDownCount, extraList.get(0), extraList
										.get(1), extraList.get(2), photosList,
								appDetailInfo1, appDetailInfo2, appUpdateTime,
								authorName, appVersion, StaticVar.CURRENT_URL
										+ appAddress));

				ArrayList<CommentBeans> commentList = new ArrayList<CommentBeans>();

				state = con.createStatement();

				sql = "select * from db_user_comment_app where AppID = '"
						+ appID + "'";

				res = state.executeQuery(sql);

				while (res.next()) {
					String id = res.getString("Id");
					String userid = res.getString("UserID");
					String Comment = res.getString("Comment");
					String comTime = res.getString("ComTime");
					String zanCount = res.getString("ZanCount");

					String usrName = queryUserName(con, userid);

					ArrayList<UserBean> usList = new ArrayList<UserBean>();

					Statement state1 = con.createStatement();
					String sql1 = "select * from db_comment_comment where CommentID = '"
							+ id + "'";
					ResultSet res1 = state1.executeQuery(sql1);

					while (res1.next()) {
						String userId = res1.getString("UserID");
						String comComment = res1.getString("CommentComment");

						String comUserName = queryUserName(con, userId);

						usList.add(new UserBean(userId, comUserName, comComment));
					}

					res1.close();
					state1.close();

					commentList.add(new CommentBeans(id, userid, usrName, null,
							zanCount, comTime, Comment, usList));
				}

				json.addAppComment(jo, commentList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	private ArrayList<String> dealAppShootList(String str) {
		ArrayList<String> list = new ArrayList<String>();

		String array[] = str.split("&&");

		for (int i = 0; i < array.length; i++) {
			list.add(StaticVar.CURRENT_URL + array[i]);
		}

		return list;
	}

	private ArrayList<String> dealAppExtra(String str) {
		ArrayList<String> list = new ArrayList<String>();

		if (str.contains("已经通过")) {
			list.add("1");
		} else {
			list.add("0");
		}

		if (str.contains("免费")) {
			list.add("1");
		} else {
			list.add("0");
		}

		if (str.contains("无广告")) {
			list.add("1");
		} else {
			list.add("0");
		}

		return list;
	}

	private String queryUserName(Connection con, String userid) {
		String userName = "";

		if (userid.equals("-1")) {
			userName = "匿名用户";
		} else {
			try {
				Statement state1 = con.createStatement();

				String sql1 = "select * from db_user where UserId = '" + userid
						+ "'";
				ResultSet res1 = state1.executeQuery(sql1);
				res1.next();
				userName = res1.getString("UserName");

				res1.close();
				state1.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userName;

	}

	public String commentBack(String uid, String cid, String content) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "insert into db_comment_comment(UserID,CommentID,CommentComment) values('"
					+ uid + "','" + cid + "','" + content + "')";

			int res = state.executeUpdate(sql);

			if (res == 0) {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
			} else {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();

	}

	public String comment(String uid, String aid, String content, String time) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "insert into db_user_comment_app(UserID,AppID,Comment,ComTime,ZanCount) values('"
					+ uid
					+ "','"
					+ aid
					+ "','"
					+ content
					+ "','"
					+ time
					+ "','0')";

			int res = state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			if (res == 0) {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
			} else {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);
			}

			ResultSet rs = state.getGeneratedKeys();
			if (rs.next()) {
				jo.put("cid", "" + rs.getInt(1));

				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();

	}

	public String zan(String cid) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			String sql = "update db_user_comment_app set ZanCount=ZanCount+1 where Id = '"
					+ cid + "'";

			int res = state.executeUpdate(sql);

			if (res == 0) {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
			} else {
				jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	public String getAllSort() {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		ArrayList<SortItemBean> list = new ArrayList<SortItemBean>();

		try {
			state = con.createStatement();

			String sql = "select * from db_types";

			ResultSet res = state.executeQuery(sql);

			while (res.next()) {
				String id = res.getString("AppTypeID");
				String name = res.getString("TypeName");
				String add = res.getString("TypeAdd");

				list.add(new SortItemBean(id, name, StaticVar.CURRENT_URL + add));
			}

			JSONArray ja = JSONArray.fromObject(list);

			jo.put("sorts", ja);
			jo.put(StaticVar.ERROR_LABEL, StaticVar.ERROR_NO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	public String getAppList(String typeID, String page) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		}

		try {
			state = con.createStatement();

			int pg = Integer.parseInt(page);

			String sql = "select * from db_app where AppTypeID = '" + typeID
					+ "' limit " + (pg - 1) + ",10";

			ResultSet res = state.executeQuery(sql);

			res.last();

			int resCount = res.getRow();

			if (resCount == 0) {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NULL);
			} else {
				json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);

				res.beforeFirst();

				while (res.next()) {
					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					json.addAppInfo(jo, new AppBriefBean(appID,
							StaticVar.CURRENT_URL + appIconAdd, appName,
							appDownCount, appSize, appBriefInfo,
							StaticVar.CURRENT_URL + appAddress));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jo.toString();
	}

	public String getHotApps() {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			ArrayList<AppBriefBean> hotList = this.getApps(con, 1);
			ArrayList<AppBriefBean> newList = this.getApps(con, 2);
			ArrayList<AppBriefBean> weekList = this.getApps(con, 3);
			ArrayList<AppBriefBean> gameList = this.getApps(con, 4);

			JSONArray hotJa = JSONArray.fromObject(hotList);
			JSONArray newJa = JSONArray.fromObject(newList);
			JSONArray weekJa = JSONArray.fromObject(weekList);
			JSONArray gameJa = JSONArray.fromObject(gameList);

			jo.put("hot", hotJa);
			jo.put("new", newJa);
			jo.put("week", weekJa);
			jo.put("game", gameJa);

		}

		return jo.toString();
	}

	private ArrayList<AppBriefBean> getApps(Connection con, int hotType) {
		Statement state = null;
		ArrayList<AppBriefBean> list = new ArrayList<AppBriefBean>();

		try {
			state = con.createStatement();

			String sql = "select * from db_app where  AppID in (select AppID from db_hot where HotType='"
					+ hotType + "')";

			ResultSet res = state.executeQuery(sql);

			res.last();

			int resCount = res.getRow();

			if (resCount == 0) {
				return list;
			} else {

				res.beforeFirst();

				int size;

				if (resCount > 5) {
					size = 5;
				} else {
					size = resCount;
				}

				for (int i = 0; i < size; i++) {
					res.next();

					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					list.add(new AppBriefBean(appID, StaticVar.CURRENT_URL
							+ appIconAdd, appName, appDownCount, appSize,
							appBriefInfo, StaticVar.CURRENT_URL + appAddress));

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public String getHotAppList(String type, String page) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				String sql = "select * from db_app where  AppID in (select AppID from db_hot where HotType='"
						+ type + "') limit " + page + ",10";

				ResultSet res = state.executeQuery(sql);

				ArrayList<AppBriefBean> list = new ArrayList<AppBriefBean>();

				while (res.next()) {

					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					list.add(new AppBriefBean(appID, StaticVar.CURRENT_URL
							+ appIconAdd, appName, appDownCount, appSize,
							appBriefInfo, StaticVar.CURRENT_URL + appAddress));

				}

				JSONArray ja = JSONArray.fromObject(list);

				jo.put(StaticVar.APPS_LABEL, ja);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

	public String getRecList() {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				String sql = "select * from db_keywords";

				ResultSet res = state.executeQuery(sql);

				ArrayList<String> list = new ArrayList<String>();

				while (res.next()) {

					String word = res.getString("word");

					list.add(word);

				}

				JSONArray ja = JSONArray.fromObject(list);

				jo.put("rec", ja);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

	public String getThemes() {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				String sql = "select * from db_themes";

				ResultSet res = state.executeQuery(sql);

				ArrayList<ThemeBean> list = new ArrayList<ThemeBean>();

				while (res.next()) {

					String tid = res.getString("ThemeID");
					String iadd = res.getString("ThemeImageAdd");
					String text = res.getString("ThemeText");

					list.add(new ThemeBean(tid, StaticVar.CURRENT_URL + iadd,
							text));

				}

				JSONArray ja = JSONArray.fromObject(list);

				jo.put("theme", ja);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

	public String getThemeAppList(String themeID, String page) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				String sql = "select * from db_app where AppID in (select AppID from db_theme_apps where ThemeID ='"
						+ themeID + "') limit " + page + ",10";

				ResultSet res = state.executeQuery(sql);

				ArrayList<AppBriefBean> list = new ArrayList<AppBriefBean>();

				while (res.next()) {

					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					list.add(new AppBriefBean(appID, StaticVar.CURRENT_URL
							+ appIconAdd, appName, appDownCount, appSize,
							appBriefInfo, StaticVar.CURRENT_URL + appAddress));

				}

				JSONArray ja = JSONArray.fromObject(list);

				jo.put(StaticVar.APPS_LABEL, ja);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

	public String getSearchApp(String keywords, String page) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				// Select major from tb1 where major regexp ‘^m’;
				String sql = "select * from db_app where AppName regexp '"
						+ keywords + "' limit " + page + ",10";

				ResultSet res = state.executeQuery(sql);

				ArrayList<AppBriefBean> list = new ArrayList<AppBriefBean>();

				while (res.next()) {

					String appID = res.getString("AppID");
					String appIconAdd = res.getString("AppIconAdd");
					String appName = res.getString("AppName");
					String appDownCount = res.getString("AppDownCount");
					String appSize = res.getString("AppSize");
					String appBriefInfo = res.getString("AppBriefInfo");
					String appAddress = res.getString("AppAdd");

					list.add(new AppBriefBean(appID, StaticVar.CURRENT_URL
							+ appIconAdd, appName, appDownCount, appSize,
							appBriefInfo, StaticVar.CURRENT_URL + appAddress));

				}

				JSONArray ja = JSONArray.fromObject(list);

				jo.put(StaticVar.APPS_LABEL, ja);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

	public String getSearchUpdate(String version) {
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				// Select major from tb1 where major regexp ‘^m’;
				String sql = "select * from db_version";

				ResultSet res = state.executeQuery(sql);

				res.next();

				String newVersion = res.getString("Version");
				String AppAdd = res.getString("AppAdd");

				if(newVersion.compareTo(version) > 0){
					jo.put("newVersion", "1");
					jo.put("version", newVersion);
					jo.put("AppAdd", StaticVar.CURRENT_URL+AppAdd);
				}else{
					jo.put("newVersion", "0");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}
	
	public String insertFeedBack(String content,String time){
		JSONObject jo = json.getNullJsonObject();

		Connection con = getConnection();

		Statement state = null;

		if (con == null) {
			jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_SQL);
			return jo.toString();
		} else {
			jo = json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);

			try {
				state = con.createStatement();

				// Select major from tb1 where major regexp ‘^m’;
				String sql = "insert into db_feedback(Content,Time) values('"+content+"','"+time+"')";

				int res = state.executeUpdate(sql);

				if(res == 0){
					jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_ERROR);
					
				}else{
					jo = json.setErrorInJsonObject(jo, StaticVar.ERROR_NO);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					state.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return jo.toString();
	}

}
