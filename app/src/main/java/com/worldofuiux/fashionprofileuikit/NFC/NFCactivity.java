package com.worldofuiux.fashionprofileuikit.NFC;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.worldofuiux.fashionprofileuikit.R;

import java.nio.charset.Charset;
import java.util.Locale;

public class NFCactivity extends AppCompatActivity {

    private TextView text;
    /**
     * NFC 통신 관련 변수
     */
    private NfcAdapter nfcAdapter; //  안드로이드 단말기의 NFC 정보를 가져오는 변수타입
    private NdefMessage mNdeMessage; // NFC로 전송할 데이터를 저장하는 변수
    private Intent intent;
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nfc);
        text = (TextView) findViewById(R.id.text);

        intent = getIntent();
        String name = intent.getExtras().getString("name");
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText(name);

        boolean how = intent.getExtras().getBoolean("how");
        TextView textView1 = (TextView) findViewById(R.id.bool);
        textView1.setText(String.valueOf(how));

        String card = intent.getExtras().getString("card");
        TextView textView2 = (TextView) findViewById(R.id.card);
        textView2.setText(card);

        String date = intent.getExtras().getString("date");
        TextView textView3 = (TextView) findViewById(R.id.date);
        textView3.setText(date);

        /** 2. NFC 단말기 정보 가져오기*/
        nfcAdapter = NfcAdapter.getDefaultAdapter(this); // nfc를 지원하지않는 단말기에서는 null을 반환.
        if (nfcAdapter != null) {
            text.setText("NFC 단말기를 접촉해주세요>>  " + nfcAdapter + "");
        } else {
            text.setText("NFC 기능이 꺼져있습니다. 켜주세요>>  " + nfcAdapter + "");
        }

        /** 3. NdefMessage 타입 mNdeMessage 변수에 NFC 단말기에 보낼 정보를 넣는다.*/
        mNdeMessage = new NdefMessage(
                new NdefRecord[]{
                        createNewTextRecord("전달하는 기기: " + nfcAdapter.toString(), Locale.ENGLISH, true),
                        createNewTextRecord("사용자: " + name, Locale.ENGLISH, true),
                        createNewTextRecord("허용여부: " + String.valueOf(how), Locale.ENGLISH, true),
                        createNewTextRecord("카드타입: " + String.valueOf(card), Locale.ENGLISH, true),
                        createNewTextRecord("날짜: " + String.valueOf(date), Locale.ENGLISH, true)
                }
        );
        findViewById(R.id.onBackPressed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    /**
     * 액티비티 화면이 나오기 전에 실행되는 메소드이다.* onCreate 에서 정한 mNdeMessage 의 데이터를 NFC 단말기에 전송한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume 호출", Toast.LENGTH_SHORT).show();
        //sound effect

        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundNdefPush(this, mNdeMessage);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //sound effect
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    ringtone.play();
                }
            }, 1800);
        }
    }


    /**
     * 액티비티 화면이 종료되면 NFC 데이터 전송을 중단하기 위해 실행되는 메소드이다.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause 호출", Toast.LENGTH_SHORT).show();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundNdefPush(this);
        }
    }

    /** 텍스트 형식의 데이터를 mNdeMessage 변수에 넣을 수 있도록 변환해 주는 메소드이다.*/
    /**
     * createNewTextRecord(["전달하는 기기 : "+nfcAdapter.toString()], [Locale.ENGLISH], [true]),
     */
    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodelnUtf8) {

        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII")); // 랭귀지 기준 아스키코드 설정
        Charset utfEncoding = encodelnUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16"); // 인코딩 설정

        byte[] textBytes = text.getBytes(utfEncoding); // 텍스트 바이트단위 변환
        int utfBit = encodelnUtf8 ? 0 : (1 << 7); //
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

}