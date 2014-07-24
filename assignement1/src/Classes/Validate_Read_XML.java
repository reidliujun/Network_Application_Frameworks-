/**********************************************************************************
 * Class: Validate_Read_XML														  *
 **********************************************************************************
 * Main class that performs validation of scheme and also allows to list, add and * 		
 * delete the books created on books.xml 										  *
 **********************************************************************************/
package Classes;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


public class Validate_Read_XML {

/****************************************************************************************
  * Method: main																	    *
  ***************************************************************************************
  * Main program that receives arguments to execute the methods.						*														
  ***************************************************************************************
  * Web-Reference:http://tutorials.jenkov.com/java-xml/dom-schema-validation.html		*
  ***************************************************************************************/
  public static void main(String[] args) throws Exception {
    String name_file = "books";
    //GET PATH OF FILES
    String path_file = new java.io.File(".").getCanonicalPath() + "/files/books";
    if (args.length == 0) {
      System.out.println("You did not specify what to do.");
      System.out.println("Use command-line argument: validate|list");
      System.out.println();
      System.exit(0);
    }
    // Validate the xml file
    else if (args[0].equals("validate")){
      try {  
        // Type of schema - W3C
        String schemaLang = "http://www.w3.org/2001/XMLSchema";
        SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
        // Create schema by reading it from an XSD file:
        Schema schema = factory.newSchema(new StreamSource(path_file +".xsd"));
        Validator validator = schema.newValidator();
        // Validation of xml file
        validator.validate(new StreamSource(path_file +".xml"));
        System.out.println("-------------------------------------");
        System.out.println("   Document"+" "+name_file+".xml is valid");
        System.out.println("-------------------------------------");
      }catch (SAXException ex) {
        //Throw exception.
    	System.out.println("-------------------------------------");
        System.err.println("Document"+" "+name_file+".xml is not valid");
        System.out.println("-------------------------------------");
        ex.printStackTrace();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    // List the books in xml file
    else if (args[0].equals("list")) {
      //CALL TO LIST BOOKS
      Book.list_books();
    }
    else if (args[0].equals("del") && args.length != 2) {
    	System.out.println("'del' command use invalid");
        System.out.println("Use argument as: del 1/2");
        System.out.println();
        System.exit(0);
    }
    // Delete the books in xml file
    else if (args[0].equals("del") && args.length == 2) {    	
    	Book.remove_books(Integer.parseInt(args[1]));
    }
    else if (args[0].equals("add")) {
    	Book.add_books();
    }
  }
}