import java.io.File;

public class Main {

    private static int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\nT1no\\Desktop\\src";
        String dstFolder = "C:\\Users\\nT1no\\Desktop\\dst_2";

        File scrDir = new File(srcFolder);
        File[] files = scrDir.listFiles();

        int cores = Runtime.getRuntime().availableProcessors();

        generateThreadsForImageResizer(files, cores, dstFolder);
    }

    public static void generateThreadsForImageResizer(File[] files, int cores, String dstFolder) {
        int lengthThread = files.length / cores;

        int remainder = 0;
        int counter = 0;

        if (files.length % 2 != 0) {
            remainder = (int) Math.ceil(files.length - (lengthThread * cores));
        }

        for (int i = 0; i < cores; i++) {
            File[] filesThread = new File[lengthThread + remainder];
            System.arraycopy(files, counter, filesThread, 0, lengthThread + remainder);
            counter += lengthThread + remainder;
            remainder = 0;
            ImageResizer resizer = new ImageResizer(filesThread, newWidth,
                    dstFolder, i + 1, System.currentTimeMillis());
            resizer.start();
        }
    }
}
