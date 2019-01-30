package jogja.telkom.edukasiwhatsapp;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {

  private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 200;
  String waktu;
  RadioGroup radioGroup;
  RadioButton radioPoll;
  Button submit;
  RadioButton radioCustom;
  EditText textCustom, textNumber, textNama, textTiket, textLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNumber     = (EditText)findViewById(R.id.phone_number);
        textNama       = (EditText)findViewById(R.id.nama_pelanggan);
        textTiket      = (EditText)findViewById(R.id.nomer_tiket);
        textLayanan    = (EditText)findViewById(R.id.nomer_layanan);
        textCustom     = (EditText)findViewById(R.id.custom_chat);

        radioCustom             = findViewById(R.id.radio_custom);
        radioGroup              = findViewById(R.id.radio_group);
        submit                  = findViewById(R.id.submit);

        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 11){
            waktu = "pagi";
        }else if(timeOfDay >= 11 && timeOfDay < 15){
            waktu = "siang";
        }else if(timeOfDay >= 15 && timeOfDay < 18){
            waktu = "sore";
        }

        checkPermission();
        requestPermission();

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String numberCountry           = textNumber.getText().toString();
                StringBuilder numberConvert = new StringBuilder(numberCountry);
                numberConvert.setCharAt(0, '2');

                String number                  = "6" + String.valueOf(numberConvert);
                String name                    = textNama.getText().toString();
                String tiket                   = textTiket.getText().toString();
                String nomerLayanan            = textLayanan.getText().toString();
                String pesanCustom             = textCustom.getText().toString();

                // Get android phone contact content provider uri.
                //Uri addContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                // Below uri can avoid java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/phones error.
                Uri addContactsUri = ContactsContract.Data.CONTENT_URI;

                // Add an empty contact and get the generated id.
                long rowContactId = getRawContactId();

                // Add contact name data.
                String displayName = textNama.getText().toString()+ " " +textLayanan.getText().toString();
                insertContactDisplayName(addContactsUri, rowContactId, displayName);

                // Add contact phone data.
                insertContactPhoneNumber(addContactsUri, rowContactId, textNumber.getText().toString());
                finish();

                //Check Form Fill
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
                            sendEdukasiCabutUsee(number, waktu, name, tiket, nomerLayanan);
                        }
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Edukasi Cabut Internet dan Usee TV")) {
                            sendEdukasiCabutUseeInet(number, waktu, name, tiket, nomerLayanan);
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
                //Clear form
                radioGroup.clearCheck();
                textLayanan.getText().clear();
                textNama.getText().clear();
                textNumber.getText().clear();
                textTiket.getText().clear();
                textCustom.getText().clear();
            }
        });

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CONTACTS);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS, WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Toast.makeText(MainActivity.this, "Permission Granted.", LENGTH_LONG).show();
                    else {
                        Toast.makeText(MainActivity.this, "Permission Denied.", LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CONTACTS, WRITE_CONTACTS},
                                                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private long getRawContactId(){
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }

    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

        getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber){
        // Create a ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

        // Calculate phone type by user selection.
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;

        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

        // Insert new contact data into phone contact list.
        getContentResolver().insert(addContactsUri, contentValues);

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
                    + " tidak perlu mengantri lagi. Ketika sudah di plasa telkom sampaikan saja ke satpam di depan bahwa ini untuk pengembalian perangkat terkait cabut useetv dan internet.\n" +
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

    private void sendEdukasiShareLocation(String number , String waktu, String name , String tiket, String nomerLayanan){
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Terkait dengan permintaan"+ " " +name + " untuk pasang indihome , mohon berkenan bisa dikirimkan Share Location yg tepat , di lokasi yang akan dilakukan pemasangan. Agar kami bisa mengecek jaringan Fiber Optik terdekat dari lokasi.\n\n"
                +"Demikian informasi yang dapat kami sampaikan.\nKami tunggu konfirmasi dari"+" " + name +".\n" +
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
                    + "Terkait dengan laporan dinomer tiket" + " "
                    + tiket
                    + " dengan nomer layanan" + " "
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
