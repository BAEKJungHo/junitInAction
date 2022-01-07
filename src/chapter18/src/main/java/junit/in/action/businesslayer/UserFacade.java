package junit.in.action.businesslayer;

import junit.in.action.model.UserDto;

// UserFacade : 영속 객체를 직접 사용하지 않고 DTO(데이터 전송 객체)를 사용한다는 뜻
public interface UserFacade {

    UserDto getUserById(Long id);
}
