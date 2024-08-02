package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL은 enum이 1, 2, 3 이렇게 숫자로 들어감, 중간에 새로운 enum값이 들어오면 기존 로직이 장애가 남
                                 // STRING은 이름으로
    private DeliveryStatus status; // READY, COMP
}
