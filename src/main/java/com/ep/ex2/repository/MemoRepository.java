package com.ep.ex2.repository;

import com.ep.ex2.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

// repository는 기본적으로 쿼리를 작성하지 않아도 된다.
// repository는 인터페이스로서 상속만 많아도 bean에 등록이 된다.
public interface MemoRepository extends JpaRepository<Memo, Long> {

    // 해당 예제에서 사용된 메서드는 쿼리 메서드로 메서드명에 쿼리의 속성이 들어가 있다.
    // 기본적인 쿼리는 JpaRepository에서 제공하는 메서드를 사용하면 된다.
    // 하지만 성능을 위해서 조인이나 포함관계가 있을 경우에는 사용을 많이 하지 않는다.

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // JpaRpository가 제공하는 강력한 기능 중하나는 페이징 처리이다.
    // 쿼리메서드는 Pageable과 같이 사용할 수 있다.
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);
}
