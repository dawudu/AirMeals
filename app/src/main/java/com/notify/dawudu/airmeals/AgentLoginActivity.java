package com.notify.dawudu.airmeals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgentLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    LinearLayout llsignin, llsignup;


    private TextInputEditText mBookingReferenceSignUp, mEmail_SignUp, mPassword_SignUp;
    private TextInputEditText mBookingReferenceSignIn, mPassword_SignIn;


//    private Button mLogin, mRegistration;


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_login);


        mAuth = FirebaseAuth.getInstance();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
        llsignup = (LinearLayout) findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////// SIGN UP/////////////////////////
        mEmail_SignUp = (TextInputEditText) findViewById(R.id.email_SignUp);
        mBookingReferenceSignUp = (TextInputEditText) findViewById(R.id.bookingReference_SignUp);
        mPassword_SignUp = (TextInputEditText) findViewById(R.id.password_SignUp);

        ////////////// SIGN IN/////////////////////////
        mBookingReferenceSignIn = (TextInputEditText) findViewById(R.id.bookingReference_SignIn);
        mPassword_SignIn = (TextInputEditText) findViewById(R.id.password_SignIn);

        //SIGN UP BUTTON
        btnSignup = (Button) findViewById(R.id.btnSignup);
//        btnSignup.setOnClickListener(this);

        //SIGN IN BUTTON
        btnSignin = (Button) findViewById(R.id.btnSignin);
//        btnSignin.setOnClickListener(this);
//        btnSignin.setOnClickListener(new MyClickListener());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(AgentLoginActivity.this, MealSeats.class);
//                startActivity(intent);

                table_user.addValueEventListener(new ValueEventListener() {

                    ProgressDialog mDialog = new ProgressDialog(AgentLoginActivity.this);

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String email = mBookingReferenceSignIn.getText().toString();
                        final String password = mBookingReferenceSignIn.getText().toString();

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //Check if the user exists in the database
                        if (dataSnapshot.child(mBookingReferenceSignIn.getText().toString()).exists()) {

                            //Get User information
                            User user = dataSnapshot.child(mBookingReferenceSignIn.getText().toString()).getValue(User.class);
                            user.setPhone(mBookingReferenceSignIn.getText().toString());//Set phone or booking flight reference
                            if (user.getPassword().equals(mPassword_SignIn.getText().toString())) {

                                Intent homeIntent = new Intent(AgentLoginActivity.this, Flights.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), "Wrong password !!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

            }

        });


        ///////////////////////////////////////////////////////////////////////////

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                if (isSigninScreen)
                    btnSignup.startAnimation(clockwise);

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        final String bookingReference = mBookingReferenceSignUp.getText().toString();
                        final String email = mEmail_SignUp.getText().toString();
                        final String password = mPassword_SignUp.getText().toString();

                        if (TextUtils.isEmpty(bookingReference)) {
                            Toast.makeText(getApplicationContext(), "Enter booking reference!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (password.length() < 6) {
                            mPassword_SignUp.setError(getString(R.string.minimum_password));

                        }


                        //Check if the user exists in the database
                        if (dataSnapshot.child(mBookingReferenceSignUp.getText().toString()).exists()) {

                            Toast.makeText(getApplicationContext(), "Please Enter Booking Reference", Toast.LENGTH_SHORT).show();

                        } else {

                            User user = new User(mEmail_SignUp.getText().toString(), mPassword_SignUp.getText().toString());
                            table_user.child(mBookingReferenceSignUp.getText().toString()).setValue(user);
                            Toast.makeText(AgentLoginActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Flights.class);
                            startActivity(intent);
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });


            }
        });
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llSignin || view.getId() == R.id.llSignup) {
            // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }

}



