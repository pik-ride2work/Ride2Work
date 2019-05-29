package com.pik.backend.util;

import java.util.concurrent.CompletableFuture;
import org.jooq.DSLContext;
import org.jooq.TransactionalRunnable;

public class DSLWrapper {

    private DSLWrapper() {
    }

    public static void transaction(DSLContext dsl, CompletableFuture future,
                                   TransactionalRunnable runnable) {
        try {
            dsl.transaction(runnable);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
    }
}
