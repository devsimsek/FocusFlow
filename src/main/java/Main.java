public class Main {
    public static void main(String[] args) {
        Benchmark.start(0);
        new FocusFlow(false).run();
        Benchmark.stop(0);
        Utils.log("Application Runtime: " + Benchmark.result(0), "BENCHMARK");
    }
}
