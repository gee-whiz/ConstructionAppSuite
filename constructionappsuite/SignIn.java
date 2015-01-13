package com.example.codetribe1.constructionappsuite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.codetribe1.constructionappsuite.dto.CompanyStaffDTO;
import com.example.codetribe1.constructionappsuite.dto.GcmDeviceDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.RequestDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.ResponseDTO;
import com.example.codetribe1.constructionappsuite.util.CacheUtil;
import com.example.codetribe1.constructionappsuite.util.GCMUtil;
import com.example.codetribe1.constructionappsuite.util.SharedUtil;
import com.example.codetribe1.constructionappsuite.util.Statics;
import com.example.codetribe1.constructionappsuite.util.WebSocketUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;

import static com.example.codetribe1.constructionappsuite.util.Util.showErrorToast;


public class SignIn extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    static final String LOG = "RegistrationActivity";
    EditText iEmail, iPin;
    Button sSignIn;
    Context ctx2;
    ProgressBar progressBar;
    Registration registration = new Registration();
    //check if device is a virgin
    GcmDeviceDTO gcmDevice = new GcmDeviceDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ctx2 = getApplication();

        checkVirgin();
        setFields();


    }

    public void checkVirgin() {
        CompanyStaffDTO dto = SharedUtil.getCompanyStaff(ctx2);
        if (dto != null) {
            Log.i(LOG, "----------------Not a virgin anymore.............Checking GCM Registration");
            String id = SharedUtil.getRegistrationId(getApplicationContext());

            if (id == null) {

                registerGCMDevice();
            }
            Intent intent = new Intent(ctx2, OperationsPager.class);
            startActivity(intent);
            finish();
            return;
        }
        registerGCMDevice();


    }

    private void registerGCMDevice() {
        boolean ok = checkPlayServices();

        if (ok) {
            Log.e(LOG, "++++++++Starting Google Cloud Messaging registration");
            GCMUtil.startGCMRegistration(getApplicationContext(), new GCMUtil.GCMUtilListener() {
                @Override
                public void onDeviceRegistered(String id) {
                    Log.e(LOG, "++++++++++++GCM - we cool, good to go.....: " + id);
                    gcmDevice = new GcmDeviceDTO();
                    gcmDevice.setManufacturer(Build.MANUFACTURER);
                    gcmDevice.setModel(Build.MODEL);
                    gcmDevice.setSerialNumber(Build.SERIAL);
                    gcmDevice.setProduct(Build.PRODUCT);
                    gcmDevice.setAndroidVersion(Build.VERSION.RELEASE);
                    gcmDevice.setRegistrationID(id);

                }

                @Override
                public void onGCMError() {
                    Log.e(LOG, "++++++++++++onGCMError --- we got GCM problems");

                }
            });
        }


    }

    public boolean checkPlayServices() {
        Log.w(LOG, "checking GooglePlayServices .................");
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(ctx2);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms")));
                return false;
            } else {
                Log.i(LOG, "This device is not supported.");
                throw new UnsupportedOperationException("GooglePlayServicesUtil resultCode: " + resultCode);
            }
        }
        return true;
    }

    public void setFields() {

        sSignIn = (Button) findViewById(R.id.btnSign2);


        sSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSignIn();

            }
        });
    }

    public void sendSignIn() {

        iEmail = (EditText) findViewById(R.id.edtEmaill);
        iPin = (EditText) findViewById(R.id.edtPinn);
        if (iEmail.getText().toString().isEmpty()) {
            showErrorToast(ctx2, "Enter Email");
            return;
        }

        if (iPin.getText().toString().isEmpty()) {
            showErrorToast(ctx2, "Enter Pin");
            return;
        }

        RequestDTO r = new RequestDTO();

        r.setRequestType(RequestDTO.LOGIN);
        r.setEmail(iEmail.getText().toString());
        r.setPin(iPin.getText().toString());
        r.setGcmDevice(gcmDevice);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        WebSocketUtil.sendRequest(ctx2, Statics.COMPANY_ENDPOINT, r, new WebSocketUtil.WebSocketListener() {
            @Override
            public void onMessage(final ResponseDTO response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if (response.getStatusCode() > 0) {
                            showErrorToast(ctx2, response.getMessage());
                            return;
                        }

                        SharedUtil.saveCompany(ctx2, response.getCompany());
                        SharedUtil.saveCompanyStaff(ctx2, response.getCompanyStaff());
                        Intent intent2 = new Intent(ctx2, OperationsPager.class);
                        startActivity(intent2);

                        CacheUtil.cacheData(ctx2, response, CacheUtil.CACHE_DATA, new CacheUtil.CacheUtilListener() {
                            @Override
                            public void onFileDataDeserialized(ResponseDTO response) {

                            }

                            @Override
                            public void onDataCached() {
                                Intent intent2 = new Intent(ctx2, OperationsPager.class);
                                startActivity(intent2);
                            }

                            @Override
                            public void onError() {

                            }

                        });
                    }

                });

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError(String message) {

            }
        });

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}