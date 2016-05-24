package org.tpl.integration.parser;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * User: Koren
 * Date: 27.jun.2010
 * Time: 21:49:04
 */
public class MatchParserHtmlImplTest{

    private TestableMatchParserHtmlImpl matchParserHtml;


    @Before
    public void setUp() throws Exception {
        matchParserHtml = new TestableMatchParserHtmlImpl();
    }


    @Test
    public void testGetMatches() throws Exception {
        List<Match> matches = matchParserHtml.getMatches();
        //TODO: Not all matches in fixturelist.html
        assertEquals(361, matches.size());
    }

    @After
    public void tearDown() throws Exception {
        matchParserHtml = null;
    }

    private class TestableMatchParserHtmlImpl extends MatchParserHtmlImpl{
        @Override
        protected Document openHtmlDocument(String url) {
            Document document = null;
            InputStream is = getClass().getClassLoader().getResourceAsStream("org/tpl/integration/parser/nrk/fixturelist.html");

            OutputStream outputStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setXHTML(false);
            document = tidy.parseDOM(is,outputStream);
            return document;
        }

        @Override
        protected Team findTeam(String teamName) throws MatchImportException {
            return new Team();
        }
    }

}
