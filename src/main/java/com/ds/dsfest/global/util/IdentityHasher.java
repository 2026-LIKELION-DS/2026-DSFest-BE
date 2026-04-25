package com.ds.dsfest.global.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class IdentityHasher {

    /**
     *"공백/케이스/노이즈 일관화"
     */
    public static String normalize(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", "").toLowerCase();
    }

    /**
     *"학번|이름|신분" 복합키 생성 및 해시
     */
    public static String hashIdentity(String studentId, String name, String status) {
        String combined = normalize(studentId) + "|" + normalize(name) + "|" + normalize(status);
        return sha256(combined);
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("해시 생성 중 오류 발생", e);
        }
    }
}
