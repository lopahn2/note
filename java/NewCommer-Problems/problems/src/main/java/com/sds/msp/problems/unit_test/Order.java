package com.sds.msp.problems.unit_test;

import java.time.Instant;

public record Order(
        int orderNumber,
        int totalCost,
        Instant createOrderTime
) {
}
