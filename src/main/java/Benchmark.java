import java.util.ArrayList;

public class Benchmark {
    protected static ArrayList<Long> processes = new ArrayList<>();

    public static void start(int processID) {
        if (processID >= processes.size()) {
            processes.add(processID, System.currentTimeMillis());
        } else {
            throw new Error("Sorry, Process already exists");
        }
    }

    public static void stop(int processID) {
        if (processID >= 0 && processID < processes.size()) {
            if (processes.get(processID) != null) {
                processes.set(processID, System.currentTimeMillis() - processes.get(processID));
            } else {
                throw new Error("Process does not exists.");
            }
        } else {
            throw new Error("Process does not exists.");
        }
    }

    public static long result(int processID) {
        if (processID >= 0 && processID < processes.size()) {
            if (processes.get(processID) != null) {
                return processes.get(processID);
            } else {
                throw new Error("Process does not exists.");
            }
        } else {
            throw new Error("Process does not exists.");
        }
    }
}
