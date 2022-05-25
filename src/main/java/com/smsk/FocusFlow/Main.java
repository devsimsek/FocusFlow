package com.smsk.FocusFlow;

public class Main {
    public static void main(String[] args) {
        PlaySound.play("Submarine.wav");
        Benchmark.start(0);
        new FocusFlow(true).run();
        Benchmark.stop(0);
        Utils.log("Application Runtime: " + Benchmark.result(0), "BENCHMARK");
    }
}
