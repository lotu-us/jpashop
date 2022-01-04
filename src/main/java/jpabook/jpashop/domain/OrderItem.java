package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //OrderItem(다) : Item(1)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //OrderItem(다) : Order(1)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //생성메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    //조회로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
