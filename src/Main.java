import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException
    {
        int numConsumers = 3;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3 + numConsumers);
        GUI resourceMonitorGUI = GUI.newInstance(scheduler);
        BlockingQueue<Map.Entry<String,Double>> queue = new ArrayBlockingQueue<>(100);

        CpuProducer cpuProducer = new CpuProducer(queue);
        FreeRAMProducer freeRAMProducer = new FreeRAMProducer(queue);
        FreeDiskSpaceProducer freeDiskSpaceProducer = new FreeDiskSpaceProducer(queue);


        scheduler.scheduleAtFixedRate(cpuProducer, 0, 100, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(freeRAMProducer, 0, 100, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(freeDiskSpaceProducer, 0, 100, TimeUnit.MILLISECONDS);


        ArrayList<Consumer> consumers = new ArrayList<>();

        for (int i = 0; i < numConsumers; i++) {
            var consumer = new Consumer(queue,resourceMonitorGUI);
            consumers.add(consumer);
            scheduler.execute(consumer);
        }

        MonitorProducersAndConsumers monitoringThread = new MonitorProducersAndConsumers(cpuProducer, freeRAMProducer, freeDiskSpaceProducer, consumers, scheduler, queue, resourceMonitorGUI);
        monitoringThread.setDaemon(true);
        monitoringThread.start();


    }
}

