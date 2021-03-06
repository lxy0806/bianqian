package com.example.bianqian;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Cuns> array;
    MyDataBase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_bwlList);//主界面的listview
        imageButton = (ImageButton) findViewById(R.id.btnAdd);//主界面的添加按钮

        inflater = getLayoutInflater();

        mdb = new MyDataBase(this);
        array = mdb.getArray();
        MyAdapter adapter = new MyAdapter(inflater, array);
        lv.setAdapter(adapter);
        /*
         * 点击listView里面的item,用来修改日记
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                intent.putExtra("ids", array.get(position).getIds());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });


        /*
         * 长点后来判断是否删除数据
         */
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //AlertDialog,来判断是否删除日记。
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除")
                        .setMessage("是否删除笔记")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mdb.toDelete(array.get(position).getIds());
                                array = mdb.getArray();
                                MyAdapter adapter = new MyAdapter(inflater, array);
                                lv.setAdapter(adapter);
                            }
                        })
                        .create().show();
                return true;
            }
        });
        /*
         * 按钮点击事件，用来新建日记
         */
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.item2:
                this.finish();
//                break;
//            case R.id.item3:
//
//                break;
            default:
                break;
        }
        return true;
    }
}
