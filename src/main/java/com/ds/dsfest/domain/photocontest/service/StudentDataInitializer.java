package com.ds.dsfest.domain.photocontest.service;

import com.ds.dsfest.domain.photocontest.entity.VerifiedStudent;
import com.ds.dsfest.domain.photocontest.repository.VerifiedStudentRepository;
import com.ds.dsfest.global.util.IdentityHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentDataInitializer implements ApplicationRunner {

    private final VerifiedStudentRepository verifiedStudentRepository;

    @Value("${STUDENT_DATA_PATH:/home/ubuntu/data/}")
    private String csvPath;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (verifiedStudentRepository.count() > 0) return;

        List<VerifiedStudent> allVerified = new ArrayList<>();
        String formattedPath = csvPath.endsWith("/") || csvPath.endsWith("\\") ? csvPath : csvPath + File.separator;

        processFile(formattedPath + "current_students.csv", "재학생", allVerified);
        processFile(formattedPath + "leave_students.csv", "휴학생", allVerified);

        if (!allVerified.isEmpty()) {
            verifiedStudentRepository.saveAll(allVerified);
            log.info("RDS DB에 해시 데이터 저장 완료.");

            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    deleteSecurityFiles(formattedPath);
                }
            });
        }
    }

    private void deleteSecurityFiles(String path) {
        String[] fileNames = {"current_students.csv", "leave_students.csv"};
        for (String fileName : fileNames) {
            File file = new File(path + fileName);
            if (file.exists() && !csvPath.contains("classpath")) {
                if (file.delete()) {
                    log.info("보안 삭제 완료: {}", fileName);
                }
            }
        }
    }

    private void processFile(String filePath, String status, List<VerifiedStudent> list) {
        File file = new File(filePath);
        if (!file.exists()) {
            log.warn("파일을 찾을 수 없습니다: {}", filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String header = br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 2) continue; // 데이터가 비어있거나 너무 짧은 줄은 무시 (에러 방어)

                String hash = null;

                /**
                 * 재학생/휴학생 여부에 따라 인덱스 다르게 읽어옴.
                 */
                if (status.equals("재학생")) {
                    hash = IdentityHasher.hashIdentity(data[0].trim(), data[1].trim(), status); // 재학생: data[0]=학번, data[1]=이름
                } else if (status.equals("휴학생")) {
                    if (data.length >= 3) {
                        hash = IdentityHasher.hashIdentity(data[1].trim(), data[2].trim(), status); // 휴학생: data[1]=학번, data[2]=이름
                    }
                }

                if (hash != null) {
                    list.add(new VerifiedStudent(hash));
                }
            }
        } catch (Exception e) {
            log.error("파일 처리 중 치명적 오류 발생: {}", filePath);
            throw new IllegalStateException("학생 데이터 파일 처리 실패: " + filePath, e);
        }
    }
}
