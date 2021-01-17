package com.ep.ex2.repository;

import com.ep.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    // 리포지토리를 주입한다.
    @Autowired
    MemoRepository memoRepository;


    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){

        // 데이터베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=================================");
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    // getOne()은 실제 객체가 필요한 순간(사용이 되는 순간)에서야 쿼리가 날라간다.
    @Transactional
    @Test
    public void testSelect2(){
        //데이터 베이스에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("=========================");

        System.out.println(memo);

    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("Update Test").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno);

    }

    @Test
    public void testPageDefault(){
        // 1페이지 10개
        Pageable pageable = PageRequest.of(0,10);

        // pagable을
        Page<Memo> result = memoRepository.findAll(pageable);
        
        System.out.println(result);

        System.out.println("================================");

        System.out.println("Total Pages : " + result.getTotalPages()); // 총 몇 페이지

        System.out.println("Total Count : " + result.getTotalElements()); //  전체 개수

        System.out.println("Page Number : " + result.getNumber()); // 현재 페이지 번호 0부터 시작

        System.out.println("Page Size : " + result.getSize()); // 페이지당 데이터 개수

        System.out.println("has next page? : " + result.hasNext()); // 다음 페이지 존재 여부

        System.out.println("first page? : " + result.isFirst()); // 시작 페이지(0) 여부

        System.out.println("-----------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.                               println(memo);
        }
    }

    @Test
    public void testSort(){

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        // 우선순위가 앞에 온다.
        Sort sortAll = sort1.and(sort2); // and를 이용한 연결

        Pageable pageable = PageRequest.of(0,10,sortAll); // 결합된 정렬 조건 사용

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }

    }

    @Test
    public void testQueryMethodWithPagable(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);

        result.get().forEach(memo -> System.out.println(memo));

    }

    // delete는 기본이 롤백처리이다.
    // 실제 개발에서는 deleteBy는 많이 사용하지 않는다. 왜냐하면 객체를 하나씩 삭제하기 때문이다.
    // 여러 작업이 동시에 이뤄지기 때문에 Transaction이 꼭 필요하다.
    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {

        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}
