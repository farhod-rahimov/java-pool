package school21.spring.service.repositories; 

import school21.spring.service.models.*;
import java.util.*;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}