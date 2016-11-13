package com.mine;

import java.util.Arrays;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by USER on 2016-11-09.
 */
public class ActInven extends Activity {
		
    DataMgr dataMgr;
    ItemInfo itemInfo = ItemInfo.getInstance(); 
    static int invenSize = 9;
    Dialog dlg;
    ImageView ivPopupIcon;
    Button btnPopupSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("d", "onCreate");
        setContentView(R.layout.act_inven);

        dataMgr = DataMgr.getInstance(this);
        dlg = new Dialog(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ivPopupIcon = new ImageView(this);
        // iv.setImageResource(R.mipmap.ic_launcher);
        btnPopupSell = new Button(this);
        btnPopupSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellItem();
                dlg.dismiss();
            }
        });

        ll.addView(ivPopupIcon);
        ll.addView(btnPopupSell);
        dlg.setContentView(ll);

        CheckBox cbFastSell = (CheckBox) findViewById(R.id.cbFastSell);
        cbFastSell.setChecked(dataMgr.getFastSell());
        cbFastSell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataMgr.setFastSell(isChecked);
            }
        });

        updateInven();
        setOnClickListener();

    }

    public void sellItem() {
        int sellItemId = dataMgr.sellMyItem(lastSelIdx);
        if (sellItemId != -1) {
        	String sellItemName = itemInfo.getName(sellItemId);
        	int sellItemPrice = itemInfo.getPrice(sellItemId);
        	String sellMsg = sellItemName + " 판매 " + sellItemPrice + "원 획득";
            Toast.makeText(ActInven.this, sellMsg, Toast.LENGTH_SHORT).show();
            ActMain.tvSystemMsg.setText(ActMain.tvSystemMsg.getText().toString() + sellMsg + "\n");
        }
        updateInven();
    }

    int lastSelIdx = -1;

    public void setOnClickListener() {
        LinearLayout llInven = (LinearLayout) findViewById(R.id.llInven);
        int itemCnt = 0;
        for (int i = 0; i < llInven.getChildCount(); i++) {
            LinearLayout llChild = (LinearLayout) llInven.getChildAt(i);
            for (int j = 0; j < llChild.getChildCount(); j++) {
                final int selIdx = itemCnt;
                ((ImageView) llChild.getChildAt(j)).setOnClickListener(new View.OnClickListener() {
                	@Override
                    public void onClick(View v) {
                    	lastSelIdx = selIdx;
                        if (dataMgr.getFastSell()) {
                            sellItem();
                        } else {
                            int getItemId = dataMgr.getMyItemId(selIdx);
                            if (getItemId == -1) return;
                            // ivPopupIcon.setImageResource(myInvenResId[getItemId]);
                            // btnPopupSell.setText(myItemName[getItemId] + " 판매");
                            ivPopupIcon.setImageResource(itemInfo.getResId(getItemId));
                            btnPopupSell.setText(itemInfo.getName(getItemId) + " 판매");
                            
                            dlg.show();
                        }
                    }
                });
                itemCnt++;
            }
        }
    }

    public void updateInven() {
        clearInven();
        LinearLayout llInven = (LinearLayout) findViewById(R.id.llInven);
        int itemCnt = 0;
        //
        // itemList = itemList.replace("[\"\"]", "").replace("]", "").replaceAll(", ", "");
        String itemList[] = dataMgr.getMyInvenItemPack().split(",");
        Log.d("d", "Arrays.toString(itemList) : " + Arrays.toString(itemList));
        if (itemList != null && itemList.length > 0 && itemList[0].length() > 0)
            loop:
                    for (int i = 0; i < llInven.getChildCount(); i++) {
                        LinearLayout llChild = (LinearLayout) llInven.getChildAt(i);
                        for (int j = 0; j < llChild.getChildCount(); j++) {
                            ImageView ivItem = (ImageView) llChild.getChildAt(j);

                            int getItemId = Integer.parseInt(itemList[itemCnt]);
                            Log.d("d", "getItemId : " + getItemId);
                            ivItem.setImageResource(itemInfo.getResId(getItemId));
                            itemCnt++;
                            if (itemCnt >= itemList.length)
                                break loop;
                        }
                    }
    }

    public void clearInven() {
        LinearLayout llInven = (LinearLayout) findViewById(R.id.llInven);
        for (int i = 0; i < llInven.getChildCount(); i++) {
            LinearLayout llChild = (LinearLayout) llInven.getChildAt(i);
            for (int j = 0; j < llChild.getChildCount(); j++)
                ((ImageView) llChild.getChildAt(j)).setImageResource(0);
        }
    }

}
