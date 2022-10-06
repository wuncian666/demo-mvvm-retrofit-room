package com.example.myroom.retrofit;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "aws_info_table", primaryKeys = {"gateway_uuid"})
public class AwsInfoResponse {

    @NonNull
    @ColumnInfo(name = "gateway_uuid")
    private String gatewayUuid;

    @ColumnInfo(name = "root_ca_name")
    private String rootCAName;

    @ColumnInfo(name = "public_key_name")
    private String publicKeyName;

    @ColumnInfo(name = "private_key_name")
    private String privateKeyName;

    @ColumnInfo(name = "cert_key_name")
    private String certKeyName;

    @ColumnInfo(name = "root_ca_url")
    private String rootCAUrl;

    @ColumnInfo(name = "public_key_url")
    private String publicKeyUrl;

    @ColumnInfo(name = "private_key_url")
    private String privateKeyUrl;

    @ColumnInfo(name = "cert_key_url")
    private String certKeyUrl;

    @ColumnInfo(name = "iot_endpoint")
    private String iotEndpoint;

    public AwsInfoResponse() {}

    @Ignore
    public AwsInfoResponse(
            @NonNull String gatewayUuid,
            String rootCAName,
            String publicKeyName,
            String privateKeyName,
            String certKeyName,
            String rootCAUrl,
            String publicKeyUrl,
            String privateKeyUrl,
            String certKeyUrl,
            String iotEndpoint) {
        this.gatewayUuid = gatewayUuid;
        this.rootCAName = rootCAName;
        this.publicKeyName = publicKeyName;
        this.privateKeyName = privateKeyName;
        this.certKeyName = certKeyName;
        this.rootCAUrl = rootCAUrl;
        this.publicKeyUrl = publicKeyUrl;
        this.privateKeyUrl = privateKeyUrl;
        this.certKeyUrl = certKeyUrl;
        this.iotEndpoint = iotEndpoint;
    }

    @NonNull
    public String getGatewayUuid() {
        return gatewayUuid;
    }

    public void setGatewayUuid(@NonNull String gatewayUuid) {
        this.gatewayUuid = gatewayUuid;
    }

    public String getRootCAName() {
        return rootCAName;
    }

    public String getPublicKeyName() {
        return publicKeyName;
    }

    public String getPrivateKeyName() {
        return privateKeyName;
    }

    public String getCertKeyName() {
        return certKeyName;
    }

    public String getRootCAUrl() {
        return rootCAUrl;
    }

    public String getPublicKeyUrl() {
        return publicKeyUrl;
    }

    public String getPrivateKeyUrl() {
        return privateKeyUrl;
    }

    public String getCertKeyUrl() {
        return certKeyUrl;
    }

    public String getIotEndpoint() {
        return iotEndpoint;
    }

    public void setRootCAName(String rootCAName) {
        this.rootCAName = rootCAName;
    }

    public void setPublicKeyName(String publicKeyName) {
        this.publicKeyName = publicKeyName;
    }

    public void setPrivateKeyName(String privateKeyName) {
        this.privateKeyName = privateKeyName;
    }

    public void setCertKeyName(String certKeyName) {
        this.certKeyName = certKeyName;
    }

    public void setRootCAUrl(String rootCAUrl) {
        this.rootCAUrl = rootCAUrl;
    }

    public void setPublicKeyUrl(String publicKeyUrl) {
        this.publicKeyUrl = publicKeyUrl;
    }

    public void setPrivateKeyUrl(String privateKeyUrl) {
        this.privateKeyUrl = privateKeyUrl;
    }

    public void setCertKeyUrl(String certKeyUrl) {
        this.certKeyUrl = certKeyUrl;
    }

    public void setIotEndpoint(String iotEndpoint) {
        this.iotEndpoint = iotEndpoint;
    }
}
