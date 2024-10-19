package App.Dao.Repository;

import App.Model.User;
import App.Model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{
    public boolean existsByUserId( User user );
    public Guest findById( long id );
    public Guest findByUserId( User user );    
}