package company.apache.solr.analysis;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class DStopFilter extends TokenFilter {

    private CharArraySet stopWords;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public DStopFilter(TokenStream input) {
        super(input);
    }

    public DStopFilter(TokenStream input, CharArraySet stopWords) {
        this(input);
        this.stopWords = stopWords;
    }


    @Override
    public boolean incrementToken() throws IOException {
        return !stopWords.contains(termAtt.buffer(), 0, termAtt.length());
    }
}
