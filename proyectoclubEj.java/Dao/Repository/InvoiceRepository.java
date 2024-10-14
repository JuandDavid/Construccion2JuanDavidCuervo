package App.Dao.Repository;

import App.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import App.Model.Partner;
import App.Model.Person;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    public List<Invoice> findByPersonId(Person person);
    public List<Invoice> findByPartnerId(Partner partner);
}