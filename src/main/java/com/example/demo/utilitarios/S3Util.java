package com.example.demo.utilitarios;

import java.io.IOException;
import java.io.InputStream;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
 
public class S3Util {
    private static final String BUCKET = "your-bucket-name";//colocar nome do nosso bucket
     
    public static void uploadFile(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(fileName)// colocar nome do nosso arquivo
                            .acl("public-read")
                            .build();
         
        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
    /*
    // Caso queira executar algumas lógicas personalizadas que dependam da existência do arquivo carregado,
    S3Waiter waiter = client.waiter();
    HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(fileName)
                        .build();
     
    WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
     
    waitResponse.matched().response().ifPresent(x -> {

  });*/
}