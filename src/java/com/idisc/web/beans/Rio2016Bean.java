package com.idisc.web.beans;

import com.bc.util.XLogger;
import com.bc.webdatex.filter.HasAttributeRegexFilter;
import com.bc.webdatex.filter.IsOrHasParentFilter;
import com.idisc.core.web.UrlNodeExtractor;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 11, 2016 11:10:16 PM
 */
public class Rio2016Bean implements Serializable {

    private final String configFilename = "META-INF/json/rio2016.json";
    
    private int updateIntervalMinutes;
    
    private long lastUpdateTime;
    
    private Path configPath;
    
    private String medalsTableHtml;
    
    private String medalsTableHtmlMinified;
    
    public Rio2016Bean() { }
    
    public void setRequest(HttpServletRequest request) {
        
        final ServletContext context = request.getServletContext();
        
        if(configPath == null) {
            configPath = Paths.get(context.getRealPath(configFilename));
        }
        
//        if(this.isNextUpdateDue()) {
//            new SaveRio2016MedalsTableToNoticesDir(context, this.getMedalsTableHtml()).run();
//            new UpdateFileFeeds(context).run();
//        }
    }

    public String getMedalsTableHtmlMinified() {
        return this.getMedalsTableHtml(true);
    }
    
    public String getMedalsTableHtml() {
        return this.getMedalsTableHtml(false);
    }

    public String getMedalsTableHtml(boolean minified) {
        if(this.medalsTableHtml == null || this.medalsTableHtmlMinified == null || this.isNextUpdateDue()) {
            final NodeList defaultValue = null;
            NodeList nodes = this.loadMedalsTableNodes(defaultValue);
            nodes = this.repair(nodes);
            this.medalsTableHtml = nodes.toHtml();
            this.medalsTableHtmlMinified = this.minify(nodes).toHtml();
        }
        return minified ? this.medalsTableHtmlMinified : this.medalsTableHtml;
    }
    
    private NodeList minify(NodeList nodes) {
        
        return this.removeAll(nodes, "TR", "class", "table-expand", false);
    } 

    private NodeList removeAll(NodeList nodes, final String tagName, 
            final String attrName, final String attrValue, boolean regexAttrValue) {
        
        final int sizeB4 = nodes.size();
        
        final NodeFilter targetFilter = this.getTagHasAttributeFilter(tagName, attrName, attrValue, regexAttrValue);
        
        final NodeFilter isOrHasParent = new IsOrHasParentFilter(targetFilter);
        
        final NodeFilter acceptToRetain = new NotFilter(isOrHasParent);
        
        nodes.keepAllNodesThatMatch(acceptToRetain, true);
        
//System.out.println("oooooooooooooooooo #removeAll ooooooooooooooooooo");                    
//System.out.println(nodes.toHtml());
            
XLogger.getInstance().log(Level.FINE, "#extractAllNodesThatMatch. NodeList size. BEFORE: {0}, AFTER: {1}", 
        this.getClass(), sizeB4, nodes.size());

        return nodes;
    }
    
    private NodeList repair(NodeList nodes) {
        
        final int sizeB4 = nodes.size();
        
        final NodeFilter acceptToRemove = this.getTagHasAttributeFilter("TD", "class", "hidden", false);
        
        final NodeFilter acceptToUpdate = this.getTagHasAttributeFilter("SPAN", "title", "Gold|Silver|Bronze", true);
        
        final NodeList toUpdate = nodes.extractAllNodesThatMatch(acceptToUpdate, true);
        
        try{
            toUpdate.visitAllNodesWith(new NodeVisitor() {
                @Override
                public void visitTag(Tag tag) {
                    Attribute attribute = tag.getAttributeEx("title");
                    String attrVal = attribute.getValue();
                    if(attrVal != null) {
                        NodeList children = tag.getChildren();
                        if(children != null) {
                            for(Node child:children) {
                                if(child instanceof TextNode) {
                                    ((TextNode)child).setText(attrVal);
                                }
                            }
                        }else{
                            TextNode textNode = new TextNode(tag.getPage(), 0, 0);
                            textNode.setText(attrVal);
                            textNode.setParent(tag);
                            children = new NodeList();
                            children.add(textNode);
                            tag.setChildren(children);
                        }
                    }
                }
            });
        }catch(ParserException e) {
            XLogger.getInstance().log(Level.WARNING, "Unexpected exception", this.getClass(), e);
        }
        
        final NodeFilter acceptToRetain = new NotFilter(acceptToRemove);
        
        nodes.keepAllNodesThatMatch(acceptToRetain, true);

//System.out.println("ooooooooooooooooo #repair oooooooooooooooooooo");  
//System.out.println(nodes.toHtml());
            
XLogger.getInstance().log(Level.FINE, "#extractAllNodesThatMatch. NodeList size. BEFORE: {0}, AFTER: {1}", 
        this.getClass(), sizeB4, nodes.size());
        
        return nodes;
    }

    private NodeFilter getTagHasAttributeFilter(
            final String tagName, final String attrName, final String attrValue, boolean regexAttrValue) {
        final NodeFilter tagNameFilter = new TagNameFilter(tagName);
        final NodeFilter hasAttrFilter = !regexAttrValue ? new HasAttributeFilter(attrName, attrValue) : 
                new HasAttributeRegexFilter(attrName, attrValue);
        final NodeFilter tagHasAttributeFilter = new AndFilter(tagNameFilter, hasAttrFilter);
        return tagHasAttributeFilter;
    }
    
    public boolean isNextUpdateDue() {
        if(this.lastUpdateTime < 1) {
            return true;
        }else{
            final long elapsed = System.currentTimeMillis() - this.lastUpdateTime;
            return elapsed > TimeUnit.MINUTES.toMillis(this.updateIntervalMinutes);
        }
    }
    
    public NodeList loadMedalsTableNodes(NodeList defaultValue) {
        
        this.lastUpdateTime = System.currentTimeMillis();
        
        XLogger logger = XLogger.getInstance();
        Class cls = this.getClass();
        Level level = Level.FINE;
        try{
            
            final String jsonText = new String(Files.readAllBytes(configPath));
            
            logger.log(level, "---------- PRINTING JSON CONFIG ---------\n{0}\n", cls, jsonText);
            
            final JSONObject json = (JSONObject)JSONValue.parseWithException(jsonText);
            
            this.updateIntervalMinutes = ((Long)json.get("updateIntervalMinutes")).intValue();
            
            JSONObject urlData = (JSONObject)json.get("url");
            String url = (String)urlData.get("start");
            logger.log(level, "URL: {0}", cls, url);
            
            JSONObject targetNode0 = (JSONObject)json.get("targetNode0");
            
            JSONObject attributes = (JSONObject)targetNode0.get("attributes");
            String nodeId = (String)attributes.get("id");
            logger.log(level, "Node ID: {0}", cls, nodeId);
            
            JSONArray arr = (JSONArray)targetNode0.get("transverse");
            Object [] path = arr == null ? null : arr.toArray();
            logger.log(level, "Path: {0}", cls, path==null?null:Arrays.toString(path));

            final String filename = configPath.getFileName().toString();
            UrlNodeExtractor extractor = new UrlNodeExtractor(filename);

            NodeList output;
            
            Tag node = extractor.extract(url, path, null);
            
            if(node != null) {
                
                output = new NodeList();
                output.add(node);
                
            }else{

                output = extractor.extract(url, nodeId, null);
            }
            
            logger.log(Level.FINER, "---------- PRINTING EXTRACTED DATA 1 ---------\n{0}\n", 
                    cls, output == null ? null : output);
            
            return output == null ? defaultValue : output;
            
        }catch(IOException | ParseException | ParserException e) {
            
            logger.log(level, "Exception extracting Rio 2016 data", cls, e);
            
            return defaultValue;
        }
    }
}
/**
 * 
    private String repair(String s) {
        s = s.replace("<td tabindex=\"-1\" colspan=\"3\" class=\"hidden\"></td>", "");
        s = s.replace(" title=\"Gold\"></span>", " title=\"Gold\">Gold</span>");
        s = s.replace(" aria-label=\"Gold\"></span>", " aria-label=\"Gold\">Gold</span>");
        s = s.replace(" title=\"Silver\"></span>", " title=\"Silver\">Silver</span>");
        s = s.replace(" aria-label=\"Silver\"></span>", " aria-label=\"Silver\">Silver</span>");
        s = s.replace(" title=\"Bronze\"></span>", " title=\"Bronze\">Bronze</span>");
        s = s.replace(" aria-label=\"Bronze\"></span>", " aria-label=\"Bronze\">Bronze</span>");
        return s;
    }
    
 * 
 */