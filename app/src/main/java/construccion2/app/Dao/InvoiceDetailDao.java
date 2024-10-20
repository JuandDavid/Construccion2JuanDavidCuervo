package app.Dao;

import java.util.ArrayList;
import java.util.List;

import App.Dao.Interfaces.InvoiceDetailDaoInterface;
import App.Dao.Repository.InvoiceDetailRepository;

import App.Dto.InvoiceDto;
import App.Dto.InvoiceDetailDto;
import App.Model.InvoiceDetail;

import App.Helper.Helper;
import App.Model.Invoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter
@Service

public class InvoiceDetailDao implements InvoiceDetailDaoInterface {
    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public void createInvoiceDetail( InvoiceDetailDto invoiceDetailDto ) throws Exception {
        InvoiceDetail invoiceDetail = Helper.parse( invoiceDetailDto );
        this.invoiceDetailRepository.save( invoiceDetail );
    }
    
    @Override
    public void deleteInvoiceDetail( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        this.invoiceDetailRepository.deleteInvoiceDetailByInvoiceId( invoice );
    }

    @Override
    public double totalInvoiceDetails( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findByInvoiceId( invoice );
        double amount = 0;

        for ( InvoiceDetail invoiceDetail : invoiceDetailList ) {
            amount += invoiceDetail.getAmount();
        }
        return amount;
    }

    @Override
    public int countInvoiceDetails( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findByInvoiceId( invoice );
        int count = 1;
        
        for ( InvoiceDetail invoiceDetail : invoiceDetailList ) {
            count += 1;
        }
        return count;
    }

    @Override
    public ArrayList<InvoiceDetailDto> listInvoiceDetails( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        ArrayList<InvoiceDetailDto> listInvoiceDetails = new ArrayList<InvoiceDetailDto>();
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findByInvoiceId( invoice );
        for ( InvoiceDetail invoiceDetail : invoiceDetailList ) {
            listInvoiceDetails.add( Helper.parse( invoiceDetail ) );
        }
        return listInvoiceDetails;
    }
    
}