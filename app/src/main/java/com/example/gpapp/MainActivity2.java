package com.example.gpapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.gpapp.Model.User;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.example.gpapp.databinding.ActivityMainBinding;




public class MainActivity2 extends AppCompatActivity {
    ImageView profile_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
        ViewPager2 viewPager2 =findViewById(R.id.viewPager);
        viewPager2.setAdapter(new GPPagerAdaper(this));


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0: {
                        tab.setText("Résumé");
                        tab.setIcon(R.drawable.ic_pending);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)
                        );
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 1: {
                        tab.setText("Company");
                        tab.setIcon(R.drawable.ic_company);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)
                        );
                        badgeDrawable.setNumber(8);
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 2: {
                        tab.setText("Scholars");
                        tab.setIcon(R.drawable.ic_scholar);

                        BadgeDrawable badgeDrawable =tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)
                        );
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(100);
                        badgeDrawable.setMaxCharacterCount(3);
                        break;
                    }
                    case 3: {
                        tab.setIcon(R.drawable.ic_menu);
                       tab.setText("Menu");
                            BadgeDrawable badgeDrawable =tab.getOrCreateBadge();
                          badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)
                        );
                        badgeDrawable.setVisible(true);

                    }
                }

            }
        }
        );
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });





        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }
                else
                {
                    Glide.with(MainActivity2.this).load(user.getImageURL()).into(profile_image);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

               // processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
              //  processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

   /* private void processsearch(String s)
    {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("course").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }*/


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity2.this , StartActivity.class));
                finish();
                return true;

        }
        return false;
    }
}