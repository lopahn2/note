package com.sds.msp.problems.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RandomUtils {
    public static final int RANDOM_ALPHANUMERIC_DEFAULT_LENGTH = 8;

    private static SecureRandom randomInstance;

    private RandomUtils() {
    }

    private static Random getRandomInstance() {
        if (randomInstance == null) {
            randomInstance = new SecureRandom();
        }
        return randomInstance;
    }

    public static String randomAlphanumeric() {
        return RandomStringUtils.secure()
                .nextAlphanumeric(RANDOM_ALPHANUMERIC_DEFAULT_LENGTH);
    }

    public static int randomInt() {
        return getRandomInstance().nextInt();
    }

    public static int randomInt(final int origin, final int bound) {
        return getRandomInstance().nextInt(origin, bound);
    }

    public static Instant randomInstant() {
        return Instant.ofEpochMilli(Math.abs(randomInt()));
    }

    public static <T> List<T> generateList(
            final long length,
            final Supplier<T> supplier) {
        return generateStream(supplier)
                .limit(length)
                .toList();
    }

    private static <T> Stream<T> generateStream(
            final Supplier<T> supplier) {
        return Stream.generate(supplier);
    }
}
