package jogja.telkom.edukasiwhatsapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class FormatEdukasi {


    static void sendEdukasiCabutUsee(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan) {
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

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiCabutUseeInet(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan) {
        String pesan = "Selamat "
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

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiSeamless(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan){
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Mohon mengikuti langkah berikut untuk registrasi wifi id seamless:\n" +
                "\n" +
                "Registrasi Hanya Dapat Dilakukan DI MyIndiHome Lewat Jaringan INDIHOME\n" +
                "\n" +
                "Berikut cara registrasinya:\n" +
                " \n" +
                "1. Download aplikasi myIndiHome di Google Play Store dan App Store\n" +
                "2. Untuk pengguna baru, klik “BUAT AKUN”. untuk pengguna eksisting, klik “MASUK”.\n" +
                "3. Isi Email dan Password yang sudah terdaftar, kemudian klik “Masuk”\n" +
                "4. Pilih menu \"Langganan\", pada halaman bagian bawah.\n" +
                "5. Pilih menu \"Tambah Layanan\", pada halaman bagian atas.\n" +
                "6. Pilih menu katagori “Internet”.\n" +
                "7. Pilih menu “wifi.id seamless”.\n" +
                "8. klik \"Aktifkan\", untuk melanjutkan proses aktivasi wifi id\n" +
                "9. Untuk mendapatkan Username dan \n" +
                "Password, klik \"AKTIFKAN\" pada \n" +
                "halaman bagian bawah.\n" +
                "10.Muncul notifikasi, pastikan aktivasi\n" +
                "dilakukan dari jaringan IndiHome.\n" +
                "Untuk melanjutkan klik \"Lihat\".\n" +
                "11. Muncul notifikasi kode aktivasi,\n" +
                "untuk melanjutkan klik \"OK\".\n" +
                "12. Cek Pesan Masuk di aplikasi myIndiHome, kemudian buka pesan.\n" +
                "13. \"klik disini\" untuk melanjutkan proses aktivasi.\n" +
                "14. Sistem akan mengecek jaringan Fiber Optik Indihome\n" +
                "15. Muncul notifikasi aktivasi berhasil di pesan masuk (a), dan halaman akan kembali ke menu wifi seamless. Jika registrasi diluar jaringan Indihome pelanggan muncul keterangan (b)\n" +
                "16. Aktivasi berhasil :\n" +
                "    a. Username dan password akan digenerate\n" +
                "        otomatis oleh system (username berupa:\n" +
                "        " + nomerLayanan + "@wifi.id ,\n" +
                "        username tidak dapat di ganti. \n" +
                "        Pelanggan hanya bisa melakukan\n" +
                "        Ganti Password)\n" +
                "    b. Jumlah perangkat yang terdaftar akan\n" +
                "        terisi.\n" +
                "    c. Daftar perangkat akan muncul\n" +
                "        dengan keterangan\n" +
                "             - Reg Date\n" +
                "             - Nama Device (default 1)\n" +
                "             - Mac Address (kosong)\n" +
                "             - Status Perangkat \n" +
                "       (aktifkan di seamless@wifi.id)\n" +
                "    d. Create billing\n\n" +
                "Demikian informasi yang dapat kami sampaikan. Kami mohon tutup laporannya karena sudah ditindaklanjuti.\nTerimakasih.";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" +pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiShareLocation(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan){
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Terkait dengan permintaan"+ " " +name + " untuk pasang indihome , mohon berkenan bisa dikirimkan Share Location yg tepat , di lokasi yang akan dilakukan pemasangan. Agar kami bisa mengecek jaringan Fiber Optik terdekat dari lokasi.\n\n"
                +"Demikian informasi yang dapat kami sampaikan.\nKami tunggu konfirmasi dari"+" " + name +".\n" +
                "Terimakasih";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiIsolirSementara(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan){
        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String pesan = "Selamat " + waktu + " " + name + ". Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket " + tiket + " dengan nomer layanan " + nomerLayanan + ".\n\n"
                + "Kami sampaikan informasi bahwa untuk isolir sementara dikenai biaya 55 ribu dan dimohon ke Plasa Telkom dengan membawa : \nFC KTP,\nmaterai 6rb,\ndan bukti pembayaran trakhir krn ada berkas yang harus ditandatangani.\n\n"
                +"Demikian informasi yang dapat kami sampaikan.\n" +
                "Terimakasih";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiPelunasan(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan, String alasan){

        String pesan = "Selamat "+waktu+", "+ name +". \n" +
                "Kami dari Telkom, ingin menginformasikan tentang laporan " + name + " dinomer tiket "
                + tiket + " dengan nomer layanan " + nomerLayanan + ", terkait " + alasan + ". \n" +
                "Sebelum permintaan bisa kami proses dimohon untuk melunasi tagihan terlebih dahulu.\n"+
                "Terimakasih.\n";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number+ "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendResponGeneral(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan, String alasan){

        String pesan = "Selamat "+waktu+", "+ name +". \n" +
                "Kami dari Telkom, ingin menginformasikan tentang laporan " + name + " dinomer tiket "
                + tiket + " dengan nomer layanan " + nomerLayanan + ", terkait " + alasan + " sudah kami proses. \n" +
                "Kami minta izin untuk menutup laporan "+ name +".\n"+
                "Terimakasih.\n";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number+ "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendResponTagihan(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan, String tagihanLebih, String tagihanAkhir, String alasan){

        String pesan = "Selamat "+waktu+", "+name+ ". \n" +
                "Kami dari Telkom, ingin menginformasikan tentang laporan "+name+ " di nomor " + nomerLayanan +
                " dengan nomor tiket " + tiket + ", terkait tagihan tidak sudah kami proses. \n" +
                "\n" +
                "Adanya kelebihan tagihan sebesar Rp. " + tagihanLebih + ",-" + " dikarenakan "+ alasan +
                ", dan sudah kami benahi manjadi Rp. "+ tagihanAkhir +",-. \n" +
                "\n" +
                "Kami informasikan juga bahwa "+name+" tidak perlu khawatir apabila tagihan pada aplikasi MyIndiHome belum diperbarui, karena dalam sistem kami sudah diperbarui. \n" +
                "\n" +
                "Kami minta izin untuk menutup laporan "+name+". \n" +
                "Terimakasih.\n";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number+ "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendEdukasiCustom(MainActivity mainActivity, String number, String waktu, String name, String tiket, String nomerLayanan, String pesanCustom){

        String pesan = "Selamat "+ waktu+ " "+ name+ "."+ "Kami dari Telkom. Maaf mengganggu waktunya."
                + "Terkait dengan laporan dinomer tiket" + " "+ tiket+ " dengan nomer layanan" + " "+ nomerLayanan + ".\n\n"
                + pesanCustom+ "\n\n"+ "Demikian informasi yang dapat kami sampaikan.Kami mohon izin untuk menutup laporannya karena kami sudah tindak lanjuti. \n"
                + "Terimakasih";

        //Copy Pesan
        Object clipboardService = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", pesan);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number+ "&text=" + pesan;
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                mainActivity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
