package org.tpl.integration.dao.chat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.chat.ChatMessage;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class JdbcChatMessageDaoTest {

    JdbcChatMessageDao jdbcChatMessageDao;
    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcChatMessageDao = new JdbcChatMessageDao();
        jdbcChatMessageDao.setDataSource(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        jdbcChatMessageDao = null;
    }

    @Test
    public void testSimpleSaveOrUpdateChatMessage() throws Exception {
        ChatMessage chatMessage = createChatMessage();
        jdbcChatMessageDao.saveOrUpdateChatMessage(chatMessage);
        assertFalse("chatMessage is not new", chatMessage.isNew());
    }

    @Test
    public void testSaveOrUpdateChatMessage() throws Exception {
        ChatMessage chatMessage = createChatMessage();
        jdbcChatMessageDao.saveOrUpdateChatMessage(chatMessage);
        ChatMessage chatMessage2 = jdbcChatMessageDao.getChatMessageById(chatMessage.getId());
        assertEquals("message is equal", chatMessage.getMessage(), chatMessage2.getMessage());
        assertEquals("date is equal", chatMessage.getCreatedDate(), chatMessage2.getCreatedDate());
        assertEquals("userid is equal", chatMessage.getFantasyManager().getManagerId(), chatMessage2.getFantasyManager().getManagerId());
    }


    @Test
    public void testGetChatMessageById() throws Exception {
        ChatMessage chatMessage = jdbcChatMessageDao.getChatMessageById(1);
        assertEquals("message is equal", "Message 1", chatMessage.getMessage());
        assertEquals("userid is equal", 1, chatMessage.getFantasyManager().getManagerId());

    }

    @Test
    public void testGetLatestChatMessages() throws Exception {
        List<ChatMessage> list = jdbcChatMessageDao.getChatMessages(5,0);
        assertEquals("size is 5",5, list.size());
    }

    @Test
    public void testGetChatMessagesForManager() throws Exception {
        List<ChatMessage> list = jdbcChatMessageDao.getChatMessages(4);
        assertEquals("size is 1",1, list.size());
    }

    @Test
    public void testDeleteChatMessagesForManager() throws Exception {
       List<ChatMessage> list = jdbcChatMessageDao.getChatMessages(4);
        assertEquals("size is 1",1, list.size());
        jdbcChatMessageDao.deleteChatMessagesForManager(4);
        list = jdbcChatMessageDao.getChatMessages(4);
        assertEquals("size is 0",0, list.size());
    }

    private ChatMessage createChatMessage(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("HAHA");
        chatMessage.setCreatedDate(new Date());
        FantasyManager fantasyManager= new FantasyManager();
        fantasyManager.setManagerId(1);
        chatMessage.setFantasyManager(fantasyManager);
        return chatMessage;
    }
}
