package comn.sdu.test;

import com.sdu.utils.JsonUtils;
import com.sdu.utils.StaticVar;

import net.sf.json.JSONObject;

public class Main {

	public static void main(String[] args) {

		JsonUtils json = new JsonUtils();
		
		JSONObject jo = json.getNullJsonObject();
		json.addErrorToJsonObject(jo, StaticVar.ERROR_NO);
		
		System.out.println(jo.toString());
	}

}
