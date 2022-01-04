package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        //given
        Member member = createMember("회원1");
        Item item = createBook("시골jpa", 10000, 10);
        int orderCount = 2;

        //when
        Long saveId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(saveId);
        Assert.assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, order.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount , order.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다", 10 - orderCount , item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과()throws Exception{
        //given
        Member member = createMember("회원1");
        Item item = createBook("시골jpa", 10000, 10);
        int orderCount = 11;

        //when
        Long saveId = orderService.order(member.getId(), item.getId(), orderCount); //여기서 예외 발생해야함

        //then
        fail("재고 수량 부족 예외가 발생하지 않았습니다");
    }

    @Test
    public void 주문취소(){
        //given
        Member member = createMember("회원1");
        Item item = createBook("시골jpa", 10000, 10);
        int orderCount = 5;
        Long saveId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(saveId);

        //then
        Order order = orderRepository.findOne(saveId);
        Assert.assertEquals("주문 상태가 취소이어야 한다", OrderStatus.CANCEL , order.getStatus());
        Assert.assertEquals("재고 수량이 10이어야 한다", 10 , item.getStockQuantity());
    }




    private Member createMember(String name){
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "강가", "123-123"));
        memberService.join(member);
        return member;
    }

    private Item createBook(String name, int price, int stockQuantity){
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        itemService.saveItem(item);
        return item;
    }
}