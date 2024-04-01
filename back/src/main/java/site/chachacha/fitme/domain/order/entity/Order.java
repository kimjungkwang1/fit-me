package site.chachacha.fitme.domain.order.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.order.OrderStatus;
import site.chachacha.fitme.domain.order.exception.CannotCancelOrderException;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @Builder
    private Order(Member member, List<OrderProduct> orderProducts) {
        this.member = member;
        this.member.addOrder(this);

        this.orderProducts = orderProducts;
        this.orderProducts
            .forEach(orderProduct -> orderProduct.setOrder(this));
    }

    // == 비즈니스 로직 == //
    public void cancel() {
        if (this.status != OrderStatus.ORDERED) {
            throw new CannotCancelOrderException("주문이 완료됐거나, 취소된 주문은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCEL;
    }
}
