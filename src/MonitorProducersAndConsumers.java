import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorProducersAndConsumers extends Thread{
    private CpuProducer cpuProducer;
    private  FreeRAMProducer freeRAMProducer;
    private  FreeDiskSpaceProducer freeDiskSpaceProducer;
    private  ArrayList<Consumer> consumers;
    private final ScheduledExecutorService scheduler;
    private final BlockingQueue<Map.Entry<String,Double>> queue;
    private final  GUI resourceMonitorGUI;
    public MonitorProducersAndConsumers(CpuProducer cpuProducer, FreeRAMProducer freeRAMProducer, FreeDiskSpaceProducer freeDiskSpaceProducer, ArrayList<Consumer> consumers, ScheduledExecutorService scheduler, BlockingQueue<Map.Entry<String, Double>> queue, GUI resourceMonitorGUI) {
        this.cpuProducer = cpuProducer;
        this.freeRAMProducer = freeRAMProducer;
        this.freeDiskSpaceProducer = freeDiskSpaceProducer;
        this.consumers = consumers;
        this.scheduler = scheduler;
        this.queue = queue;
        this.resourceMonitorGUI = resourceMonitorGUI;
    }

    @Override
    public void run() {

        while(Main.isRunning){
            try {
                Thread.sleep(1000);
                Instant currentTime = Instant.now();
                if (cpuProducer.getLastProducedTime() != null && Duration.between(cpuProducer.getLastProducedTime(), currentTime).getSeconds() >= 10) {
                    System.out.println("CpuProducer stopped working. Starting a new one...");
                    resourceMonitorGUI.addAlert("CpuProducer stopped working. Starting a new one...");
                    var newCpuProducer = new CpuProducer(queue);
                    scheduler.scheduleAtFixedRate(newCpuProducer, 0, 100, TimeUnit.MILLISECONDS);
                    this.cpuProducer = newCpuProducer;
                }
                if (freeRAMProducer.getLastProducedTime() != null && Duration.between(freeRAMProducer.getLastProducedTime(), currentTime).getSeconds() >= 10) {
                    System.out.println("FreeRamProducer stopped working. Starting a new one...");
                    resourceMonitorGUI.addAlert("FreeRamProducer stopped working. Starting a new one...");
                    var newFreeRamProducer = new FreeRAMProducer(queue);
                    scheduler.scheduleAtFixedRate(freeRAMProducer, 0, 100, TimeUnit.MILLISECONDS);
                    this.freeRAMProducer = newFreeRamProducer;
                }
                if (freeDiskSpaceProducer.getLastProducedTime() != null && Duration.between(freeDiskSpaceProducer.getLastProducedTime(), currentTime).getSeconds() >= 10) {
                    System.out.println("FreeDiskSpaceProducer stopped working. Starting a new one...");
                    resourceMonitorGUI.addAlert("FreeDiskSpaceProducer stopped working. Starting a new one...");
                    var newFreeDiskSpaceProducer = new FreeDiskSpaceProducer(queue);
                    scheduler.scheduleAtFixedRate(newFreeDiskSpaceProducer, 0, 100, TimeUnit.MILLISECONDS);
                    this.freeDiskSpaceProducer = newFreeDiskSpaceProducer;
                }
                for (Consumer c :consumers) {
                    if (Duration.between(c.getLastProducedTime(), currentTime).getSeconds() >= 30) {
                        consumers.remove(c);
                        System.out.println("Consumer stopped working. Starting a new one...");
                        resourceMonitorGUI.addAlert("Consumer stopped working. Starting a new one...");
                        Consumer consumer = new Consumer(queue,resourceMonitorGUI);
                        consumers.add(consumer);
                        scheduler.execute(consumer);
                    }
                }
                if (!Main.isRunning) {
                    break;
                }
                ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                Thread[] threads = new Thread[threadGroup.activeCount()];
                threadGroup.enumerate(threads);

                System.out.println("Active Threads:");
                for (Thread thread : threads) {
                    if (thread != null) {
                        System.out.println("Thread Name: " + thread.getName() +
                                " | Thread ID: " + thread.getId() +
                                " | Thread State: " + thread.getState());
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }

}