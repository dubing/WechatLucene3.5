package com.nike.wechatlucene.Util;

import com.nike.wechatlucene.model.WeMessage;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by bing.du on 4/3/14.
 */
public class IndexUtil {
    Directory directory;
    Analyzer analyzer;
    IndexWriterConfig iwc;

    public IndexUtil() throws IOException {
        directory = FSDirectory.open(new File("/Users/bing.du/JavaProject/resource/case2"));
        analyzer = new StandardAnalyzer(Version.LUCENE_35);
        iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
    }

    public void index(List<WeMessage> weMessages) throws IOException {
        Analyzer al = new StandardAnalyzer(Version.LUCENE_35);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,al);
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(directory, iwc);
            Document doc = null;

            for (WeMessage weMessage : weMessages) {
                doc = new Document();
                doc.add(new Field("Content", weMessage.getContent(), Field.Store.NO, Field.Index.ANALYZED));
                doc.add(new Field("SendUser", weMessage.getSendUser(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("Id", weMessage.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("GroupId", weMessage.getGroupId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("indexDate",
                        DateTools.dateToString(weMessage.getSendDate(), DateTools.Resolution.SECOND),
                        Field.Store.YES,
                        Field.Index.NOT_ANALYZED));

                iw.addDocument(doc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (iw != null) iw.close();
        }
    }

    public int getIndexCount() throws IOException {
        IndexReader indexReader = IndexReader.open(directory);
        int indexCount = indexReader.numDocs();
        indexReader.close();
        return indexCount;
    }

    public void delete() throws IOException {
        IndexWriter indexWriter=new IndexWriter(directory, iwc);
        indexWriter.deleteAll();
        indexWriter.close();
    }

}
