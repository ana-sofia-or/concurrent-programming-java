import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private GUI resourceMonitorGUI;
    private final BlockingQueue<Map.Entry<String,Double>> queue;
    private Temporal lastProducedTime;
    public Consumer(BlockingQueue<Map.Entry<String,Double>> queue,GUI resourceMonitorGUI) {
        this.queue = queue;
        this.resourceMonitorGUI = resourceMonitorGUI;
        this.lastProducedTime = Instant.now();
    }
    public Temporal getLastProducedTime() {
        return lastProducedTime;
    }

    @Override
    public void run() {

        while(true){
            try {
                Map.Entry<String,Double> result = queue.poll(5,TimeUnit.SECONDS);
                System.out.println("consumer");
                switch (result.getKey()) {
                    case "CpuProducer" -> {
                        if (result.getValue() > 80) {
                            resourceMonitorGUI.addAlert("CPU load is over 80%!");
                        }
                    }
                    case "FreeRAMProducer" -> {
                        if (result.getValue() < 10) {
                            resourceMonitorGUI.addAlert("Free RAM is lower than 10%!");
                        }
                    }
                    case "FreeDiskSpaceProducer" -> {
                        if (result.getValue() < 20) {
                            resourceMonitorGUI.addAlert("Free Disk Space is lower than 20%");
                        }
                    }
                }

            }catch (Exception e){
                break;
            }
        }
    }
}
