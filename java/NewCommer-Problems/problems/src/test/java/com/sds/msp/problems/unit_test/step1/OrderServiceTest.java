package com.sds.msp.problems.unit_test.step1;

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

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final List<Coffee> COFFEES = List.of(
            new Coffee("americano", 1000, "americano"),
            new Coffee("latte", 1500, "latte")
    );
    private static final Integer ORDER_NUMBER = 2;

    @InjectMocks
    private OrderService underTest;
    @Mock
    private OrderDao orderDao;
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
        // when
        underTest.createOrder(ORDER_NUMBER, COFFEES);
        // then
        verify(orderDao).save(orderCaptor.capture());

        final Order order = orderCaptor.getValue();

        assertThat(order.orderNumber()).isEqualTo(ORDER_NUMBER);
        assertThat(order.totalCost()).isEqualTo(totalCost);
        assertThat(order.createOrderTime()).isNotNull();
    }
}
