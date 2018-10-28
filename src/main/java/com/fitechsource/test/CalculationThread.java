package com.fitechsource.test;

import java.util.Set;
import java.util.logging.Logger;

public class CalculationThread extends Thread {
    private Set<Double> target;
    private int num;
    private boolean threadWorkingError;
    private static final Logger LOGGER = Logger.getLogger(CalculationThread.class.getName());

    public CalculationThread(Set<Double> target) {
        this.target = target;
    }

    public Set<Double> getTarget() {
        return target;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isThreadWorkingError() {
        return threadWorkingError;
    }

    @Override
    public void run() {
        try {
            synchronized (target) {
                target = TestCalc.calculate(num);
                LOGGER.info("Calculated by[" + this.getName() + "] num=" + num + ", res=" + target);
            }
        } catch (TestException e) {
            LOGGER.warning("Exception while calculation: " + e);
            threadWorkingError = true;
        }
    }
}