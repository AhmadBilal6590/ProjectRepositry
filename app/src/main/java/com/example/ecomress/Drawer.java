package com.example.ecomress;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomress.Modal.Products;
import com.example.ecomress.ViewHolder.ProductViewHolder;
import com.example.ecomress.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

public class Drawer extends AppCompatActivity {

    // private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle toggle;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        recyclerView = findViewById(R.id.recycle_menu_drawer);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Paper.init(this);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Drawer.this,Main2Activity.class);
                startActivity(i);

            }
        });


         navigationView = findViewById(R.id.nav_view);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.user_name1);
        ImageView userProfilePic = headerView.findViewById(R.id.user_profile_image);
       // userName.setText(Prevalent.CurrentonlineUser.getName());
        //Picasso.get().load(Prevalent.CurrentonlineUser.getImage()).into(userProfilePic);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//       mAppBarConfiguration = new AppBarConfiguration.Builder(
//              R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//               .setDrawerLayout(drawer)
//               .build();
//        @SuppressLint("ResourceType") ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,1,1);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//       navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_cart) {
                    Intent i=new Intent(Drawer.this,Main2Activity.class);
                    startActivity(i);
                    drawer.closeDrawer(GravityCompat.START);

                } else if (id == R.id.nav_orders) {
                    Toast.makeText(getApplicationContext(), "order", Toast.LENGTH_LONG).show();
                    drawer.closeDrawer(GravityCompat.START);

                } else if (id == R.id.nav_catagories) {
                    Toast.makeText(getApplicationContext(), "catagories", Toast.LENGTH_LONG).show();
                    drawer.closeDrawer(GravityCompat.START);

                } else if (id == R.id.nav_settings) {

                    Intent intent=new Intent(Drawer.this,SettingsActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(GravityCompat.START);

                } else if (id == R.id.nav_logout) {
                    Paper.book().destroy();
                    Intent intent = new Intent(Drawer.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();

                  FirebaseRecyclerAdapter<Products ,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                      @RequiresApi(api = Build.VERSION_CODES.M)
                      @Override
                      protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                          holder.txtProductName.setText(model.getName());
                          holder.textProductDescription.setText(model.getDescription());
                          holder.textProductPrice.setText("Price ="+model.getPrice()+"$");
                          Picasso.get().load(model.getImage()).into(holder.imageView);

                          holder.itemView.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Intent i=new Intent(Drawer.this,ProductDetailsActivity.class);
                                  i.putExtra("pid",model.getPid());
                                  startActivity(i);
                              }
                          });

                      }

                      @RequiresApi(api = Build.VERSION_CODES.M)
                      @NonNull
                      @Override
                      public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                          View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
                          ProductViewHolder holder=new ProductViewHolder(view);
                          return holder;
                      }
                  };
                  recyclerView.setAdapter(adapter);
                  adapter.startListening();


         }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

}
