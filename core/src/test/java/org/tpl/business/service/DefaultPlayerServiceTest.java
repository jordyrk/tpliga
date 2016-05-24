package org.tpl.business.service;

import org.junit.Test;
import org.tpl.business.model.search.OrTerm;

import static junit.framework.Assert.assertEquals;

public class DefaultPlayerServiceTest {

    @Test
    public void testCreateOrTermFromAlias() throws Exception {
        DefaultPlayerService service = new DefaultPlayerService();
        OrTerm orTermFromAlias = service.createOrTermFromAlias("Rita Hayford Work", 1);
        assertEquals(5, orTermFromAlias.getParameters().size());
        orTermFromAlias = service.createOrTermFromAlias("Rita",1);
        assertEquals(4, orTermFromAlias.getParameters().size());
        orTermFromAlias = service.createOrTermFromAlias("Rita Hayford",1);
        assertEquals(5, orTermFromAlias.getParameters().size());

    }
}
