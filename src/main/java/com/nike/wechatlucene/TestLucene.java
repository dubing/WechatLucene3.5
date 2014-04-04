package com.nike.wechatlucene;

import com.nike.wechatlucene.Util.IndexUtil;
import com.nike.wechatlucene.Util.SearchUtil;
import com.nike.wechatlucene.model.WeMessage;
import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by bing.du on 4/3/14.
 */
public class TestLucene {
    @Test
    public void testSearch() throws IOException, ParseException {
        new SearchUtil().search("SendUser","a");
    }


    @Test
    public void getIndexCount() throws IOException {
        System.out.println(new IndexUtil().getIndexCount());
    }

    @Test
    public void addIndex() throws IOException {
        List<WeMessage> weMessages = new ArrayList<WeMessage>() ;
        Random randomGenerator = new Random();
        for(int i=0;i<=500;i++)
        {
            WeMessage weMessage = new WeMessage();
            weMessage.setId(String.valueOf(i));
            weMessage.setGroupId(String.valueOf(randomGenerator.nextInt(10)));
            weMessage.setContent(generateString(randomGenerator,"abcdefghijklmn",10));
            weMessage.setSendUser(generateString(randomGenerator,"abcdefghijklmn",1));
            weMessage.setSendDate(generateDate(randomGenerator,10));
            weMessages.add(weMessage);
        }
        new IndexUtil().index(weMessages);
        System.out.println(new IndexUtil().getIndexCount());
    }

    @Test
    public void delete() throws IOException {
        new IndexUtil().delete();
        System.out.println(new IndexUtil().getIndexCount());
    }


    public String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public Date generateDate(Random rng,int length)
    {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,rng.nextInt(10));//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }
}
