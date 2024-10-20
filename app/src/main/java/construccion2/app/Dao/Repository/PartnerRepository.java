package app.Dao.Repository;

import App.Model.User;
import App.Model.Partner;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long>{
    public boolean existsByUserId( User user );
    public Partner findById( long id );
    public Partner findByUserId( User user );    
    public ArrayList<Partner> findByType( String type );    
    public long countByType(String vip);
}