package com.worldofuiux.fashionprofileuikit.Auth;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.worldofuiux.fashionprofileuikit.Block.AsyncblockGetData;
import com.worldofuiux.fashionprofileuikit.Block.Asyncblockpost;
import com.worldofuiux.fashionprofileuikit.MainActivity;
import com.worldofuiux.fashionprofileuikit.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AuthActivity extends AppCompatActivity {

    private ImageView iv_fingerprint;
    private TextView tv_message;
    private static final String KEY_NAME = "example_key";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    private boolean start;

    @TargetApi(Build.VERSION_CODES.M)
    public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
        CancellationSignal cancellationSignal;
        private Context context;

        public FingerprintHandler(Context context) {
            this.context = context;
        }
        public void startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject,boolean start) {
            cancellationSignal = new CancellationSignal();
            fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) { this.update("인증 에러 발생" + errString, false); }

        @Override
        public void onAuthenticationFailed() {
            this.update("인증 실패", false);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) { this.update("Error: " + helpString, false); }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) { this.update("앱 접근이 허용되었습니다.", true); }

        public void stopFingerAuth() { if (cancellationSignal != null && !cancellationSignal.isCanceled()) { cancellationSignal.cancel(); } }

        private void update(String s, boolean b) {
            tv_message = (TextView) ((Activity)context).findViewById(R.id.tv_message);
            iv_fingerprint = (ImageView) findViewById(R.id.iv_fingerprint);
            //안내 메세지 출력
            tv_message.setText(s);
            if(b == false){
                tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                iv_fingerprint.setBackgroundDrawable(getResources().getDrawable(R.drawable.finger_foreground_red));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_fingerprint.setBackgroundDrawable(getResources().getDrawable(R.drawable.finger_foreground));
                    }
                }, 1000);
            } else {//지문인증 성공
                start = true;
                iv_fingerprint.setBackgroundDrawable(getResources().getDrawable(R.drawable.finger_foreground_green));
                tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

                //sound effect
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone((Activity)context, notification);
                ringtone.play();

                if(start==true){
                    EditText editText;
                    editText = (EditText) findViewById(R.id.getNmae);
                    String name = editText.getText().toString();

                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_message.setText("앱이 시작되었습니다.");
//        Toast.makeText(getApplicationContext(),getPhone().toString(),Toast.LENGTH_SHORT).show();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            //Manifest에 Fingerprint 퍼미션을 추가해 워야 사용가능
            if(!fingerprintManager.isHardwareDetected()){
                tv_message.setText("지문을 사용할 수 없는 디바이스 입니다.");
            } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                tv_message.setText("지문사용을 허용해 주세요.");
                /*잠금화면 상태를 체크한다.*/
            } else if(!keyguardManager.isKeyguardSecure()){
                tv_message.setText("잠금화면을 설정해 주세요.");
            } else if(!fingerprintManager.hasEnrolledFingerprints()){
                tv_message.setText("등록된 지문이 없습니다.");
            } else {//모든 관문을 성공적으로 통과(지문인식을 지원하고 지문 사용이 허용되어 있고 잠금화면이 설정되었고 지문이 등록되어 있을때)
                tv_message.setText("손가락을 홈버튼에 대 주세요.");
                generateKey();
                if(cipherInit()){
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    //핸들러실행
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                    fingerprintHandler.startAutho(fingerprintManager, cryptoObject,start);
                }
            }
        }
    }

    //Cipher Init() 암호초기화
    public boolean cipherInit(){
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    //Key Generator 키 발생기
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey() {
        try { keyStore = KeyStore.getInstance("AndroidKeyStore"); } catch (Exception e) { e.printStackTrace(); }
        try { keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"); } catch (NoSuchAlgorithmException | NoSuchProviderException e) { throw new RuntimeException("Failed to get KeyGenerator instance", e); }
        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }

//    private String getPhone() {
//        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return "";
//        }
//        return phoneMgr.getLine1Number();
//    }


}
