package com.sds.msp.problems.unit_test.step3;

import com.sds.msp.problems.unit_test.Coffee;
import com.sds.msp.problems.unit_test.Order;
import com.sds.msp.problems.unit_test.OrderDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final List<Coffee> COFFEES = List.of(
            new Coffee("americano", 1000, "americano"),
            new Coffee("latte", 1500, "latte")
    );

    @InjectMocks
    private OrderService underTest;

    @Mock
    private OrderDao orderDao;
    @Mock
    private Clock clock;
    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @DisplayName("주문을 생성하고 주문 내역에 저장한다.")
    @Test
    void createOrder() {
        // given
        final int totalCost =
                COFFEES.stream()
                        .mapToInt(Coffee::price)
                        .reduce(0, Integer::sum);
        final Instant createdOrderTime = Instant.parse("2024-01-01T00:00:00Z");
        given(clock.instant()).willReturn(createdOrderTime);
        // when
        underTest.createOrder(COFFEES.size(), COFFEES);
        // then
        verify(orderDao).save(orderCaptor.capture());
        final Order order = orderCaptor.getValue();

        assertAll("order status check",
                () -> assertThat(order.orderNumber()).isEqualTo(COFFEES.size()),
                () -> assertThat(order.totalCost()).isEqualTo(totalCost),
                () -> assertThat(order.createOrderTime()).isEqualTo(createdOrderTime)
        );
    }
}
