package org.tpl.presentation.common;


import no.kantega.publishing.security.SecuritySession;
import no.kantega.publishing.security.data.Role;
import no.kantega.publishing.security.data.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class FantasyUtilTest {
    @Mock
    private SecuritySession securitySession ;
    @Mock
    private User user;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(securitySession.isLoggedIn()).thenReturn(true);
        when(securitySession.getUser()).thenReturn(user);
    }

    @Test
    public void shouldNotHaveAdminRole() throws Exception {
        when(user.getRoles()).thenReturn(getRoles("admin"));
        boolean isNotAdmin = new FantasyUtil().hasRole(securitySession, "tpladmin");
        assertFalse(isNotAdmin);

    }

    @Test
    public void shouldHaveAdminRole() throws Exception {
        when(user.getRoles()).thenReturn(getRoles("admin"));
        boolean isAdmin = new FantasyUtil().hasRole(securitySession, "admin");
        assertTrue(isAdmin);

    }

    @Test
    public void testReturnsTrueWhenCheckingForMultipleRoles() throws Exception {
        when(user.getRoles()).thenReturn(getRoles("admin"));
        boolean isAdmin = new FantasyUtil().hasRole(securitySession, "tplsadmin","admin");
        assertTrue(isAdmin);

    }

    HashMap<String, Role> getRoles(String... roles){
        HashMap<String,Role> map = new HashMap<String, Role>();
        for(String role : roles){
            map.put(role,null);
        }
        return map;
    }
}
