package com.example.myroom.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myroom.database.DataBase;
import com.example.myroom.repository.AwsInfoRepository;
import com.example.myroom.retrofit.AwsInfoResponse;
import com.example.myroom.retrofit.GatewayUuidBody;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    AwsInfoRepository aswInfoRepository;

    private final LiveData<List<AwsInfoResponse>> mAswInfoList;

    private final MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private final MutableLiveData<String> mApiResultMutableData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        aswInfoRepository = new AwsInfoRepository(getApplication());

        mAswInfoList = aswInfoRepository.getAllAwsInfo();

        mProgressMutableData.postValue(View.INVISIBLE);
        mApiResultMutableData.postValue("null");
    }

    public void requestApi(String body) {
        mProgressMutableData.postValue(View.VISIBLE);

        aswInfoRepository.awsInfoRemote(
                new GatewayUuidBody(body),
                new AwsInfoRepository.IAwsInfoResponse() {
                    @Override
                    public void onResponse(AwsInfoResponse response) {
                        Log.i(TAG, "onResponse: " + response.getCertKeyName());

                        mProgressMutableData.postValue(View.INVISIBLE);
                        mApiResultMutableData.postValue("Success");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i(TAG, "onFailure: " + throwable);

                        mProgressMutableData.postValue(View.INVISIBLE);
                        mApiResultMutableData.postValue(
                                "Failure: " + throwable.getLocalizedMessage());

                    }
                });
    }

    public LiveData<List<AwsInfoResponse>> getAswInfoList() {
        return mAswInfoList;
    }

    public LiveData<Integer> getProgressLiveData() {
        return mProgressMutableData;
    }

    public MutableLiveData<String> getApiResultMutableData() {
        return mApiResultMutableData;
    }

    public void updateAwsInfo(String gatewayUuid,
                              String rootCAName,
                              String publicKeyName,
                              String privateKeyName,
                              String certKeyName,
                              String rootCAUrl,
                              String publicKeyUrl,
                              String privateKeyUrl,
                              String certKeyUrl,
                              String iotEndpoint) {
        aswInfoRepository.updateAwsInfo(
                gatewayUuid,
                rootCAName,
                publicKeyName,
                privateKeyName,
                certKeyName,
                rootCAUrl,
                publicKeyUrl,
                privateKeyUrl,
                certKeyUrl,
                iotEndpoint);
    }
}
