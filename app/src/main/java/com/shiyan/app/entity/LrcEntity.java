package com.shiyan.app.entity;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: created by shiyan on 2018/9/6
 **/

public class LrcEntity implements Comparable<LrcEntity>{

    private String time;

    private long timeLong;

    private String text;

    private static List<LrcEntity> entityList = new ArrayList<>();

    public LrcEntity() {
    }

    public LrcEntity(String time, String text, long timeLong) {
        this.time = time;
        this.text = text;
        this.timeLong = timeLong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 解析歌词文本
     * @param lrcText
     * @return
     */
    public static List<LrcEntity> parseLrc(String lrcText) {
        if (TextUtils.isEmpty(lrcText)) {
            return null;
        }

        entityList.clear();

        // 将字符串以换行符切割
        String[] array = lrcText.split("\\n");
        for (String line : array) {
            // 循环遍历按行解析
            List<LrcEntity> list = parseLine(line);
            if (list != null && !list.isEmpty()) {
                entityList.addAll(list);
            }
        }
        // 使序列按大小升序排序（由小到大）
        Collections.sort(entityList);
        return entityList;
    }

    /**
     * 针对每一句歌词解析，并存到LrcEntity中
     * @param line
     * @return
     */
    private static List<LrcEntity> parseLine(String line) {
        Matcher lineMatcher = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d\\d\\])+)(.+)").matcher(line);
        // 如果没有，返回null
        if (!lineMatcher.matches()) {
            return null;
        }
        List<LrcEntity> entryList = new ArrayList<>();
        int pos1 = line.indexOf("[");//0
        int pos2 = line.indexOf("]");//9  indexof如果找不到返回-1
        if (pos1 == 0 && pos2 != -1) {
            //long数组用于存放时间戳，判断含有多少个时间标签
            String[] times = new String[getCount(line)];
            String strTime = line.substring(pos1, pos2+1);//[02:45.69]
            // 时间标签数组
            times[0] = strTime;
            //判断是否还有下一个
            String text = line;//[02:45.69][02:42.20][02:37.69][01:10.60]就在记忆里画一个叉
            int i = 1;
            while (pos1 == 0 && pos2 != -1) {//判断是否有时间的显示，既歌词
                text = text.substring(pos2 + 1);//[02:42.20][02:37.69][01:10.60]就在记忆里画一个叉
                pos1 = text.indexOf("[");//0
                pos2 = text.indexOf("]");//9
                if (pos2 != -1) {
                    strTime = text.substring(pos1, pos2 + 1);//[02:42.20]
                    times[i] = strTime;//将第二个时间戳添加到数组中
                    if (times[i] == "") {
                        return entryList;
                    }
                }
                i++;
            }

            LrcEntity lrcEntity = new LrcEntity();
            for (int j = 0; j < times.length; j++) {
                if (times[j] != null) {
                    lrcEntity.setText(text);
                    lrcEntity.setTimeLong(Str2Long(times[j]));
                    lrcEntity.setTime(times[j]);
                    entryList.add(lrcEntity);//将歌词信息添加到集合中
                    lrcEntity = new LrcEntity();
                }
            }
        }
        return entryList;
    }

    //将字符串转换为long类型
    private static long Str2Long(String strTime) {
        long showTime = -1;
        try {
            strTime = strTime.substring(1,strTime.length()-1);
            String[] s1 = strTime.split(":");
            String[] s2 = s1[1].split("\\.");
            long min = Long.parseLong(s1[0]);
            long second = Long.parseLong(s2[0]);
            long mil = Long.parseLong(s2[1]);
            showTime = min * 60 * 1000 + second * 1000 + mil * 10;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    /**
     * 判断当前行的歌词播放几次
     *
     * @param line
     * @return
     */
    private static int getCount(String line) {
        String[] split = line.split("\\]");
        return split.length;
    }

    /**
     * seek歌词
     * @param position
     * @return
     */
    private static String seekTo(long position){

        String result = "";

        if(position < 0) return result;

        if(entityList.size() > 0) {
            for(int i = 0; i < entityList.size(); i++){

                if((i + 1) <= entityList.size() -1) {
                    if (entityList.get(i).getTimeLong() >= position && entityList.get(i + 1).getTimeLong() < position) {
                        return entityList.get(i).getText();
                    }
                }else if(i == entityList.size() -1){
                    return entityList.get(i).getText();
                }
            }
        }

        return result;
    }

    @Override
    public int compareTo(@NonNull LrcEntity entity) {
        if (entity == null) {
            return -1;
        }
        return (int) (timeLong - entity.getTimeLong());
    }
}
