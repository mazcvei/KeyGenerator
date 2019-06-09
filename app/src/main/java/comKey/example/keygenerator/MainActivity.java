package comKey.example.keygenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keygenerator.R;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class MainActivity extends AppCompatActivity {

    Switch sw1,sw2,sw3,sw4;
    final char nums[]={'1','2','3','4','5','6','7','8','9','0'};
    final char letters_min[]={'a','b','c','d','e','f','g','h','i','j','k','l','n','ñ','o','p',
                                'q','r','s','t','u','v','w','x','y','z'};
    final char characters[]={'|','@','#','~','€','¬','[',']','{','}','º','ª','!','"','·','$',
            '%','&','/','(',')','=','?','¿','^','*','¨','Ç','_',':',';','>','/','*','-','+'};
    private int longitud_clave=0;
    private SeekBar seek;
    private TextView tv_longitud,tv_clave,tv_security;
    private Button bt_generar,btn_porta;

    private PublisherAdView mPublisherAdView;
    private PublisherInterstitialAd mPublisherInterstitialAd;

    private  int interstitial_count=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BannerAD
        mPublisherAdView = findViewById(R.id.ad_banner);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);
        //InterstitialAD
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-4306575227581951/7477829995");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        //
        sw1=findViewById(R.id.sw1);
        sw2=findViewById(R.id.sw2);
        sw3=findViewById(R.id.sw3);
        sw4=findViewById(R.id.sw4);
        btn_porta=findViewById(R.id.btn_porta);
        tv_longitud=findViewById(R.id.tv_longitud);
        tv_clave=findViewById(R.id.tv_clave);
        tv_security=findViewById(R.id.tv_security);
        bt_generar=findViewById(R.id.bt_generar);
        seek=findViewById(R.id.seekBar);
        bt_generar.setEnabled(false);
        tv_longitud.setText(R.string.txt_longitud+ longitud_clave);

        btn_porta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_clave.getText().toString()==""){
                    Toast toast=  Toast.makeText(getApplicationContext(),R.string.toast_noclip,Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if(interstitial_count==3){
                    if (mPublisherInterstitialAd.isLoaded()) {
                        mPublisherInterstitialAd.show();
                        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
                        interstitial_count=0;
                    }
                    interstitial_count=0;
                }else{interstitial_count++;}

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  tv_clave.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast toast=  Toast.makeText(getApplicationContext(),R.string.toast_clip,Toast.LENGTH_LONG);
                toast.show();
            }
        });
        bt_generar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                tv_clave.setText(generate());
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                longitud_clave = progress;

                if(longitud_clave>0){
                    bt_generar.setEnabled(true);
                }else{
                    bt_generar.setEnabled(false);

                }
                tv_longitud.setText("Longitud de la clave: " + longitud_clave);
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

    }
    public String generate() {
        int tipo_caracter = 0;
        String clave = "";
        for (int i = 0; i < longitud_clave; i++) {
            if (!sw1.isChecked() && !sw2.isChecked() && !sw3.isChecked() && !sw4.isChecked()) {

                break;
            }else if (sw1.isChecked() && sw2.isChecked() && sw3.isChecked() && sw4.isChecked()) {
                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec3);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));
                }else{
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                tipo_caracter = (int) (Math.random() * 4);
            } else if (sw1.isChecked() && sw2.isChecked() && sw3.isChecked()) {
                if(longitud_clave>=15){
                    tv_security.setText(R.string.sec3);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));
                }else{
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                tipo_caracter = (int) (Math.random() * 3);
            } else if (sw1.isChecked() && sw2.isChecked() && sw4.isChecked()) {

                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec3);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));
                }else{
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 2);
            } else if (sw2.isChecked() && sw3.isChecked() && sw4.isChecked()) {

                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec3);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));
                }else{
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 0);
            } else if (sw1.isChecked() && sw3.isChecked() && sw4.isChecked()) {
                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec3);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));
                }else{
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 1);
            } else if (sw1.isChecked() && sw2.isChecked()) {
                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tipo_caracter = (int) (Math.random() * 2);
            } else if (sw1.isChecked() && sw3.isChecked()) {
                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 1 || tipo_caracter == 3);

            } else if (sw1.isChecked() && sw4.isChecked()) {
                if(longitud_clave>=50){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));}
                else if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else {
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 1 || tipo_caracter == 2);

            } else if (sw2.isChecked() && sw3.isChecked()) {
                if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 0 || tipo_caracter == 3);

            } else if (sw2.isChecked() && sw4.isChecked()) {
                if(longitud_clave>=50){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));}
                else if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 0 || tipo_caracter == 2);

            } else if (sw3.isChecked() && sw4.isChecked()) {
                if(longitud_clave>=50){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.green));}
                else if(longitud_clave>=10){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                do {
                    tipo_caracter = (int) (Math.random() * 4);
                } while (tipo_caracter == 0 || tipo_caracter == 1);

            } else if (sw1.isChecked()) {
                if(longitud_clave>=20){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tipo_caracter = 0;
            } else if (sw2.isChecked()) {
                if(longitud_clave>=20){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tipo_caracter = 1;
            } else if (sw3.isChecked()) {
                if(longitud_clave>=20){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tipo_caracter = 2;
            } else if (sw4.isChecked()) {
                if(longitud_clave>=20){
                    tv_security.setText(R.string.sec2);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.yellow));
                }else{
                    tv_security.setText(R.string.sec1);
                    tv_security.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tipo_caracter = 3;
            }

            switch (tipo_caracter) {
                case 0:
                    int caracter_num = (int) (Math.random() * nums.length);
                    if (caracter_num <= nums.length) {
                        clave += nums[caracter_num];
                    }

                    break;
                case 1:
                    int caracter_min = (int) (Math.random() * letters_min.length);
                    if (caracter_min <= letters_min.length) {
                        clave += letters_min[caracter_min];
                    }

                    break;
                case 2:
                    int caracter_may = (int) (Math.random() * letters_min.length);
                    if (caracter_may <= letters_min.length) {
                        clave += (char) (letters_min[caracter_may] - 32);//convertir a mayuscula
                    }

                    break;
                case 3:
                    int caracter_exp = (int) (Math.random() * characters.length);
                    if (caracter_exp <= characters.length) {
                        clave += characters[caracter_exp];
                    }
                    break;
            }//switch

        }//for
        return clave;
    }
}
