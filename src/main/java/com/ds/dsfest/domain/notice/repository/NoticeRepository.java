package com.ds.dsfest.domain.notice.repository;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.entity.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  List<Notice> findAllByOrderByCreatedAtDesc();
  List<Notice> findAllByCategoryOrderByCreatedAtDesc(NoticeCategory category);

  Optional<Notice> findTopByUrgentTrueOrderByCreatedAtDesc();

  @Query(
      "SELECT n FROM Notice n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword% ORDER BY n.createdAt DESC")
  List<Notice> searchByKeyword(@Param("keyword") String keyword);

  List<Notice> findTop3ByOrderByViewCountDesc();

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Notice n SET n.viewCount = n.viewCount + 1 WHERE n.id = :id")
  void incrementViewCount(@Param("id") Long id);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Notice n SET n.urgent = false WHERE n.urgent = true")
  void clearAllUrgent();
}
