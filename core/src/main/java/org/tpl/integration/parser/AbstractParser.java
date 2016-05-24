package org.tpl.integration.parser;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.tpl.business.service.LeagueService;
import no.kantega.commons.log.Log;
import org.tpl.business.service.PlayerService;
import org.w3c.dom.*;
import org.w3c.tidy.DOMElementImpl;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 21:35:32
 */
public class AbstractParser {

    protected final String PLAYER_NODE              = "player";
    protected final String PLAYER_FIRSTNAME         = "firstName";
    protected final String PLAYER_LASTNAME          = "lastName";
    protected final String PLAYER_POSITION          = "position";
    protected final String PLAYER_ID                = "id";

    protected LeagueService leagueService;
    protected PlayerService playerService;


    protected Document openHtmlDocument(String url){
        Log.info(this.getClass().getName(), "Starting openDocument.", null, null);
        Document document = null;
        try{
            URL link = new URL(url);
            URLConnection urlConnection = link.openConnection();
            OutputStream outputStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setXHTML(false);
            tidy.setCharEncoding(3);
            document = tidy.parseDOM(urlConnection.getInputStream(),outputStream);
        }catch (MalformedURLException e){
            Log.error(this.getClass().getName(),e,null,null);
        }catch(IOException e){
            Log.error(this.getClass().getName(),e,null,null);
        }
//        cleanUpDocument(document);
        return document;
    }

    protected JSONObject openJson(String url){

        Log.info(this.getClass().getName(), "Starting openJson", null, null);
        JSONObject jsonObject = null;
        try{
            URL link = new URL(url);
            URLConnection urlConnection = link.openConnection();
            String jsonTxt = IOUtils.toString(urlConnection.getInputStream());
            jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
        }catch (MalformedURLException e){
            Log.error(this.getClass().getName(),e,null,null);
        }catch(IOException e){
            Log.error(this.getClass().getName(),e,null,null);
        }catch (JSONException e){
            Log.error(this.getClass().getName(),e,null,null);
        }
        return jsonObject;
    }

    protected Document openXmlDocument(String url){
        Log.info(this.getClass().getName(), "Starting openDocument.", null, null);
        Document document = null;
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            InputStream in = conn.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(in);
        } catch (IOException e) {
            Log.error(this.getClass().getName(),e,null,null);
        } catch (ParserConfigurationException e) {
            Log.error(this.getClass().getName(),e,null,null);
        } catch (SAXException e) {
            Log.error(this.getClass().getName(),e,null,null);
        }
        return document;
    }

    protected String getAttributeValue(Node node, String name){
        NamedNodeMap namedNodeMap= node.getAttributes();
        Node subNode = namedNodeMap.getNamedItem(name);
        if(subNode == null){
            return null;
        }
        return subNode.getNodeValue();
    }

    protected int parseInt(String tempNumber){

        IntegerValidator integerValidator = IntegerValidator.getInstance();
        Integer number = integerValidator.validate(tempNumber);
        return number == null ? -1 : number;
    }

    protected String cleanUpString(String string){
        string = string.replace("[","");
        string = string.replace("]","");
        return string;
    }

    protected String getText(Node node) {
        NodeList children = node.getChildNodes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeType()) {
                case Node.ELEMENT_NODE:
                    sb.append(getText(child));
                    sb.append(" ");
                    break;
                case Node.TEXT_NODE:
                    sb.append(((Text) child).getData());
                    break;
            }
        }
        return sb.toString();
    }

    protected List<DOMElementImpl> getNodeList(Document document , String tagName, String attribute, String... attributeValues){
        NodeList nodeList = document.getElementsByTagName(tagName);
        return nodeSearch(nodeList,attribute,attributeValues);
    }

    protected DOMElementImpl nodeSearchFindFirstNode(Node node , String tagName, String attribute, String... attributeValues){
        List<DOMElementImpl> domElements = nodeSearch(node, tagName, attribute, attributeValues);
        if(domElements.size() > 0){
            return domElements.get(0);
        }
        return null;
    }

    protected List<DOMElementImpl> nodeSearch(Node node , String tagName, String attribute, String... attributeValues){
        DOMElementImpl domNode = (DOMElementImpl)node;
        NodeList nodeList = domNode.getElementsByTagName(tagName);
        return nodeSearch(nodeList,attribute,attributeValues);

    }

    protected List<DOMElementImpl> nodeSearch(NodeList nodeList, String attribute, String... attributeValues){
        List<DOMElementImpl> nodeArrayList = new ArrayList<DOMElementImpl>();

        for(int i = 0; i < nodeList.getLength(); i++){
            DOMElementImpl childDomNode = (DOMElementImpl)nodeList.item(i);
            if(attribute == null){

                nodeArrayList.add(childDomNode);
            }
            else if(childDomNode.getAttribute(attribute) != null){
                if(attributeValues == null || attributeValues.length == 0){
                    nodeArrayList.add(childDomNode);
                }

                for(String attributeValue: attributeValues){
                    String nodeAttrValue =getAttributeValue(childDomNode,attribute);
                    if(attribute.equals("id")){
                        if(nodeAttrValue != null && nodeAttrValue.equals(attributeValue)){
                            nodeArrayList.add(childDomNode);
                        }
                    }else{
                        if(nodeAttrValue != null && nodeAttrValue.contains(attributeValue)){
                            nodeArrayList.add(childDomNode);
                        }

                    }
                }
            }
        }
        return nodeArrayList;
    }

    public void setLeagueService(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
