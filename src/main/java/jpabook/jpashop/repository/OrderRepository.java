package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long orderId){
        return em.find(Order.class, orderId);
    }

//    public List<Order> findAll(OrderSearch orderSearch){
//        String jpql = "select";
//        query dsl같은 동적쿼리 프레임워크를 사용해서 구현하자
//        cv czfdsfsdafdsfa   return em.createQuery(jpql, Order.class)
//                .setMaxResults(100)
//                .getResultList();
//    }
}
