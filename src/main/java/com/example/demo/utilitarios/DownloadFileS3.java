package com.example.demo.utilitarios;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.BufferedReader;
import java.io.FileReader;

public class DownloadFileS3 {
    public static void download(String[] args) throws IOException {
	String bucketnAME = "group-3-bucket";
	String keyName = "csv_projeto_grupo-3.csv";

AwsCredentialsProvider credentialsProvider = new AwsCredentialsProvider() {
  @Override
  public AwsCredentials resolveCredentials() {
      return new AwsCredentials() {
          @Override
          public String accessKeyId() {
              return System.getenv("AWS_ACCESS_KEY");
          }

          @Override
          public String secretAccessKey() {
              return System.getenv("AWS_SECRET_KEY");
          }
      };
  }
};
	S3Client client = S3Client.builder()
	                        .region(Region.US_EAST_1)
	                        .credentialsProvider(credentialsProvider)
	                        .build();
	
	GetObjectRequest request = GetObjectRequest.builder()
	.bucket(bucketnAME)
	.key(keyName)
	.build();
	
	ResponseInputStream<GetObjectResponse> InputStream = client.getObject(request);

	String fileName  = new File(keyName).getName();
	
	BufferedOutputStream outputStrem = new BufferedOutputStream(new FileOutputStream(fileName));
	
	byte[] buffer = new byte[4096];
	
	int bytesRead = -1;
	
	while ((bytesRead = InputStream.read(buffer)) != -1) {
		outputStrem.write(buffer, 0, bytesRead);
	};
	
	InputStream.close();
	outputStrem.close();
	
	//lendo
	String file = keyName;
	BufferedReader reader = null;
	String line = "";
	
	try {
		reader = new BufferedReader(new FileReader(file));
		while((line = reader.readLine()) != null) {
			System.out.println();
			String[] row = line.split(";");
			for (String string : row) {
				System.out.printf("%-10s ", string);
			}
			System.out.println();
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		try {
			reader.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
		
	}
}

