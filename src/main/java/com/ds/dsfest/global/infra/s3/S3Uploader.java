package com.ds.dsfest.global.infra.s3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

  private final S3Client s3Client;
  private final S3Properties s3Properties;

  /**
   * S3에 파일 업로드
   *
   * @param file 업로드할 파일
   * @param dirName S3 저장 경로 (예: "artists", "notices", "foodtrucks")
   * @return 업로드된 파일의 S3 URL
   */
  public String upload(MultipartFile file, String dirName) throws IOException {
    String originalFilename =
        file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown";
    String key = dirName + "/" + UUID.randomUUID() + "_" + originalFilename;

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(s3Properties.getBucket())
            .key(key)
            .contentType(file.getContentType())
            .contentLength(file.getSize())
            .build(),
        RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    return buildFileUrl(key);
  }

  /**
   * S3에서 파일 삭제
   *
   * @param fileUrl 삭제할 파일의 S3 URL
   */
  public void delete(String fileUrl) {
    if (fileUrl == null || !fileUrl.contains(".amazonaws.com/")) {
      log.warn("S3 삭제 건너뜀 - 유효하지 않은 URL: {}", fileUrl);
      return;
    }
    String key = extractKey(fileUrl);

    s3Client.deleteObject(
        DeleteObjectRequest.builder().bucket(s3Properties.getBucket()).key(key).build());
  }

  private String buildFileUrl(String key) {
    return "https://"
        + s3Properties.getBucket()
        + ".s3."
        + s3Properties.getRegion()
        + ".amazonaws.com/"
        + key;
  }

  private String extractKey(String fileUrl) {
    return fileUrl.substring(fileUrl.indexOf(".amazonaws.com/") + ".amazonaws.com/".length());
  }
}
