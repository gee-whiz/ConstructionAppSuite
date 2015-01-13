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

import com.example.codetribe1.constructionappsuite.dto.CompanyDTO;
import com.example.codetribe1.constructionappsuite.dto.CompanyStaffDTO;
import com.example.codetribe1.constructionappsuite.dto.GcmDeviceDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.RequestDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.ResponseDTO;
import com.example.codetribe1.constructionappsuite.util.CacheUtil;
import com.example.codetribe1.constructionappsuite.util.ErrorUtil;
import com.example.codetribe1.constructionappsuite.util.GCMUtil;
import com.example.codetribe1.constructionappsuite.util.SharedUtil;
import com.example.codetribe1.constructionappsuite.util.Statics;
import com.example.codetribe1.constructionappsuite.util.WebSocketUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.acra.ACRA;

import static com.example.codetribe1.constructionappsuite.util.Util.showErrorToast;



/*Author: George Kapoya
 Mentor: Aubrey Malabie
  Date: 02-01-2015
 */

public class Registration extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    static final String LOG = "RegistrationActivity";
    Context ctx;
    EditText eFirstName, eSurname, eEmail, ePinNumber, eCompany, iEmail, iPin;
    Button bReg, bCancel, iSignin, iCancel;
    ProgressBar progressBar;
    GcmDeviceDTO gcmDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ctx = getApplication();
        checkVirgin();
        setFields();


    }


    //set Fields
    public void setFields() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        eFirstName = (EditText) findViewById(R.id.edtFirstName);
        eSurname = (EditText) findViewById(R.id.edtSurname);
        eEmail = (EditText) findViewById(R.id.edtEmail);
        ePinNumber = (EditText) findViewById(R.id.edtPin);
        eCompany = (EditText) findViewById(R.id.edtCompanyname);
        bReg = (Button) findViewById(R.id.REG_btnNewGroup);
        bCancel = (Button) findViewById(R.id.btnCancel);
        iEmail = (EditText) findViewById(R.id.edtEmaill);
        iPin = (EditText) findViewById(R.id.edtPinn);
        iSignin = (Button) findViewById(R.id.btnSign2);


        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegistration();
            }
        });
    }

//check if device is a virgin

    public void checkVirgin() {
        CompanyStaffDTO dto = SharedUtil.getCompanyStaff(ctx);
        if (dto != null) {
            Log.i(LOG, "----------------Not a virgin anymore.............Checking GCM Registration");
            String id = SharedUtil.getRegistrationId(getApplicationContext());

            if (id == null) {

                registerGCMDevice();
            }
            Intent intent = new Intent(ctx, OperationsPager.class);
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
                .isGooglePlayServicesAvailable(ctx);
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

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public void sendRegistration() {

        //verify user inputs
        if (eCompany.getText().toString().isEmpty()) {
            showErrorToast(ctx, "Company name");
            return;
        }

        if (eFirstName.getText().toString().isEmpty()) {
            showErrorToast(ctx, "Enter First name");
            return;
        }

        if (eSurname.getText().toString().isEmpty()) {
            showErrorToast(ctx, "Enter surname");
            return;
        }

        if (eEmail.getText().toString().isEmpty()) {
            showErrorToast(ctx, "Enter Email");
            return;
        }

        if (ePinNumber.getText().toString().isEmpty()) {
            showErrorToast(ctx, "Enter pin number");
            return;

        }

        CompanyStaffDTO a = new CompanyStaffDTO();


        a.setFirstName(eFirstName.getText().toString());
        a.setLastName(eSurname.getText().toString());
        a.setEmail(eEmail.getText().toString());
        a.setPin(ePinNumber.getText().toString());
        a.setGcmDevice(gcmDevice);

        final CompanyDTO b = new CompanyDTO();

        b.setCompanyName(eCompany.getText().toString());

        RequestDTO r = new RequestDTO();

        r.setRequestType(RequestDTO.REGISTER_COMPANY);
        r.setCompany(b);
        r.setCompanyStaff(a);


        progressBar.setVisibility(View.VISIBLE);

        WebSocketUtil.sendRequest(ctx, Statics.COMPANY_ENDPOINT, r, new WebSocketUtil.WebSocketListener() {
            @Override
            public void onMessage(final ResponseDTO response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if (!ErrorUtil.checkServerError(ctx, response)) {
                            return;
                        }

                        SharedUtil.saveCompanyStaff(ctx, response.getCompanyStaff());
                        SharedUtil.saveCompany(ctx, response.getCompany());
                        if (response.getCompany() != null) {
                            ACRA.getErrorReporter().putCustomData("companyID", "" + response.getCompany().getCompanyID());
                            ACRA.getErrorReporter().putCustomData("companyName", response.getCompany().getCompanyName());
                        }

                        ResponseDTO countries = new ResponseDTO();
                        countries.setCountryList(response.getCountryList());

                        CacheUtil.cacheData(ctx, countries, CacheUtil.CACHE_COUNTRIES, new CacheUtil.CacheUtilListener() {
                            @Override
                            public void onFileDataDeserialized(ResponseDTO response) {

                            }

                            @Override
                            public void onDataCached() {
                                response.setCountryList(null);
                                CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_DATA, new CacheUtil.CacheUtilListener() {
                                    @Override
                                    public void onFileDataDeserialized(ResponseDTO response) {

                                    }

                                    @Override
                                    public void onDataCached() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });

                            }

                            @Override
                            public void onError() {

                            }
                        });

                        Intent intent = new Intent(ctx, OperationsPager.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onClose() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(String message) {

            }

        });


    }


}



