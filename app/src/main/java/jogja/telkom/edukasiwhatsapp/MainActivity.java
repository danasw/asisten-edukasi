package jogja.telkom.edukasiwhatsapp;

import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
  EditText textCustom, textNumber, textNama, textTiket, textLayanan, textTagihanLebih, textTagihanAkhir, textAlasan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNumber      = (EditText)findViewById(R.id.phone_number);
        textNama        = (EditText)findViewById(R.id.nama_pelanggan);
        textTiket       = (EditText)findViewById(R.id.nomer_tiket);
        textLayanan     = (EditText)findViewById(R.id.nomer_layanan);
        textCustom      = (EditText)findViewById(R.id.custom_chat);
        textTagihanLebih= (EditText)findViewById(R.id.tagihan_lebih);
        textTagihanAkhir= (EditText)findViewById(R.id.tagihan_akhir);
        textAlasan      = (EditText)findViewById(R.id.alasan);


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
                String tagihanLebih            = textTagihanLebih.getText().toString();
                String tagihanAkhir            = textTagihanAkhir.getText().toString();
                String alasan                  = textAlasan.getText().toString();

                //ADD CONTACT
                Uri addContactsUri = ContactsContract.Data.CONTENT_URI;
                long rowContactId = getRawContactId();
                String displayName = textNama.getText().toString()+ " " +textTiket.getText().toString();
                insertContactDisplayName(addContactsUri, rowContactId, displayName);
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

                        //Do Edukasi Cabut Usee TV
                        if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Cabut Usee TV")) {
                            FormatEdukasi.sendEdukasiCabutUsee(MainActivity.this, number, waktu, name, tiket, nomerLayanan);
                        }
                        //Do Edukasi Cabut Usee dan Internet
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Edukasi Cabut Internet dan Usee TV")) {
                            FormatEdukasi.sendEdukasiCabutUseeInet(MainActivity.this, number, waktu, name, tiket, nomerLayanan);
                        }
                        //Do Edukasi Seamless
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Edukasi Registrasi WiFi Id Seamless")) {
                            FormatEdukasi.sendEdukasiSeamless(MainActivity.this, number, waktu, name, tiket, nomerLayanan);
                        }
                        //Do Edukasi Share Loc
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Share Location")){
                            FormatEdukasi.sendEdukasiShareLocation(MainActivity.this, number,waktu,name,tiket,nomerLayanan);
                        }
                        //Do Edukasi Isolir Sementara
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Isolir Sementara")){
                            FormatEdukasi.sendEdukasiIsolirSementara(MainActivity.this, number,waktu,name,tiket,nomerLayanan);
                        }
                        //Do Edukasi Pelunasan
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Edukasi Melunasi Tagihan")){
                            //EditText not empty
                            if (TextUtils.isEmpty(textAlasan.getText())){
                                textAlasan.setError("ISI ALASAN");
                            }
                            else {
                                FormatEdukasi.sendEdukasiPelunasan(MainActivity.this, number, waktu, name, tiket, nomerLayanan, alasan);
                            }
                        }
                        //Do Respon Laporan UMUM
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Respon Laporan Umum")){
                            if (TextUtils.isEmpty(textAlasan.getText())){
                                textAlasan.setError("ISI ALASAN");
                            }
                            else {
                                FormatEdukasi.sendResponGeneral(MainActivity.this, number, waktu, name, tiket, nomerLayanan, alasan);
                            }
                        }
                        //Respon Tagihan tidak sesuai
                        else if (radioPoll.getText().toString().equalsIgnoreCase("Respon Tagihan Tidak Sesuai")){
                            if (TextUtils.isEmpty(textTagihanLebih.getText())){
                                textTagihanLebih.setError("ISI JUMLAH KELEBIHAN");
                            }
                            else if (TextUtils.isEmpty(textTagihanAkhir.getText())){
                                textTagihanAkhir.setError("ISI TAGIHAN YANG SUDAH BENAR");
                            }
                            else if (TextUtils.isEmpty(textAlasan.getText())){
                                textAlasan.setError("ISI ALASAN KELEBIHAN");
                            }
                            else {
                                FormatEdukasi.sendResponTagihan(MainActivity.this, number,waktu,name,tiket,nomerLayanan,tagihanLebih,tagihanAkhir,alasan);
                            }
                        }
                        //Custom Chat
                        else if(radioPoll.getText().toString().equalsIgnoreCase("Custom Chat")){
                            if (TextUtils.isEmpty(textCustom.getText())){
                                textCustom.setError("ISI INTI DARI PESAN");
                            }
                            else {
                            FormatEdukasi.sendEdukasiCustom(MainActivity.this, number,waktu,name,tiket,nomerLayanan,pesanCustom);
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
        return ContentUris.parseId(rawContactUri);
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

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_cabut:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_cabut_usee_inet:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_seamless:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_shareloc:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_isolir_sementara:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_pelunasan:
                if (checked)
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.VISIBLE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_respon_general:
                if (checked)
                    textCustom.setVisibility(View.GONE);
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_respon_tagihan:
                if (checked)
                    textTagihanLebih.setVisibility(View.VISIBLE);
                    textTagihanAkhir.setVisibility(View.VISIBLE);
                    textAlasan.setVisibility(View.VISIBLE);
                    textCustom.setVisibility(View.GONE);
                break;
            case R.id.radio_custom:
                if (checked)
                    textCustom.setVisibility(View.VISIBLE);
                    textTagihanLebih.setVisibility(View.GONE);
                    textTagihanAkhir.setVisibility(View.GONE);
                    textAlasan.setVisibility(View.GONE);
                break;
        }
    }
}
