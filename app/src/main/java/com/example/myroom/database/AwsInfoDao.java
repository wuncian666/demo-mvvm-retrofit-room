package com.example.myroom.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myroom.retrofit.AwsInfoResponse;

import java.util.List;

@Dao
public interface AwsInfoDao {
    String tableName = "aws_info_table";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AwsInfoResponse awsInfoResponse);

    @Query("UPDATE aws_info_table SET " +
            "root_ca_name = :rootCAName," +
            " public_key_name = :publicKeyName," +
            " private_key_name = :privateKeyName," +
            " cert_key_name = :certKeyName," +
            " root_ca_url = :rootCAUrl," +
            " public_key_url = :publicKeyUrl," +
            " private_key_url = :privateKeyUrl," +
            " cert_key_url = :certKeyUrl," +
            " iot_endpoint = :iotEndpoint WHERE gateway_uuid = :gatewayUuid")
    void updateAwsInfo(String gatewayUuid,
                       String rootCAName,
                       String publicKeyName,
                       String privateKeyName,
                       String certKeyName,
                       String rootCAUrl,
                       String publicKeyUrl,
                       String privateKeyUrl,
                       String certKeyUrl,
                       String iotEndpoint);

    @Query("DELETE FROM aws_info_table WHERE gateway_uuid = :gatewayUuid")
    void deleteByGatewayUuid(String gatewayUuid);

    @Query("SELECT * FROM aws_info_table WHERE gateway_uuid = :gatewayUuid")
    LiveData<AwsInfoResponse> getAwsInfoByGatewayUuid(String gatewayUuid);

    @Query("SELECT * FROM aws_info_table")
    LiveData<List<AwsInfoResponse>> getAllAwsInfo();
}
