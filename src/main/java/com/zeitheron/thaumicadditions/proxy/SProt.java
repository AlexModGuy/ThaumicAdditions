package com.zeitheron.thaumicadditions.proxy;

import com.pengu.hammercore.common.utils.IOUtils;
import com.pengu.hammercore.json.JSONArray;
import com.pengu.hammercore.json.JSONException;
import com.pengu.hammercore.json.JSONObject;
import com.zeitheron.thaumicadditions.TAReconstructed;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class SProt
{
	public static final String BUILD_COPY = "bc-270518";
	
	@SideOnly(Side.CLIENT)
	public static String playErrClient()
	{
		try
		{
			JSONObject jobj = (JSONObject) IOUtils.downloadjson("https://pastebin.com/raw/uxEkprBU");
			Session se = Minecraft.getMinecraft().getSession();
			JSONArray jarr = jobj.optJSONObject(BUILD_COPY).getJSONArray("players");
			if(jarr != null)
				return jarr.join(",").contains(se.getUsername()) ? null : "You are not in ThaumicAdditions BETA whitelist! Please contact `Zeitheron#2999` on discord for entrusting your minecraft user!";
			else
				return "This build is outdated! You MUST request new verstion of ThaumicAdditions";
		} catch(JSONException e)
		{
			return "Remote server returned invalid JSON response! Please contact `Zeitheron#2999` on discord!";
		}
	}

	@SideOnly(Side.SERVER)
	public static String playErrServer()
	{
		try
		{
			JSONObject jobj = (JSONObject) IOUtils.downloadjson("https://pastebin.com/raw/uxEkprBU");
			JSONArray jarr = jobj.optJSONObject(BUILD_COPY).getJSONArray("servers");
			if(jarr != null)
				return jarr.join(",").contains(new String(IOUtils.downloadData("http://checkip.amazonaws.com"))) ? null : "Your server is not in ThaumicAdditions BETA whitelist! Please contact `Zeitheron#2999` on discord for entrusting your server!";
			else
				return "This build is outdated! You MUST request new verstion of ThaumicAdditions";
		} catch(JSONException e)
		{
			return "Remote server returned invalid JSON response! Please contact `Zeitheron#2999` on discord!";
		}
	}
	
	public static String playErr()
	{
		return TAReconstructed.proxy.getClass() == CommonProxy.class ? playErrServer() : playErrClient();
	}
}