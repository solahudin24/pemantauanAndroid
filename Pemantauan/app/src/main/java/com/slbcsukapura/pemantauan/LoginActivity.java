package com.slbcsukapura.pemantauan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText Nis, Password;
    Button LogIn ;
    String PasswordHolder, NisHolder;
    String finalResult ;
    String HttpURL = "http://slbcsukapura.info/android/APILogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserNis = "";
    DatabaseHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyDB = new DatabaseHelper(this);
        Cursor res = MyDB.LihatData();
        if(res.moveToNext()){
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getPermissionToReadUserLocation();
//        }
        Nis = (EditText)findViewById(R.id.nis);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.Login);

        //startService(new Intent(LoginActivity.this, LocationTrace.class));

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserLoginFunction(NisHolder, PasswordHolder);

                }
                else {

                    Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    // private static final int READ_LOCATION_PERMISSIONS_REQUEST = 1;

    public void CheckEditTextIsEmptyOrNot(){

        NisHolder = Nis.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NisHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String nis, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LoginActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                Log.i("Hasil", "onPostExecute: "+httpResponseMsg);
                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    MyDB.SimpanData(NisHolder, PasswordHolder);
                    finish();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    intent.putExtra(UserNis,nis);

                    startActivity(intent);

                }
                else{

                    Toast.makeText(LoginActivity.this, "Koneksi gagal", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("nis",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(nis,password);
    }

//    @TargetApi(Build.VERSION_CODES.M)
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void getPermissionToReadUserLocation() {
//        // 1) Pastikan ContextCompat.checkSelfPermission(...) menggunakan versi support ]
//        // library karena Context.checkSelfPermission(...) hanya tersedia di Marshmallow.
//        // 2) Selalu lakukan pemeriksaan meskipun permission sudah pernah diberikan
//        // karena pengguna dapat mencabut permission kapan saja lewat Settings
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Bila masuk ke blok ini artinya permission belum diberikan oleh user.
//            // Periksa apakah pengguna pernah diminta permission ini dan menolaknya.
//            // Jika pernah, kita akan memberikan penjelasan mengapa permission ini dibutuhkan.
//            if (shouldShowRequestPermissionRationale(
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Tampilkan penjelasan mengapa aplikasi ini perlu membaca kontak disini
//                // sebelum akhirnya me-request permission dan menampilkan hasilnya
//            }
//
//            // Lakukan request untuk meminta permission (menampilkan jendelanya)
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    READ_LOCATION_PERMISSIONS_REQUEST);
//        }
//    }
//
//    // Callback yang membawa hasil dari pemanggilan requestPermissions(...)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        // Pengecekan ini akan memastikan bahwa hasil yang diberikan berasal dari request
//        // yang kita lakukan berdasarkan kode yang ditulis di atas
//        if (requestCode == READ_LOCATION_PERMISSIONS_REQUEST) {
//            if (grantResults.length == 1 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Read Location permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Read Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

}
