package com.studentassistance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    UserLocalStore userLocalStore;

    int flag=0;

    EditText eno,password,dob;
    ImageView calendar,seepassword;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eno = (EditText)findViewById(R.id.eno);
        password = (EditText)findViewById(R.id.password);
        dob = (EditText)findViewById(R.id.dob);
        calendar = (ImageView)findViewById(R.id.cal);
        textView = (TextView)findViewById(R.id.login_edit);
        seepassword = (ImageView)findViewById(R.id.imageView2);

        eno.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(eno, 0);
            }
        },200);



        seepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {

                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    flag=1;
                    Resources res = getResources();
                    seepassword.setImageDrawable(res.getDrawable(R.drawable.ic_custom_hide));

                }
                else
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=0;
                    Resources res = getResources();
                    seepassword.setImageDrawable(res.getDrawable(R.drawable.ic_custom_show));

                }
            }
        });

        calendar.setOnClickListener(this);
        textView.setOnClickListener(this);


        userLocalStore = new UserLocalStore(this);

    }

    @Override
    public void onClick(View v) {

        if(v == textView){

            hideSoftKeyboard();

            if(new Util().check_connection(Login.this)) {

                if(validate()) {

                    String enos = eno.getText().toString();
                    String passwords = password.getText().toString();
                    String dobs = dob.getText().toString();

                    authenticate(enos, passwords, dobs);
                }
                else {

                    if(eno.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.eno));
                    }
                    if(password.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.password));
                    }
                    if(dob.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.dob));
                    }


                }
            }
            else{

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("No Internent Connection")
                        .setContentText("Won't be able to login!")
                        .setConfirmText("Go to Settings!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                startActivity(new Intent(Settings.ACTION_SETTINGS));
                                sDialog.cancel();
                            }
                        })
                        .show();

            }


        }
        else {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    Login.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );

            dpd.show(getFragmentManager(), "Datepickerdialog");

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        dob.setText(date);
    }

    private boolean validate() {
        return !eno.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")
                && !dob.getText().toString().trim().equals("");
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    void authenticate(String eno,String password,String dob){

        Map<String, String> params = new HashMap<String, String>();
        params.put("eno", eno);
        params.put("password", password);
        params.put("dob", dob);


        new Util().getlogin(params, this, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {
                if (jsonObject != null) {
                    userLocalStore.setUserloggedIn(true);
                    senddata(jsonObject);

                }

            }
        });

    }

    void senddata (JSONObject jsonObject){
        Intent intent = new Intent(this,Home.class);
        intent.putExtra("subject", jsonObject.toString());
        startActivity(intent);
    }
}
