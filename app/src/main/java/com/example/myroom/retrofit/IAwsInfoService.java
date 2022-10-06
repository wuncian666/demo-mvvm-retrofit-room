package com.example.myroom.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAwsInfoService {
    // post body
    @POST(".")//將資料傳送新增
    Call<AwsInfoResponse> postGatewayUuid(@Body GatewayUuidBody gatewayUuidBody);
    //傳送@Body型態的post物件
}
