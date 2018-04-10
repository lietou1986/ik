package position.wltea.analyzer.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.AttributeFactory;
import position.mlcs.search.mlcsseg.lucene.ReloadableTokenizerFactory;
import position.mlcs.search.mlcsseg.lucene.ReloaderRegister;
import position.wltea.analyzer.dic.Dictionary;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class IKTokenizerFactory extends ReloadableTokenizerFactory {
    private boolean useSmart;

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKTokenizerFactory(Map<String, String> args) {
        super(args);
        String useSmartArg = args.get("useSmart");
        setUseSmart(useSmartArg != null ? Boolean.parseBoolean(useSmartArg) : false);
    }


    public Tokenizer create(AttributeFactory factory) {
        Tokenizer _IKTokenizer = new IKTokenizer(factory, this.useSmart);
        return _IKTokenizer;
    }

    public void inform(ResourceLoader loader) throws IOException { // 在启动时初始化一次
//		System.out.println(":::ik:::inform::::::::::::::::::::::::" + conf);
        ReloaderRegister.register(this, loader, conf);
    }


    @Override
    public void update(List<InputStream> inputStreams) {
        Dictionary.addDic2MainDic(inputStreams);
    }
}