package animal_shop.shop.item.repository;

import animal_shop.shop.item.ItemSellStatus;
import animal_shop.shop.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
//    Page<Item> findBySpecies(Pageable pageable, String species);
    Page<Item> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Page<Item> findByMemberId(Long memberId,Pageable pageable);

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.thumbnail_url " + // thumbnail_url 컬렉션을 fetch join으로 가져옴
            "WHERE i.species = :species " +
            "AND i.itemSellStatus != :itemSellStatus " +
            "ORDER BY i.createdDate DESC") // 날짜 역순으로 정렬
    Page<Item> findBySpecies(
            @Param("species") String species,
            @Param("itemSellStatus") ItemSellStatus itemSellStatus,
            Pageable pageable);

    // fetch join을 사용한 쿼리
    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.thumbnail_url " +  // thumbnail_url 컬렉션을 fetch join으로 가져옴
            "WHERE i.species = :species " +
            "AND i.category = :category " +
            "AND i.itemSellStatus != :itemSellStatus ")
    Page<Item> findBySpeciesAndCategoryWithThumbnails(
            @Param("species") String species,
            @Param("category") String category,
            @Param("itemSellStatus") ItemSellStatus itemSellStatus,
            Pageable pageable);

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.thumbnail_url " +  // thumbnail_url 컬렉션을 fetch join으로 가져옴
            "WHERE i.species = :species " +
            "AND i.category = :category " +
            "AND i.detailed_category = :detailedCategory " +
            "AND i.itemSellStatus != :itemSellStatus ")
    Page<Item> findBySpeciesCategoryAndDetailedCategoryWithThumbnails(
            @Param("species") String species,
            @Param("category") String category,
            @Param("detailedCategory") String detailedCategory,
            @Param("itemSellStatus") ItemSellStatus itemSellStatus,
            Pageable pageable);

    //종과 카테고리와, 디테일 카테고리를 통해 이름이 비슷한 아이템 검색
    @EntityGraph(attributePaths = {"options"})
    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.thumbnail_url " +
            "WHERE i.species = :species " +
            "AND i.category = :category " +
            "AND i.detailed_category = :detailedCategory " +
            "AND LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Item> findBySpeciesAndCategoryAndDetailedCategoryWithThumbnailsContainingIgnoreCase(
            @Param("species") String species,
            @Param("category") String category,
            @Param("detailedCategory") String detailedCategory,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("SELECT i FROM Item i " +
            "WHERE i.itemSellStatus = :itemSellStatus")
    Page<Item> findByItemSellStatus(
            @Param("itemSellStatus") ItemSellStatus itemSellStatus,
            Pageable pageable);


    @EntityGraph(attributePaths = {"options", "member"})
    @Query("SELECT i FROM Item i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Item> findByItemNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);


    @EntityGraph(attributePaths = {"options"})
    @Query("SELECT i FROM Item i WHERE i.member.nickname LIKE %:searchTerm%")
    Page<Item> findByMemberNicknameContainingWithOptions(@Param("searchTerm") String searchTerm, Pageable pageable);


    // 이름으로 검색 (엔티티만 반환)
    @Query("SELECT i FROM Item i WHERE i.member.nickname LIKE %:searchTerm%")
    Page<Item> findByMemberNicknameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    // 전체 아이템 조회
    @Query("SELECT i FROM Item i " +
            "WHERE i.itemSellStatus != :itemSellStatus")
    Page<Item> findAllSearch(
            @Param("itemSellStatus") ItemSellStatus itemSellStatus,
            Pageable pageable);

}
