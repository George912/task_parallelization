package com.fitechsource.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Should be improved to reduce calculation time.
 * <p>
 * Change it or create new one. (max threads count is src.main.tmp.TestConsts#MAX_THREADS)
 * Do not use `executors`, only plain threads.
 */
public class Test {
    private static Set<Double> res = new HashSet<>();
    private static Queue<Thread> calculationThreadQueue = new LinkedList<>();
    private static boolean threadWorkingError;
    private static final Logger LOGGER = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws TestException {
        calculationThreadQueueInit();

        for (int i = 0; i < TestConsts.N; i++) {
            if (threadWorkingError) {
                break;
            } else {
                if (calculationThreadQueue.isEmpty()) {
                    calculationThreadQueueInit();
                }
                Thread calculationThread = calculationThreadQueue.poll();
                ((CalculationThread) calculationThread).setNum(i);
                calculationThread.start();
                try {
                    calculationThread.join();
                    res.addAll(((CalculationThread) calculationThread).getTarget());
                    threadWorkingError = ((CalculationThread) calculationThread).isThreadWorkingError();
                } catch (InterruptedException e) {
                    LOGGER.warning("Exception while joining: " + e);
                }
            }
        }
        LOGGER.info("Result: " + res);
    }

    private static void calculationThreadQueueInit() {
        LOGGER.info("call calculationThreadQueueInit()");
        for (int i = 0; i < TestConsts.MAX_THREADS; i++) {
            calculationThreadQueue.add(new CalculationThread(res));
        }
        LOGGER.info("calculationThreadQueue: " + calculationThreadQueue);
    }
}
