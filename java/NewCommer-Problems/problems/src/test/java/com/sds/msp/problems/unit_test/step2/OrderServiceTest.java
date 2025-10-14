package com.sds.msp.problems.unit_test.step2;

import com.sds.msp.problems.unit_test.Coffee;
import com.sds.msp.problems.unit_test.Order;
import com.sds.msp.problems.unit_test.OrderDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static com.sds.msp.problems.utils.RandomUtils.randomInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final List<Coffee> COFFEES = List.of(
            new Coffee("americano", 1000, "americano"),
            new Coffee("latte", 1500, "latte")
    );

    private TestOrderService underTest;
    private Instant createdOrderTime;
    @Mock
    private OrderDao orderDao;
    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void setUp() {
        createdOrderTime = randomInstant();
        underTest = new TestOrderService(orderDao, createdOrderTime);
    }

    @DisplayName("주문을 생성하고 주문 내역에 저장한다.")
    @Test
    void createOrder() {
        // given
        final int totalCost =
                COFFEES.stream()
                        .mapToInt(Coffee::price)
                        .reduce(0, Integer::sum);
        // when
        underTest.createOrder(COFFEES.size(), COFFEES);
        // then
        verify(orderDao).save(orderCaptor.capture());
        Order order = orderCaptor.getValue();

        assertAll("order status check",
                () -> assertThat(order.orderNumber()).isEqualTo(COFFEES.size()),
                () -> assertThat(order.totalCost()).isEqualTo(totalCost),
                () -> assertThat(order.createOrderTime()).isEqualTo(createdOrderTime)
        );
    }

    private static class TestOrderService extends OrderService {
        private final Instant createOrderTime;

        public TestOrderService(
                final OrderDao orderDao,
                final Instant createOrderTime) {
            super(orderDao);
            this.createOrderTime = createOrderTime;
        }

        @Override
        protected Instant getNow() {
            return createOrderTime;
        }
    }
}
