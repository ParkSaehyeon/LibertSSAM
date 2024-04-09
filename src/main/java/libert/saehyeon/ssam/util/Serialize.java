package libert.saehyeon.ssam.util;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Base64;

public class Serialize {
    public static String serialize(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Base64를 디코딩하고 역직렬화하여 객체로 반환하는 메서드
    public static Object deSerialize(String base64) {

        if(base64 == null) {
            return null;
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            dataInput.close();
            return dataInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void serializeFile(String path, Object obj) throws IOException {
        File file = new File(path);

        if(file.exists()) {
            file.delete();
        }

        String str = serialize(obj);

        FileWriter w = new FileWriter(file);
        w.write(str);
        w.close();
    }

    public static Object deSerializeFile(String path) {

        if(new File(path).exists()) {
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

            String base64 = content.toString();

            if(base64.isEmpty()) {
                return null;
            }

            return deSerialize(base64);
        }

        return null;
    }
}
