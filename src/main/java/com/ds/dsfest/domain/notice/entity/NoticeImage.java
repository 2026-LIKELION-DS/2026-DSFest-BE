package com.ds.dsfest.domain.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notice_images")
public class NoticeImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notice_id", nullable = false)
  private Notice notice;

  @Column(nullable = false, length = 500)
  private String imageUrl;

  @Column(nullable = false)
  private int imageOrder;

  public static NoticeImage create(Notice notice, String imageUrl, int imageOrder) {
    NoticeImage image = new NoticeImage();
    image.notice = notice;
    image.imageUrl = imageUrl;
    image.imageOrder = imageOrder;
    return image;
  }
}
