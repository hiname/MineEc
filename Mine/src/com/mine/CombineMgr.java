package com.mine;

import java.util.ArrayList;
import java.util.Collections;

import android.util.Log;


public class CombineMgr {
	private ItemInfo itemInfo = ItemInfo.getInstance();
	private MyItemList myItemList = MyItemList.getInstance();
	
	private CombineMgr() {}
	
	private static class Singleton {
		static CombineMgr instance = new CombineMgr();
	}
	
	public static CombineMgr getInstance() {
		return Singleton.instance;
	}
	
}
