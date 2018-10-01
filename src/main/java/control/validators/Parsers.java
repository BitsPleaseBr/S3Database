package control.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Parsers {

  public static void main(String[] args) {
    System.out.println(dateParse("08/08/2002"));
  }
  
  public static String removeNonNum(String str) {
    return str.replaceAll("\\D", "");
  }


  public static String removeNonNum(Object str) {
    return removeNonNum(str.toString());
  }

  public static String cepParse(Object CEP) {
    String str = removeNonNum(CEP.toString());
    return str.length() == 8 ? str : null;
  }

  public static String cpfParse(Object CPF) {
    String str = removeNonNum(CPF.toString());
    return str.length() == 11 ? str : null;
  }

  public static String dateParse(Object data) {
    
    DateFormat bdFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat brformat = new SimpleDateFormat("dd/MM/yyyy");

    try {
      
      if (data.toString().matches("\\d{2}/\\d{2}/\\d{1,4}")) {
        
        return brformat.format(brformat.parse(data.toString()));
      } else if (data.toString().matches("\\d{1,4}-\\d{2}-\\d{2}")) {
        
        return brformat.format(bdFormat.parse(data.toString()));
      }
    } catch (ParseException e) {
      
      e.printStackTrace();
    }
    
    return null;
  }
}
