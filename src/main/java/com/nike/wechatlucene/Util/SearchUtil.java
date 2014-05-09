package com.nike.wechatlucene.Util;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by bing.du on 4/3/14.
 */
public class SearchUtil {
    Directory directory;

    public SearchUtil() throws IOException {
        directory = FSDirectory.open(new File("/Users/bing.du/JavaProject/resource/case2"));
    }

    public Query getNormalQuery(String key, String value)  {
        return new TermQuery(new Term(key, value));
    }

    public Query getWildcardQuery(String key, String value)  {
        return new WildcardQuery(new Term(key, value));
    }


    public Query getDateQuery(Date startDate,Date endDate)
    {
        String startDateStr = DateTools.dateToString(startDate,
                DateTools.Resolution.SECOND);
        String endDateStr = DateTools.dateToString(endDate,
                DateTools.Resolution.SECOND);
        TermRangeQuery rangeQuery = new TermRangeQuery("SendDate",startDateStr,endDateStr,true,true);
        return rangeQuery;

    }

    public void search(Query query,int count) throws IOException, java.text.ParseException
    {
        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //QueryParser queryParser = new QueryParser(Version.LUCENE_47, "SendUser", new StandardAnalyzer(Version.LUCENE_47));
        TopDocs topDocs = indexSearcher.search(query, count);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("TotalCount:"+scoreDocs.length);
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(
                    String.format("ID:%s,GroupId:%s,SendUser:%s,SendDate:%s,Content:%s",
                            document.get("Id"),
                            document.get("GroupId"),
                            document.get("SendUser"),
                            DateTools.stringToDate(document.get("SendDate")),
                            document.get("Content")
                    ));
        }
        indexReader.close();
    }


}
