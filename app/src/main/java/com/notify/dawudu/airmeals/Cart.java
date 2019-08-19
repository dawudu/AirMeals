package com.notify.dawudu.airmeals;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create new Request
                showAlertDialog();

            }
        });

        loadListFood();
    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this, R.style.AlertDialog_AppCompat_);
        alertDialog.setTitle("One more step!");
        alertDialog.setIcon(R.drawable.sinlogo);
        alertDialog.setMessage("Enter your flight seat number & Booking" +
                "\nreference from your flight ticket." +
                "\n\nPut them in the following format and click YES to submit your meal order" +
                "\n\nExample: Seat 009, 9G41J3");


        final EditText edtAddress = new EditText(Cart.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);//Add edit text to alert dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getEmail(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),

                        cart
                );
                String address = edtAddress.getText().toString();
                if (TextUtils.isEmpty(address)) {

                    Toast.makeText(getApplicationContext(), "Enter your Flight seat & Booking Ref!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    //Submit to Firebase
                    //We will be using System.CurrentMilli to key
                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);

                    //Delet cart
                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "Thank you, Order Placed", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        alertDialog.show();


    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);


        //Calculate Total Price

        int total = 0;
        for (Order order : cart)
            total += (Double.parseDouble(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));


    }
}
