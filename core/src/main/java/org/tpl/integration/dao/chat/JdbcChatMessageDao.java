package org.tpl.integration.dao.chat;

import no.kantega.publishing.security.data.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.Team;
import org.tpl.business.model.chat.ChatMessage;
import org.tpl.business.model.fantasy.FantasyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcChatMessageDao extends SimpleJdbcDaoSupport implements ChatMessageDao {
    private ChatMessageRowMapper chatMessageRowMapper = new ChatMessageRowMapper();
    public void saveOrUpdateChatMessage(ChatMessage chatMessage) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("managerId", chatMessage.getFantasyManager().getManagerId());
        params.put("message", chatMessage.getMessage());
        params.put("createdDate", chatMessage.getCreatedDate());

        if(chatMessage.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("chatmessage");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            chatMessage.setId(number.intValue());

        }else{
            params.put("id", chatMessage.getId());
            String sql = "UPDATE chatmessage SET managerId=:managerId, message=:message, createdDate=:createdDate WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public ChatMessage getChatMessageById(int chatMessageId) {
        List<ChatMessage> list = getSimpleJdbcTemplate().query("SELECT * FROM chatmessage  WHERE id = ?", chatMessageRowMapper, chatMessageId);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<ChatMessage> getChatMessages(int number, int offset) {
        List<ChatMessage> list = getSimpleJdbcTemplate().query("SELECT * FROM chatmessage order by createdDate desc limit " + number + " offset " + offset, chatMessageRowMapper);
        return list;
    }

    public List<ChatMessage> getChatMessages(int managerId) {
        List<ChatMessage> list = getSimpleJdbcTemplate().query("SELECT * FROM chatmessage WHERE managerId = ?", chatMessageRowMapper, managerId);
        return list;

    }

    public void deleteChatMessagesForManager(int managerId) {
        String sql = "DELETE FROM chatmessage WHERE managerId = ?";
        getSimpleJdbcTemplate().update(sql,managerId);
    }

    private class ChatMessageRowMapper implements ParameterizedRowMapper<ChatMessage> {
        public ChatMessage mapRow(ResultSet rs, int i) throws SQLException {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(rs.getInt("id"));
            chatMessage.setMessage(rs.getString("message"));
            chatMessage.setCreatedDate(rs.getTimestamp("createdDate"));
            FantasyManager fantasyManager = new FantasyManager();
            fantasyManager.setManagerId(rs.getInt("managerId"));
            chatMessage.setFantasyManager(fantasyManager);
            return chatMessage;
        }
    }
}
