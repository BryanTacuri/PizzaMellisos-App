package com.example.pizzamellisos.sale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzamellisos.AboutUs;
import com.example.pizzamellisos.IniciarSesionActivity;
import com.example.pizzamellisos.Promocion;
import com.example.pizzamellisos.R;
import com.example.pizzamellisos.UserProfile;
import com.example.pizzamellisos.sale.dialog.add_sales;
import com.example.pizzamellisos.sale.dialog.show_sale_dialog;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetail;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.example.pizzamellisos.entities.SaleHeader;
import com.example.pizzamellisos.entities.SaleHeaderForView;
import com.example.pizzamellisos.producto.Producto;
import com.example.pizzamellisos.utils.Api;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class PantallaPrincipalActiviry extends AppCompatActivity {
    private TextView mostrar;
    private FirebaseAuth mAuth;
    private DatabaseReference fbDataBase;
    private Button btnAddSales;
    private TableLayout tbtlout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<SaleHeaderForView> salesHeaderForViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_activiry);
        mAuth = FirebaseAuth.getInstance();
        fbDataBase= FirebaseDatabase.getInstance().getReference();
        btnAddSales = findViewById(R.id.btn_add_sales);
        salesHeaderForViewList=new ArrayList<>();
        tbtlout=findViewById(R.id.tbl_row_sales);
       // mostrar = (TextView)findViewById(R.id.textMostrar);
        //String sms = getIntent().getStringExtra("sms");
    //    mostrar.setText(sms);

        preferences = getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        fillTable();

    }
    private void  fillTableRows(){

        int index=0;
        tbtlout.removeViews(1, Math.max(0, tbtlout.getChildCount() - 1));
        for (SaleHeaderForView sale_header: salesHeaderForViewList) {
            TableRow tbrow = new TableRow(this);
            String color_="#FFC5CAE9";
            if(index%2==0){
                color_="#FFFFE0B2" ;
            }
            tbrow.setBackgroundColor( Color.parseColor(color_));
            tbrow.setPadding(2,2,2,2);
           // TextView tv0 = new TextView(this);
            TableRow.LayoutParams layoutParams= new TableRow.LayoutParams();
            layoutParams.weight=1;
           // layoutParams.width="wrap_content"

           // tv0.setGravity(Gravity.CENTER);
           // tv0.setLayoutParams(layoutParams);


            TextView txtname = new TextView(this);
            txtname.setText(sale_header.getClient());
            txtname.setLayoutParams(layoutParams);
            txtname.setGravity(Gravity.CENTER);


            TextView txtTotal = new TextView(this);
            txtTotal.setText(""+sale_header.getTotal());
            txtTotal.setLayoutParams(layoutParams);
            txtTotal.setGravity(Gravity.CENTER);






            layoutParams.width=15;
            MaterialButton button_show = new MaterialButton(this);
            button_show.setBackgroundColor(Color.parseColor("#FF3F51B5"));
            button_show.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_eye));
            button_show.setIconPadding(0);
            button_show.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);

            tbrow.addView(button_show);
            tbrow.addView(txtname);
            tbrow.addView(txtTotal);

            button_show.setLayoutParams(layoutParams);
            button_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("uiid", sale_header.getUid());
                    bundle.putString("client", sale_header.getClient());
                    bundle.putString("observation", sale_header.getObservation());
                    bundle.putString("total", ""+sale_header.getTotal());
                    show_sale_dialog sdt=new show_sale_dialog();
                    sdt.setArguments(bundle);
                    sdt.show(getSupportFragmentManager(), "DialogShowSale");
                }
            });
            layoutParams.rightMargin=15;
            MaterialButton button_edit = new MaterialButton(this);
            button_edit.setBackgroundColor(Color.parseColor("#FF673AB7"));
            button_edit.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_edit));
            button_edit.setIconPadding(0);
            button_edit.setLayoutParams(layoutParams);
            button_edit.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);

            MaterialButton button_delete = new MaterialButton(this);
            button_delete.setBackgroundColor(Color.parseColor("#FFF44336"));
            button_delete.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_delete_f));
            button_delete.setIconPadding(0);
            button_delete.setLayoutParams(layoutParams);
            Context cv=this;

            button_edit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            fbDataBase.child("sale_details").orderByChild("uidSaleHeader")
                            .equalTo(sale_header.getUid()).addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    ArrayList<SaleDetailForView> productsList=new ArrayList<>();
                                                    for (DataSnapshot single: snapshot.getChildren()) {

                                                        SaleDetail sale_detail=single.getValue(SaleDetail.class);
                                                        productsList.add(new SaleDetailForView(
                                                                sale_detail.getUid(), new Product(sale_detail.getUidProduct()), sale_detail.getCount(),
                                                                sale_detail.getPrice(), sale_detail.getTotal()
                                                        ));



                                                    }
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("client", sale_header.getClient());
                                                    bundle.putString("observation", sale_header.getObservation());
                                                    add_sales dialog=new add_sales(sale_header.getUid(), true, productsList);
                                                    dialog.setArguments(bundle);
                                                    dialog.show(getSupportFragmentManager(), "DialogAddSales");
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            }
                                    );

                        }
                    }
            );
            button_delete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Api.deleteChild("sales", sale_header.getUid(), cv);
                        }
                    }
            );

            button_delete.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);

            //layoutParams.width=50;
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation( LinearLayout.HORIZONTAL);



            linearLayout.addView(button_edit);

            linearLayout.addView(button_delete);

            tbrow.addView(linearLayout);
            tbtlout.addView(tbrow);
            index++;

        }


    }
    private void fillTable(){

        fbDataBase.child("sales").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        salesHeaderForViewList.clear();
                        for(DataSnapshot singleSnapshot : snapshot.getChildren()){

                            if(singleSnapshot.exists()){
                            SaleHeader slh = singleSnapshot.getValue(SaleHeader.class);
                            System.out.println(slh.getClient());
                            ArrayList<SaleDetailForView> sdtFvList=new ArrayList<SaleDetailForView>();
                            salesHeaderForViewList.add(
                                    new SaleHeaderForView(
                                            slh.getUid(),
                                            slh.getClient(),
                                            slh.getTotal(),
                                            slh.getObservation(),
                                            sdtFvList
                                    )
                            );
                            }
                        }
                        System.out.println(salesHeaderForViewList.size());
                        fillTableRows();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }


    public void openAddSalesDialog(View v){
        ArrayList<SaleDetailForView> details= new ArrayList<>();
        add_sales dialog=new add_sales(UUID.randomUUID().toString(), false, details);
        dialog.show(getSupportFragmentManager(), "DialogAddSales");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.opt_logout:{
                this.logOut();
                break;
            }
            case R.id.opt_product:
                Intent i = new Intent(getApplicationContext(), Producto.class);
                this.goToActivity(i);
                break;
            case R.id.opt_perfil:
                Intent p =new Intent(getApplicationContext(), UserProfile.class);
                this.goToActivity(p);
                break;
            case R.id.opt_about_us:
                this.goToAboutUsActivity();
                break;
            case R.id.opt_add_promotion:
                Intent prom = new Intent(getApplicationContext(), Promocion.class);
                this.goToActivity(prom);
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
    private void goToActivity(Intent i){
        startActivity(i);
    }

    private void goToAboutUsActivity(){
        Intent i = new Intent(getApplicationContext(), AboutUs.class);
        startActivity(i);
    }
    private void logOut(){
        mAuth.signOut();
        editor.putBoolean("sesion", false);
        editor.apply();
        Intent i = new Intent(getApplicationContext(), IniciarSesionActivity.class);
        startActivity(i);
        finish();
    }
}