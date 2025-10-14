package com.sds.msp.problems.unit_test.step2;

import com.sds.msp.problems.unit_test.Coffee;
import com.sds.msp.problems.unit_test.Order;
import com.sds.msp.problems.unit_test.OrderDao;

import java.time.Instant;
import java.util.List;

class OrderService {
    private final OrderDao orderDao;

    OrderService(final OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    void createOrder(
            final int orderNumber,
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

    protected Instant getNow() {
        return Instant.now();
    }
}
