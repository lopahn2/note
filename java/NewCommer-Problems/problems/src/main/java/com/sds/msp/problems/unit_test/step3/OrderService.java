package com.sds.msp.problems.unit_test.step3;


import com.sds.msp.problems.unit_test.Coffee;
import com.sds.msp.problems.unit_test.Order;
import com.sds.msp.problems.unit_test.OrderDao;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

class OrderService {
    private final OrderDao orderDao;
    private final Clock clock;

    OrderService(
            final OrderDao orderDao,
            final Clock clock) {
        this.orderDao = orderDao;
        this.clock = clock;
    }

    void createOrder(
            int orderNumber,
            final List<Coffee> coffees) {
        final int totalCost =
                coffees.stream()
                        .mapToInt(Coffee::price)
                        .reduce(0, Integer::sum);

        final var order = new Order(
                orderNumber,
                totalCost,
                getNow()
        );
        orderDao.save(order);
    }

    private Instant getNow() {
        return clock.instant();
    }
}
