package libert.saehyeon.ssam.util;

import java.io.*;

public class FileUtil {
    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    // 파일에 내용을 쓰는 메서드
    public static void writeFile(String path, String content) {
        try {
            // 기존 파일이 있는 경우 삭제
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            // 새로운 파일 생성
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
