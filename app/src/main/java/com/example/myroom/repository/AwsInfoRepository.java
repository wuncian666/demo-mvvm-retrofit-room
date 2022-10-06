package com.example.myroom.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.myroom.database.AwsInfoDao;
import com.example.myroom.database.DataBase;
import com.example.myroom.retrofit.AwsInfoResponse;
import com.example.myroom.retrofit.GatewayUuidBody;
import com.example.myroom.retrofit.IAwsInfoService;
import com.example.myroom.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AwsInfoRepository {
    private final static String TAG = AwsInfoResponse.class.getSimpleName();

    AwsInfoDao awsInfoDao;

    public AwsInfoRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);

        awsInfoDao = dataBase.getAwsDao();
    }

    public void awsInfoRemote(GatewayUuidBody body, IAwsInfoResponse awsInfoResponse) {
        IAwsInfoService service =
                RetrofitClientInstance.getInstance().create(IAwsInfoService.class);

        Call<AwsInfoResponse> initiate = service.postGatewayUuid(body);

        initiate.enqueue(new Callback<AwsInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<AwsInfoResponse> call,
                                   @NonNull Response<AwsInfoResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    awsInfoResponse.onResponse(response.body());

                    AwsInfoResponse data = response.body();
                    data.setGatewayUuid(body.getGatewayUUID());
                //TODO: insert
                    insert(data);
                } else {
                    awsInfoResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AwsInfoResponse> call,
                                  @NonNull Throwable t) {
                awsInfoResponse.onFailure(t);
            }
        });

    }

    public interface IAwsInfoResponse {
        void onResponse(AwsInfoResponse response);

        void onFailure(Throwable throwable);
    }

    public void insert(AwsInfoResponse response) {
        DataBase.databaseWriteExecutor.execute(() -> awsInfoDao.insert(response));
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
        DataBase.databaseWriteExecutor.execute(() ->
                awsInfoDao.updateAwsInfo(gatewayUuid,
                        rootCAName,
                        publicKeyName,
                        privateKeyName,
                        certKeyName,
                        rootCAUrl,
                        publicKeyUrl,
                        privateKeyUrl,
                        certKeyUrl,
                        iotEndpoint));
    }

    public void deleteByGatewayUuid(String gatewayUuid) {
        DataBase.databaseWriteExecutor.execute(() -> awsInfoDao.deleteByGatewayUuid(gatewayUuid));
    }

    public LiveData<AwsInfoResponse> getAwsInfoByGatewayUuid(String gatewayUuid) {
        return awsInfoDao.getAwsInfoByGatewayUuid(gatewayUuid);
    }

    public LiveData<List<AwsInfoResponse>> getAllAwsInfo() {
        return awsInfoDao.getAllAwsInfo();
    }
}
