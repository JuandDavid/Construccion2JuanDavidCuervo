package app.Dto.Interfaces;

public interface PersonDtoInterface {
    public void getPersonNameDto() throws Exception;
    public void getPersonCellNumberDto() throws Exception;
    public void getPersonDocumentDto() throws Exception;    
    public void getPersonDocumentDto( String message ) throws Exception;    
}