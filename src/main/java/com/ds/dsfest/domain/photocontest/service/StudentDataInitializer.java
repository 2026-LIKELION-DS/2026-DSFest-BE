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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentDataInitializer implements ApplicationRunner {

    private final VerifiedStudentRepository verifiedStudentRepository;

    /**
     * 환경변수 STUDENT_DATA_PATH를 읽어오며, 기본값은 리눅스 경로로 설정
     */
    @Value("${STUDENT_DATA_PATH:/home/ubuntu/data/}")
    private String csvPath;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        /**
         * DB에 이미 데이터가 있으면 실행하지 않음
         */
        if (verifiedStudentRepository.count() > 0) return;

        List<VerifiedStudent> allVerified = new ArrayList<>();

        /**
         * 경로 뒤에 /가 없을 수도 있으니 체크하는 로직
         */
        String formattedPath = csvPath.endsWith("/") || csvPath.endsWith("\\") ? csvPath : csvPath + File.separator;

        processAndSecurityDelete(formattedPath + "current_students.csv", "재학생", allVerified);
        processAndSecurityDelete(formattedPath + "leave_students.csv", "휴학생", allVerified);

        if (!allVerified.isEmpty()) {
            verifiedStudentRepository.saveAll(allVerified);
            log.info("RDS DB에 해시 데이터 저장 완료. 원본 파일은 서버에서 삭제되었습니다.");
        }
    }

    private void processAndSecurityDelete(String filePath, String status, List<VerifiedStudent> list) {
        File file = new File(filePath);
        if (!file.exists()) {
            log.warn("파일을 찾을 수 없습니다: {}", filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String hash = IdentityHasher.hashIdentity(data[0].trim(), data[1].trim(), status);
                    list.add(new VerifiedStudent(hash));
                }
            }
        } catch (Exception e) {
            log.error("파일 처리 중 오류 발생: {}", e.getMessage());
        } finally {
            /**
             * 읽기가 끝난 후 실제 파일 삭제 (단, classpath 설정이 아닐 때만)
             */
            if (file.exists() && !csvPath.contains("classpath")) {
                if (file.delete()) {
                    log.info("보안을 위해 원본 파일이 삭제되었습니다: {}", filePath);
                }
            }
        }
    }
}
