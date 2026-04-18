package com.ds.dsfest.domain.notice.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notices")
public class Notice extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private NoticeCategory category;

  @Column(name = "is_urgent", nullable = false)
  private boolean urgent;

  @Column(nullable = false)
  private int viewCount;

  @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("imageOrder ASC")
  private List<NoticeImage> images = new ArrayList<>();

  public static Notice create(
      String title, String content, NoticeCategory category, boolean urgent) {
    Notice notice = new Notice();
    notice.title = title;
    notice.content = content;
    notice.category = category;
    notice.urgent = urgent;
    return notice;
  }

  public void update(String title, String content, NoticeCategory category, boolean urgent) {
    this.title = title;
    this.content = content;
    this.category = category;
    this.urgent = urgent;
  }

  public void clearUrgent() {
    this.urgent = false;
  }

  public void addImage(String imageUrl, int order) {
    this.images.add(NoticeImage.create(this, imageUrl, order));
  }

  public void clearImages() {
    this.images.clear();
  }
}
