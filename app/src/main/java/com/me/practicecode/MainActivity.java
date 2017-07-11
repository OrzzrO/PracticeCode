package com.me.practicecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.me.practicecode.bezierview.BezierViewActivity;
import com.me.practicecode.realm.ui.RealmTestActivity;
import com.me.practicecode.rxjava.RxJavaTestActivity;
import com.me.practicecode.testview.TestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Map<String, Object>> data = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);
        Map<String,Object> itemData;

        itemData = new HashMap<>();
        itemData.put("text","BezierView");
        itemData.put("class", BezierViewActivity.class);
        data.add(itemData);

        itemData = new HashMap<>();
        itemData.put("text","testView");
        itemData.put("class", TestActivity.class);
        data.add(itemData);

        itemData = new HashMap<>();
        itemData.put("text","RxJavaTest");
        itemData.put("class", RxJavaTestActivity.class);
        data.add(itemData);

        itemData = new HashMap<>();
        itemData.put("text","Realm_Demo");
        itemData.put("class", RealmTestActivity.class);
        data.add(itemData);

        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, data, android.R.layout
                .simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1});
        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, (Class<?>) data.get(position).get("class"));
                startActivity(intent);
            }
        });
    }
}
