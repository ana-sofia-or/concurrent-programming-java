import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class CpuProducer implements Runnable {
    private final String key = "CpuProducer";
    private final BlockingQueue<Map.Entry<String,Double>> queue;
    private Temporal lastProducedTime;
    public CpuProducer(BlockingQueue<Map.Entry<String,Double>> queue){
        this.queue = queue;
    }
    public Temporal getLastProducedTime() {
        return lastProducedTime;
    }

    @Override
    public void run() {
        try {
            double cpuLoad = Utils.getCpuLoad();
            System.out.println("cpu: " + cpuLoad);
            queue.offer(Map.entry(key,cpuLoad), 5, TimeUnit.SECONDS);
            this.lastProducedTime = Instant.now();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class FreeRAMProducer implements Runnable {
    private final String key = "FreeRAMProducer";
    private final BlockingQueue<Map.Entry<String,Double>> queue;
    private Temporal lastProducedTime;
    public FreeRAMProducer(BlockingQueue<Map.Entry<String,Double>> queue){
        this.queue = queue;
    }
    public Temporal getLastProducedTime() {
        return lastProducedTime;
    }
    @Override
    public void run() {
        try {
            double freeRAM = Utils.getFreeRAM();
            System.out.println("free ram: " + freeRAM);
            queue.offer(Map.entry(key,freeRAM), 5, TimeUnit.SECONDS);
            this.lastProducedTime = Instant.now();
        } catch (Exception e) {
        }
    }
}

class FreeDiskSpaceProducer implements Runnable {
    private final String key = "FreeDiskSpaceProducer";
    private final BlockingQueue<Map.Entry<String,Double>> queue;
    private Temporal lastProducedTime;
    public FreeDiskSpaceProducer(BlockingQueue<Map.Entry<String,Double>> queue){
        this.queue = queue;
    }

    public Temporal getLastProducedTime() {
        return lastProducedTime;
    }

    @Override
    public void run() {
        try {
            double freeDiskSpace = Utils.getFreeDiskSpace();
            System.out.println("free disk: " + freeDiskSpace);
            queue.offer(Map.entry(key,freeDiskSpace), 5, TimeUnit.SECONDS);
            this.lastProducedTime = Instant.now();
        } catch (Exception e) {

        }
    }


}

