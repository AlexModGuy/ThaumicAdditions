package com.zeitheron.thaumicadditions;

import com.pengu.hammercore.common.utils.IOUtils;
import com.pengu.hammercore.json.JSONArray;
import com.pengu.hammercore.json.JSONException;
import com.pengu.hammercore.json.JSONObject;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SProt
{
	public static final String BUILD_COPY = "bc-2105";
	
	public static String playErr()
	{
		try
		{
			JSONObject jobj = (JSONObject) IOUtils.downloadjson("https://pastebin.com/raw/uxEkprBU");
			Session se = Minecraft.getMinecraft().getSession();
			JSONArray jarr = jobj.optJSONArray(BUILD_COPY);
			if(jarr != null)
				return jarr.join(",").contains(se.getUsername()) ? null : "You are not in ThaumicAdditions BETA whitelist!";
			else
				return "This build is outdated! You MUST request new verstion of ThaumicAdditions";
		} catch(JSONException e)
		{
			return "Remote server returned invalid JSON response! Please contact `Zeitheron#2999` on discord!";
		}
	}
}