package app.Dao.Repository;

import App.Model.Invoice;
import App.Model.InvoiceDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailDao,Long>{
    @Transactional
    public void deleteInvoiceDetailByInvoiceId( Invoice invoice );
    public List<InvoiceDetail> findByInvoiceId( Invoice invoice );
    
}