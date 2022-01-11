package junit.in.action.businesslayer;

import junit.in.action.model.User;
import junit.in.action.model.UserDto;
import junit.in.action.persistencelayer.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.in.action.chapter18.model.EntitiesHelper.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertNull;

// EasyMock 을 이용하여 DAO 인터페이스를 구현하는 방식으로 비지니스 계층의 퍼사드를 테스트
class UserFacadeImplTest {

    private UserFacadeImpl facade;
    private UserDao dao;

    @BeforeEach
    void setFixtures() {
        dao = createMock(UserDao.class);
        facade = new UserFacadeImpl(dao);
    }

    @DisplayName("ID 로 USER 가져오기 : 성공")
    @Test
    void testGetUserById() {
        Long id = 666l;
        User user = newUserWithTelephones();
        expect(dao.getUserById(id)).andReturn(user);
        replay(dao);
        UserDto dto = facade.getUserById(id);
        assertUser(dto);
    }

    @DisplayName("UNKNOWN ID 로 USER 가져오기")
    @Test
    void testGetUserByIdUnknownId() {
        Long id = 666l;
        expect(dao.getUserById(id)).andReturn(null);
        replay(dao);
        UserDto dto = facade.getUserById(id);
        assertNull(dto);
    }
}