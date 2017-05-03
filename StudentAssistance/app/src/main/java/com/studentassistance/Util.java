package com.studentassistance;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

public class Util {


    public static final String BASEURL = "http://139.59.14.66:5001";
    public static final String LOGINURL = "/login";
    public static final String BASEURL2 = "http://projectstechmantra.in/webkioskSubject/";
    public static final String GETSUBJECT = "getSubjects.php";
    public static final String SENDREQUEST = "newRequest.php";
    public static final String GETSTATUS = "getRequestsById.php";
    public static final String CANCELREQUEST = "cancelRequest.php";


    public boolean check_connection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showerrormessage(Context context, String message){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    public void showsuccessmessage(Context context, String message){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Yea...")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }


    public void getlogin(Map args, final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();
        String url = BASEURL + LOGINURL;


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(args), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String body;

                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.dismiss();

                    try {
                        body = new String(error.networkResponse.data,"UTF-8");

                        try{
                            JSONObject jsonObject = new JSONObject(body);
                                showerrormessage(context, jsonObject.getString("error"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Response is " + body);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.dismiss();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                }
            }
        })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }


    public void getbucketdata(final Context context, final GetJsonArrayResult getJsonArrayResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String URL = BASEURL2 + GETSUBJECT;

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.POST, URL,null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getJsonArrayResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String body;

                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.dismiss();

                    try {
                        body = new String(error.networkResponse.data,"UTF-8");

                        try{
                            JSONObject jsonObject = new JSONObject(body);
                            showerrormessage(context, jsonObject.getString("error"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Response is " + body);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.dismiss();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }

    public void sendrequest(final Map args,final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String URL = BASEURL2 + SENDREQUEST;

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, args, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                 if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.dismiss();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }

    public void getstatus(final Map args,final Context context, final GetJsonArrayResult getJsonArrayResult){
        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String URL = BASEURL2 + GETSTATUS;

        CustomJsonArrayRequest jsObjRequest = new CustomJsonArrayRequest(Request.Method.POST, URL,args, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getJsonArrayResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String body;

                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.dismiss();

                    try {
                        body = new String(error.networkResponse.data,"UTF-8");

                        try{
                            JSONObject jsonObject = new JSONObject(body);
                            showerrormessage(context, jsonObject.getString("error"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Response is " + body);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.dismiss();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);

    }

    public void deleterequest(final Map args,final Context context, final GetResult getResult){
        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String URL = BASEURL2 + CANCELREQUEST;

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL,args, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.dismiss();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);

    }

}