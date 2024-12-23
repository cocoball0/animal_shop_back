package animal_shop.tools.abandoned_animal.repository;

import animal_shop.community.member.entity.Member;
import animal_shop.tools.abandoned_animal.entity.InterestAnimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestAnimalRepository extends JpaRepository<InterestAnimal,Long> {
    Page<InterestAnimal> findByMember(Member member, Pageable pageable);

    boolean existsByMemberAndDesertionNo(Member member, String desertion_no);

    List<InterestAnimal> findByDesertionNo(String desertionNo);


    //관심 동물 레포지토리
}
