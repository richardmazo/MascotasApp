package richard.appmascostas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import static android.Manifest.permission.CALL_PHONE;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DetailActivity extends AppCompatActivity{

    public static final String PET_KEY = "pet";
    public static final int CALL_PHONE_REQUEST_CODE = 0;
    public String localOwnerPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    Bundle extras = getIntent().getExtras();

    final Pet pet = extras.getParcelable(PET_KEY);

        if(pet != null){
            TextView petDescription = (TextView) findViewById(R.id.activity_detail_pet_description);
            TextView ownerName = (TextView) findViewById(R.id.activity_detail_owner_name);
            TextView ownerPhoneNumber = (TextView) findViewById(R.id.activity_detail_owner_phone_number);
            ImageView petImage = (ImageView) findViewById(R.id.activity_detail_pet_image);

            petDescription.setText(pet.getDescripcion());
            ownerName.setText(pet.getOwnerName());
            ownerPhoneNumber.setText(pet.getPhoneNumber());
            localOwnerPhoneNumber = pet.getPhoneNumber();
            petImage.setImageDrawable(ContextCompat.getDrawable(this, pet.getImageId()));

            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_detail_collapsing_toolbar);
            collapsingToolbarLayout.setTitle(pet.getName());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_detail_fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeCall();
                }
            });
        }
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + localOwnerPhoneNumber));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }else{
                final String[] permissions = new String[]{CALL_PHONE};
                requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);
            }
        }else{
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CALL_PHONE_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            } if(shouldShowRequestPermissionRationale(CALL_PHONE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hacer llamadas");
                builder.setMessage("Debes aceptar este permiso para hacer llamadas desde la app Mascotas");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String[] permissions = new String[]{CALL_PHONE};
                        requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }

    }
}
