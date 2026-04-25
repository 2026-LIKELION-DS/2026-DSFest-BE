package com.ds.dsfest.domain.photocontest.entity;

import com.ds.dsfest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo_votes")
public class PhotoVote extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "photo_entry_id", nullable = false)
  private PhotoEntry photoEntry;

  @Column(nullable = false, length = 64, unique = true)
  private String voterKey;

  public PhotoVote(PhotoEntry photoEntry, String voterKey) {
      this.photoEntry = photoEntry;
      this.voterKey = voterKey;
  }
}
