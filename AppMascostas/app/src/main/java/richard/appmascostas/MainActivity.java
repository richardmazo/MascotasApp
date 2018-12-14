package richard.appmascostas;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        RecyclerView petRecycler = (RecyclerView) findViewById(R.id.activity_main_pet_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        petRecycler.setLayoutManager(gridLayoutManager);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Sia",getString(R.string.pet_descripcion),"Sandra","555-55-55",R.drawable.sia));
        petList.add(new Pet("Pun",getString(R.string.pet_descripcion),"Jesus","555-55-55",R.drawable.pun));
        petList.add(new Pet("Catrina",getString(R.string.pet_descripcion),"Myriam","555-55-55",R.drawable.catrina));
        petList.add(new Pet("Husk",getString(R.string.pet_descripcion),"Ulises","555-55-55",R.drawable.husk));
        petList.add(new Pet("Capitano",getString(R.string.pet_descripcion),"Omar","555-55-55",R.drawable.capitano));
        petList.add(new Pet("Noodles",getString(R.string.pet_descripcion),"Claudia","555-55-55",R.drawable.noodles));
        petList.add(new Pet("Tut",getString(R.string.pet_descripcion),"Gabriela","555-55-55",R.drawable.tut));

        PetAdapter petAdapter = new PetAdapter(this, petList);
        petRecycler.setAdapter(petAdapter);

        petAdapter.setOnPetClickListener(new PetAdapter.OnPetClickListener() {
            @Override
            public void onPetClick(Pet pet) {

                try{
                    Toast.makeText(MainActivity.this, pet.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, richard.appmascostas.DetailActivity.class);
                    intent.putExtra(DetailActivity.PET_KEY, pet);

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                        startActivity(intent, bundle);
                    }else{
                        startActivity(intent);
                    }
                }catch(ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        });



    }
}
