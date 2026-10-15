package org.dromara.common.oss.util;

public final class FileUtil {

    private FileUtil() {
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String excludeExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static boolean isImage(String fileName) {
        String extension = getFileExtension(fileName);
        String[] imageExtension = new String[]{"jpeg", "jpg", "bmp", "png", "webp", "gif"};
        for (String e : imageExtension) {
            if (extension.toLowerCase().equals(e)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGif(String fileName) {
        String extension = getFileExtension(fileName);
        return extension.equalsIgnoreCase("gif");
    }
}
