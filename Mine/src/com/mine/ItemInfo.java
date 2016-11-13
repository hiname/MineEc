package com.mine;

import java.util.HashMap;


public class ItemInfo {
	
	private ItemInfo(){
		int cntId = 0;
		addItem(cntId++, R.drawable.woodblock, "목재블럭", 200);
		addItem(cntId++, R.drawable.stoneblock, "석재블럭", 300);
		addItem(cntId++, R.drawable.goldblock, "황금블럭", 1000);
		addItem(cntId++, R.drawable.stick, "막대기", 300);
		addItem(cntId++, R.drawable.hammer, "망치", 400);
		addItem(cntId++, R.drawable.maul, "큰망치", 500);
		addItem(cntId++, R.drawable.mace, "메이스", 600);
		addItem(cntId++, R.drawable.spear, "창", 700);
		addItem(cntId++, R.drawable.morningstar, "모닝스타", 800);
	}
	
	private static class Singleton {
		private static final ItemInfo instance = new ItemInfo();
	}
	
	public static ItemInfo getInstance() {
		return Singleton.instance;
	}
	
	private class Item {
		private int resId;
		private String name;
		private int price;
		
		public Item(int resId, String name, int price) {
			this.resId = resId;
			this.name = name;
			this.price = price;	
		}
	}
	
	private HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();
	
	public void addItem(int id, int resId, String name, int price) {
		itemList.put(id, new Item(resId, name, price));
	}
	
	public int getListSize() {
		return itemList.size();
	}
	
	public int getResId(int id) {
		return itemList.get(id).resId;
	}
	
	public String getName(int id) {
		return itemList.get(id).name;
	}
	
	public int getPrice(int id) {
		return itemList.get(id).price;
	}
	
	public Item getItem(int id) {
		return itemList.get(id);
	}
	
}
