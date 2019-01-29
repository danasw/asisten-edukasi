package jogja.telkom.edukasiwhatsapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String waktu;
    RadioGroup radioGroup;
    RadioButton radioPoll;
    Button submit;
    RadioButton radioCustom;
    EditText textCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText textNumber     = (EditText) findViewById(R.id.phone_number);
        final EditText textNama       = (EditText)findViewById(R.id.nama_pelanggan);
        final EditText textTiket      = (EditText)findViewById(R.id.nomer_tiket);
        final EditText textLayanan    = (EditText)findViewById(R.id.nomer_layanan);
        textCustom                    = (EditText)findViewById(R.id.custom_chat);

        radioCustom             = findViewById(R.id.radio_custom);
        radioGroup              = findViewById(R.id.radio_group);
        submit                  = findViewById(R.id.submit);


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 10){
            waktu = "pagi";
        }else if(timeOfDay >= 10 && timeOfDay < 15){
            waktu = "siang";
        }else if(timeOfDay >= 15 && timeOfDay < 18){
            waktu = "sore";
        }

        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                String number                  = textNumber.getText().toString();
                String name                    = textNama.getText().toString();
                String tiket                   = textTiket.getText().toString();
                String nomerLayanan            = textLayanan.getText().toString();
                String pesanCustom             = textCustom.getText().toString();

                if( TextUtils.isEmpty(textNama.getText())){
                    textNama.setError( "Isikan Nama" );
                }
                else if( TextUtils.isEmpty(textNumber.getText())){
                    textNumber.setError( "Isikan Nomer Telepon" );
                }
                else if( TextUtils.isEmpty(textTiket.getText())){
                    textTiket.setError( "Isikan Nomer Tiker" );
                }
                else if( TextUtils.isEmpty(textLayanan.getText())){
                    textLayanan.setError( "Isikan Nomer Layanan Pelanggan" );
                }
                else {
                    if(radioGroup.getCheckedRadioButtonId() != -1) {
                        // get selected radio button from radioGroup
                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioPoll = (RadioButton) findViewById(selectedId);

                        if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Cabut Usee TV")) {
                            sendEdukasiCabutUseeInet(number, waktu, name, tiket, nomerLayanan);
                        }
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Edukasi Cabut Internet dan Usee TV")) {
                            sendEdukasiCabutUsee(number, waktu, name, tiket, nomerLayanan);
                        }
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Share Location")){
                            sendEdukasiShareLocation(number,waktu,name,tiket,nomerLayanan);
                        }
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Isolir Sementara")){
                            sendEdukasiIsolirSementara(number,waktu,name,tiket,nomerLayanan);
                        }
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Custom Chat")){
                            if (TextUtils.isEmpty(textCustom.getText())){
                                textCustom.setError("ISI INTI DARI PESAN");
                            }
                            else {
                            sendEdukasiCustom(number,waktu,name,tiket,nomerLayanan,pesanCustom);
                            }
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "ISI JENIS EDUKASI", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void sendEdukasiCabutUsee(String number , String waktu, String name , String tiket, String nomerLayanan) {
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan
                + " untuk Cabut UseeTv sudah kami proses melalui sistem. \n\n"
                + "Untuk pencabutan UseeTV dimohon ke PlasaTelkom, Mohon dibantu dengan membawa :\n"
                + "perangkat STB dan remote,\n" +
                "FC KTP ,\n" +
                "dan materai 6ribu.\n" +
                "Karena ada berkas yang harus ditandatangani.\n\n"
                + "Dan untuk informasi, bahwa nanti" + " " + name
                + " tidak perlu mengantri lagi. Ketika sudah di plasa telkom sampaikan saja ke satpam di depan bahwa ini untuk pengembalian perangkat terkait cabut useetv.\n\n" +
                "Mohon maaf"+ " " + name + " sekiranya kapan bisa datang ke plasa telkom? untuk kami catatkan dinotifikasi kami. \n\n"
                +"Demikian informasi yang dapat kami sampaikan.Kami mohon izin untuk menutup laporannya karena kami sudah tindak lanjuti.\n" +
                "Terimakasih";
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEdukasiCabutUseeInet(String number , String waktu, String name , String tiket, String nomerLayanan) {
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone="
                    + number
                    + "&text="
                    + "Selamat "
                    + waktu
                    + " "
                    + name
                    + "."
                    + "Kami dari Telkom. Maaf mengganggu waktunya."
                    + "Terkait dengan laporan dinomer tiket "
                    + tiket
                    + " dengan nomer layanan" + " "
                    + nomerLayanan
                    + " untuk Cabut UseeTv dan internet sudah kami proses melalui sistem. \n\n"
                    + "Untuk pencabutan UseeTV dan Internet dimohon ke PlasaTelkom, Mohon dibantu dengan membawa :\n" +
                    "Perangkat ONT,\n" +
                    "perangkat STB dan remote,\n" +
                    "FC KTP ,\n" +
                    "dan materai 6ribu \n" +
                    "Karena ada berkas yang harus ditandatangani.\n\n"
                    + "Dan untuk informasi"
                    + " bahwa nanti" + " "
                    + name
                    + " tidak perlu mengantri lagi. Ketika sudah di plasa telkom sampaikan saja ke satpam di depan bahwa ini untuk pengembalian perangkat terkait cabut useetv.\n" +
                    " \n" +
                    "Mohon maaf" + " "
                    + name
                    + " sekiranya kapan bisa datang ke plasa telkom? untuk kami catatkan dinotifikasi kami. \n" +
                    " \n" +
                    "Demikian informasi yang dapat kami sampaikan.Kami mohon izin untuk menutup laporannya karena kami sudah tindak lanjuti. \n" +
                    "Terimakasih";
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEdukasiCustom(String number, String waktu, String name, String tiket, String nomerLayanan, String pesanCustom){
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone="
                    + number
                    + "&text="
                    + "Selamat "
                    + waktu
                    + " "
                    + name
                    + "."
                    + "Kami dari Telkom. Maaf mengganggu waktunya."
                    + "Terkait dengan laporan dinomer tiket "
                    + tiket
                    + " dengan nomer layanan "
                    + nomerLayanan + ".\n\n"
                    + pesanCustom
                    + "\n\n"
                    + "Demikian informasi yang dapat kami sampaikan.Kami mohon izin untuk menutup laporannya karena kami sudah tindak lanjuti. \n"
                    + "Terimakasih";
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEdukasiIsolirSementara(String number , String waktu, String name , String tiket, String nomerLayanan){
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Kami sampaikan informasi bahwa untuk isolir sementara dikenai biaya 55 ribu dan dimohon ke Plasa Telkom dengan membawa : \nFC KTP,\nmaterai 6rb,\ndan bukti pembayaran trakhir krn ada berkas yang harus ditandatangani.\n\n"
                +"Demikian informasi yang dapat kami sampaikan.\n" +
                "Terimakasih";
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEdukasiShareLocation(String number , String waktu, String name , String tiket, String nomerLayanan){
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Terkait dengan permintaan"+name + " untuk pasang indihome , mohon berkenan bisa dikirimkan Share Location yg tepat , di lokasi yang akan dilakukan pemasangan. Agar kami bisa mengecek jaringan Fiber Optik terdekat dari lokasi.\n\n"
                +"Demikian informasi yang dapat kami sampaikan.\nKami tunggu konfirmasi dari" + name +".\n" +
                "Terimakasih";
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_custom:
                if (checked)
                    textCustom.setVisibility(View.VISIBLE);
                    break;
            case R.id.radio_cabut:
                if (checked)
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_cabut_usee_inet:
                if (checked)
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_isolir_sementara:
                if (checked)
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_shareloc:
                if (checked)
                    textCustom.setVisibility(View.GONE);
                break;
        }
    }

}
