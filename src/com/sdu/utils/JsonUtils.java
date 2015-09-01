package com.sdu.utils;

import java.util.ArrayList;

import com.sdu.beans.AdBean;
import com.sdu.beans.AppBriefBean;
import com.sdu.beans.AppDetailBean;
import com.sdu.beans.CommentBeans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {

	public JSONObject getNullJsonObject() {
		JSONObject jo = new JSONObject();

		return jo;
	}

	public JSONObject getJsonObjectWithError(int error) {
		JSONObject jo = new JSONObject();

		jo.put(StaticVar.ERROR_LABEL, error);

		return jo;
	}

	public JSONObject addErrorToJsonObject(JSONObject jo, int error) {
		jo.put(StaticVar.ERROR_LABEL, error);

		return jo;
	}

	public JSONObject setErrorInJsonObject(JSONObject jo, int error) {
		
		if(jo.containsKey(StaticVar.ERROR_LABEL)){
			jo.remove(StaticVar.ERROR_LABEL);
			jo.put(StaticVar.ERROR_LABEL, error);
		}else{
			jo.put(StaticVar.ERROR_LABEL, error);
		}
		

		return jo;
	}

	public JSONObject addAppInfo(JSONObject jo, AppBriefBean info) {

		JSONObject temp = new JSONObject();
		
		temp.put("appID", info.getAppID());
		temp.put("appIconAdd", info.getAppIconAdd());
		temp.put("appName", info.getAppName());
		temp.put("appDownCount", info.getAppDownCount());
		temp.put("appSize", info.getAppSize());
		temp.put("briefInfo", info.getBriefInfo());
		temp.put("appAddress", info.getAppAddress());
		
		if (jo.containsKey(StaticVar.APPS_LABEL)) {

			JSONArray ja = jo.getJSONArray(StaticVar.APPS_LABEL);

			ja.add(temp);
			jo.put(StaticVar.APPS_LABEL, ja);

		} else {
			JSONArray ja = new JSONArray();
			ja.add(temp);

			jo.put(StaticVar.APPS_LABEL, ja);

		}

		return jo;

	}
	
	public JSONObject addAdverInfo(JSONObject jo, AdBean ab) {

		JSONObject temp = new JSONObject();
		
		temp.put("appID",ab.getAppID());
		temp.put("imageUrl", ab.getImageUrl());
		
		if (jo.containsKey(StaticVar.AD_LABEL)) {

			JSONArray ja = jo.getJSONArray(StaticVar.AD_LABEL);

			ja.add(temp);
			jo.put(StaticVar.AD_LABEL, ja);

		} else {
			JSONArray ja = new JSONArray();
			ja.add(temp);

			jo.put(StaticVar.AD_LABEL, ja);

		}

		return jo;

	}
	
	
	public JSONObject addAppDetail(JSONObject jo,AppDetailBean adb){
		
		JSONObject obj = JSONObject.fromObject(adb);
		
		jo.put("appInfo", obj);
		
		return jo;
	}
	
	public JSONObject addAppComment(JSONObject jo,ArrayList<CommentBeans> commentList){
		
		JSONArray array = JSONArray.fromObject(commentList);
		jo.put("comment", array);
		
		return jo;
	}
}
