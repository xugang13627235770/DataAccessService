package cn.citms.dataaccessserver.util;
import java.io.*;

public class FileUtil {

   /* public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setCode("00000" + i);
            user.setCount("123456" + i);
            user.setName("黎明" + i);
            userList.add(user);
        }
        writeContext(userList, 1000);
    }

    public static void writeContext(List<User> userList, int length) {
        if (userList.size() > length) {
            List<List<User>> splitList = splitList(userList, length);
            Long start = System.currentTimeMillis();
            splitList.stream().forEach(users -> writeObjectToTxt(users));
            System.out.println("写入txt数据时间：" + (System.currentTimeMillis() - start) / 1000 + "ms");
        }
    }

    *//**
     * 写数据到txt
     *
     * @param userList
     *//*
    private static void writeObjectToTxt(List<User> userList) {
        File file = new File("F:\\" + System.currentTimeMillis() + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                userList.stream().forEach(user -> {
                    try {
                        bufferedWriter.write(JSON.toJSONString(user));
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        System.out.println("写入txt数据异常");
                    }
                });
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("txt文件创建失败");
            }
        }

    }

    *//**
     * 按长度切割List
     *
     * @param list 源List
     * @param len  切割的长度
     * @return
     *//*
    private static List<List<User>> splitList(List<User> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<User>> result = new ArrayList<>();
        int size = list.size();
        // int count = (size + len - 1) / len;
        int count = (int) Math.ceil(size / Double.valueOf(len));
        for (int i = 0; i < count; i++) {
            List<User> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    *//**
     * 快速获取文件行数
     *
     * @param file
     * @return
     * @throws IOException
     *//*
    public int getTotalLines(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber();
        reader.close();
        long endTime = System.currentTimeMillis();
        System.out.println("统计文件行数运行时间： " + (endTime - startTime) + "ms");
        return lines;
    }
*/
}
