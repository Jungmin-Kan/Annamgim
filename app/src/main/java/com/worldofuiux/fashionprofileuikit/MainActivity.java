package com.worldofuiux.fashionprofileuikit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.worldofuiux.fashionprofileuikit.Block.AsyncblockGetData;
import com.worldofuiux.fashionprofileuikit.Block.Asyncblockpost;
import com.worldofuiux.fashionprofileuikit.Block.BlockValueobject;
import com.worldofuiux.fashionprofileuikit.Map.MapActivity;
import com.worldofuiux.fashionprofileuikit.NFC.NFCactivity;
import com.worldofuiux.fashionprofileuikit.adapter.CollectionAdapter;
import com.worldofuiux.fashionprofileuikit.adapter.CollectionAdapterPay;
import com.worldofuiux.fashionprofileuikit.model.collection;
import com.worldofuiux.fashionprofileuikit.model.collectionpay;

import com.worldofuiux.fashionprofileuikit.Block.Asyncblockpost;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    private Activity activity = MainActivity.this;

    private Intent intent;
    private String name;
    private ArrayList<collectionpay> collectionListpay; // 리싸이클 뷰 목록 종류
    private RecyclerView recyclerViewCollectionpay; //  슬라이드 박스(내용물 담을 통)
    private CollectionAdapterPay collectionAdapterPay; // 이벤트 리스너, 및 내용물 등록 어댑터

    private ArrayList<String> title = new ArrayList<String>(); //날짜,시간
    private ArrayList<String> description = new ArrayList<String>(); // 결재내역



    private ArrayList<collection> collectionList2; // 리싸이클 뷰 목록 종류
    private RecyclerView recyclerViewCollection2; //  슬라이드 박스(내용물 담을 통)
    private CollectionAdapter collectionAdapter2; // 이벤트 리스너, 및 내용물 등록 어댑터

    private Integer image2[] = {R.drawable.hyundai,R.drawable.kb,R.drawable.visa}; // 카드사진
    private String title2[] = {"현대카드", "국민카드", "비자카드"}; // 중간 (카드이름)
    private String description2[] = {"8001023125", "1001023127", "2001023129"}; //하단(카드번호)

    private ArrayList<BlockValueobject> blockValueobjects = new ArrayList<BlockValueobject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Asyncblockpost asyncblockpost = new Asyncblockpost();
            asyncblockpost.execute();
            blockValueobjects = new AsyncblockGetData().execute().get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        intent = getIntent();
        name = intent.getExtras().getString("name");
        if(name.equals("")){ name = "default"; }
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText(name);

        intent = new Intent(MainActivity.this, NFCactivity.class);
        intent.putExtra("name", name);
        intent.putExtra("how", true);

        for(int x=0;x<blockValueobjects.size();x++){
            if(blockValueobjects.get(x).getNewUser().equals(name)){
                title.add(blockValueobjects.get(x).getTimestamp().toString());
                description.add(blockValueobjects.get(x).getNewPrice().toString());
            }
        }

        TextView tvAddress = (TextView) findViewById(R.id.tvaddress);
        Shader myShader = new LinearGradient(100, 0, 0, 80, Color.parseColor("#00d4e7"), Color.parseColor("#448bde"), Shader.TileMode.CLAMP);
        tvAddress.getPaint().setShader(myShader);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

      /**결제 내역*/
        recyclerViewCollectionpay = findViewById(R.id.RecyclerViewCollection);
        recyclerViewCollectionpay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCollectionpay.setItemAnimator(new DefaultItemAnimator());
        collectionListpay = new ArrayList<>();

        for (int x = 0; x < title.size(); x++) {
            collectionpay collectionpay = new collectionpay(title.get(x), description.get(x));
            collectionListpay.add(collectionpay);
        }
        collectionAdapterPay = new CollectionAdapterPay(mContext, collectionListpay);
        recyclerViewCollectionpay.setAdapter(collectionAdapterPay);

        /**결제할 카드목록*/
        recyclerViewCollection2 = findViewById(R.id.RecyclerViewCollection2);
        recyclerViewCollection2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCollection2.setItemAnimator(new DefaultItemAnimator());
        collectionList2 = new ArrayList<>();
        for (int i = 0; i < title2.length; i++) {
            collection collection2 = new collection(title2[i], description2[i],image2[i], i);
            collectionList2.add(collection2);
        }
        collectionAdapter2 = new CollectionAdapter(mContext, collectionList2, intent, activity);
        recyclerViewCollection2.setAdapter(collectionAdapter2);


        ((Switch) findViewById(R.id.a)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) { Toast.makeText(getApplicationContext(), "결제가능", Toast.LENGTH_SHORT).show(); collectionAdapter2.setPay(true); }
                else { Toast.makeText(getApplicationContext(), "결제불가", Toast.LENGTH_SHORT).show();collectionAdapter2.setPay(false); }
            }
        });
        findViewById(R.id.Map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void MakeTransaction(){ ProcessBuilder processBuilder = new ProcessBuilder(); }

}
