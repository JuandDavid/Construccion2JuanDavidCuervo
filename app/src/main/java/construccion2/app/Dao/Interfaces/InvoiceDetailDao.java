package app.Dao.Interfaces;

import App.Dto.InvoiceDetailDto;
import java.util.List;

public interface InvoiceDetailDao {
  
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception;
   public  void updateInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception;
    public void deleteInvoiceDetail(long id) throws Exception;
    List<InvoiceDetailDto> getDetailsByInvoiceId(long invoiceId) throws Exception;
    InvoiceDetailDto getInvoiceDetailById(long id) throws Exception;
    
}