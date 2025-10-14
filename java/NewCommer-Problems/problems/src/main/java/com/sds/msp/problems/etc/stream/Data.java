package com.sds.msp.problems.etc.stream;

import java.time.Instant;
import java.util.List;

record Data(
        String id,
        int basis1,
        int basis2,
        int basis3,
        Instant startedAt,
        Instant endedAt,
        List<Integer> numbers
) {
}
