package com.mprog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mprog.serbuf.model.ClusterConfig;
import org.apache.catalina.Cluster;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestIP {

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
//        InetAddress ip = InetAddress.getLocalHost();
//        System.out.println("Current IP address : " + ip.getHostAddress());

        String val = "shokhrukh";
        String val2 = "munir";
//        System.out.println(val.hashCode());
//        System.out.println(val.hashCode() % 2);
//        System.out.println(val2.hashCode());
//        System.out.println(val2.hashCode() % 2);
        Map<Integer, Integer> map = new HashMap<>();


        for (int i = 0; i < 100; i++) {
            String x = generateRandomString();
            int shard = x.hashCode() % 3;
            if (map.containsKey(shard)) {
                map.put(shard, map.get(shard) + 1);
            } else {
                map.put(shard, 1);
            }
            System.out.println(x + " " + shard);
        }

        System.out.println(map);
    }

    public static String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = random.nextInt(1, 10);
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31*h + string.charAt(i);
        }
        return h;
    }

}
