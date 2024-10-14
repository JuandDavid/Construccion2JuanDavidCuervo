package App.Dao.Interfaces;

import app.Dto.InvoiceDto;
import java.util.List;

public interface InvoiceDao {
    
    public void createInvoice(InvoiceDtoInterface invoiceDto) throws Exception;
    public InvoiceDtoInterface findInvoiceById( Long id) throws Exception;
    public void updateInvoice(InvoiceDtoInterface invoiceDto) throws Exception;
   public  void deleteInvoice(Long id) throws Exception;

    public List<InvoiceDtoInterface> findAllInvoices();
    
}