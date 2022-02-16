package br.com.seg.maxipago.gateway;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class CompactCdataWriter extends CompactWriter {

 public CompactCdataWriter(Writer writer) {  
        super(writer);  
    }  
    @Override  
    protected void writeText(QuickWriter writer, String text) {  
        if (useCdata(text)) {  
            writer.write("<![CDATA[");  
            writer.write(text);  
            writer.write("]]>");  
        } else {  
            super.writeText(writer, text);  
        }  
    }  
    private boolean useCdata(String text) {  
        if (text.indexOf("]]>") < 0) {  
            for (int i = 0; i < text.length(); i++) {  
                switch (text.charAt(i)) {  
                case '<':  
                case '>':  
                case '&':  
                case '"':  
                case '\'':  
                case '\r':  
                    return true;  
                }  
            }  
        }  
        return false;  
    }
}