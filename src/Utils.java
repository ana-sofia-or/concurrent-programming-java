import java.lang.management.ManagementFactory;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import com.sun.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
public class Utils
{
    private static final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    private static final Locale dotLocale = new Locale("en", "US");
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(dotLocale));
    private static final Random randomGenerator = new Random();
    public static double getCpuLoad()
    {
        // This method returns the real CPU load.
        double cpuLoad = operatingSystemMXBean.getCpuLoad();

        // If the CPU load is invalid, an exception is thrown.
        if (cpuLoad < 0.0)
          throw new RuntimeException("Invalid CPU load.");
        return Double.parseDouble(decimalFormat.format(cpuLoad * 100));
    }
    public static double getFreeRAM()
    {
//        long freeMemory = Runtime.getRuntime().freeMemory();
//        long totalMemory = Runtime.getRuntime().totalMemory();
//        return Double.parseDouble(decimalFormat.format(((double) freeMemory / totalMemory) * 100));
        return simulatePercentage("Invalid free RAM percentage.");
    }

    public static double getFreeDiskSpace()
    {
//        File file = new File("/");
//        long freeSpace = file.getFreeSpace();
//        long totalSpace = file.getTotalSpace();
//        return Double.parseDouble(decimalFormat.format(((double) freeSpace / totalSpace) * 100));
        return simulatePercentage("Invalid free disk space percentage.");
    }
    private static double simulatePercentage(String errorMessage)
    {
        // There is a 5% probability that an exception may occur.
        if (randomGenerator.nextDouble() < 0.05)
            throw new RuntimeException(errorMessage);

        return randomGenerator.nextDouble();
    }
}