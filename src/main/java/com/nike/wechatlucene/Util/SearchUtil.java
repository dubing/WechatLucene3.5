package com.nike.wechatlucene.Util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by bing.du on 4/3/14.
 */
public class SearchUtil {
    Directory directory;

    public SearchUtil() throws IOException {
        directory = FSDirectory.open(new File("/Users/bing.du/JavaProject/resource/case2"));
    }

    public void search(String key, String value) throws IOException, ParseException {
        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //QueryParser queryParser = new QueryParser(Version.LUCENE_47, "SendUser", new StandardAnalyzer(Version.LUCENE_47));
        Query query =new TermQuery(new Term("SendUser","a"));
        TopDocs topDocs = indexSearcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("Id"));
        }
        indexReader.close();
    }

    public int getIndexCount() throws IOException {
        IndexReader indexReader = IndexReader.open(directory);
        int indexCount = indexReader.numDocs();
        indexReader.close();
        return indexCount;
    }
}
