package com.lembergstate.spcs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(v -> login());

        _signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }

    private void login() {
        Log.d(TAG, "Login");

//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("Person_ID", input_email.getText().toString());
//        startActivity(intent);
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);

        // TODO: IF SERVER IS OK UNCOMMENT THIS
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }
//
//        _loginButton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//        String email = input_rfid.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        // TODO: Implement your own authentication logic here.
//
//        new android.os.Handler().postDelayed(
//                () -> {
//                    // On complete call either onLoginSuccess or onLoginFailed
//                    onLoginSuccess();
//                    // onLoginFailed();
//                    progressDialog.dismiss();
//                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

// --Commented out by Inspection START (07.04.2019 16:14):
//    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);
//        finish();
//    }
// --Commented out by Inspection STOP (07.04.2019 16:14)

// --Commented out by Inspection START (07.04.2019 16:14):
//// --Commented out by Inspection START (07.04.2019 16:14):
////    public void onLoginFailed() {
////        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
////
////        _loginButton.setEnabled(true);
////    }
//// --Commented out by Inspection STOP (07.04.2019 16:14)
//
//    public boolean validate() {
//        boolean valid = true;
//
//        String email = input_rfid.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            input_rfid.setError("enter a valid rfid number");
//            valid = false;
//        } else {
//            input_rfid.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        return valid;
//
//    }
// --Commented out by Inspection STOP (07.04.2019 16:14)
}
