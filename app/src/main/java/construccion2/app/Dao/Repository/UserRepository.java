package App.Dao.Repository;

import App.Model.Person;
import App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    public boolean existsByUserName(String userName);
    public User findById( long id );
    public User findByUserName( String name );    
    public User findByPersonnId( Person id );    
}