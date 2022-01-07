package junit.in.action.businesslayer;

import junit.in.action.model.Telephone;
import junit.in.action.model.User;
import junit.in.action.model.UserDto;
import junit.in.action.persistencelayer.UserDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private static final String TELEPHONE_STRING_FORMAT = "%s (%s)";
    private final UserDao userDao;

    @Override
    public UserDto getUserById(Long id) {
        User user = userDao.getUserById(id);
        if(user == null) {
            return null;
        }
        UserDto dto = user.toDto();
        List<String> telephoneDtos = dto.getTelephones();
        for (Telephone telephone : user.getTelephones()) {
            String telephoneDto = String.format(TELEPHONE_STRING_FORMAT, telephone.getNumber(), telephone.getType());
            telephoneDtos.add(telephoneDto);
        }
        return dto;
    }
}
