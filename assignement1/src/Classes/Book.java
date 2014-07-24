/**********************************************************************************
 * Class: Book																	  *
 **********************************************************************************
 * Definition of book attributes and methods to add,delete and list books from	  *	   
 * a .xml file.																	  *
 **********************************************************************************/

package Classes;
import java.io.File;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Book {
	 private int id_book;
	 private String author;
	 private String title;
	 private String genre;
	 private int bookshelf;
	 private String language;
	 private int npages;
	 private String resource;
	 private String review;
	 
	 
	 /*********************************************************************************
	  * 					         CONSTRUCTORS									  * 									  
	  *********************************************************************************/
	 public Book()
	 {
		 setId_book(0);
		 setAuthor("");
		 setTitle("");
		 setGenre("");
		 setBookshelf(0);
		 setLanguage(""); 
		 setNpages(0); 
		 setResource("");
		 setReview("");
	 }
	 
	 public Book(int v_id_book,String v_author,String v_title,String v_genre,int v_bookshelf,String v_language,int v_npages,String v_resource,String v_review)
	 { 
		 setId_book(v_id_book);
		 setAuthor(v_author);
		 setTitle(v_title);
		 setGenre(v_genre);
		 setBookshelf(v_bookshelf);
		 setLanguage(v_language); 
		 setNpages(v_npages); 
		 setResource(v_resource);
		 setReview(v_review);
	 }
	 
	 /*************************************************************************************************************************
	  * Method: add_books																	  								  *
	  *************************************************************************************************************************
	  * Read a "book" from extrabook.xml and it is added to book.xml.														  *														
	  *************************************************************************************************************************
	  * Web-Reference:http://stackoverflow.com/questions/4613140/xml-to-append-xml-document-into-the-node-of-another-document *
	  *************************************************************************************************************************/
	 public static void add_books () {
		 try {
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	         //GET PATH OF books.xml and extrabook.xml
	         String path_file = new java.io.File(".").getCanonicalPath() + "/files";
	         Document doc = docBuilder.parse (new File(path_file+"/books.xml"));
	         Document doc_add = docBuilder.parse (new File(path_file+"/extrabook.xml"));
	         doc.getDocumentElement ().normalize ();
	         doc_add.getDocumentElement ().normalize ();
	         
	         //GET node from two files
	         NodeList listBook = doc.getElementsByTagName("book");
	         NodeList listAddBook = doc_add.getElementsByTagName("book");
	         int totalBooks = listBook.getLength();
	         int totalAddBooks = listAddBook.getLength();
	         System.out.println("The number of books to be added: " + Integer.toString(totalAddBooks) );
	         //GET last node of books.xml
	         Node n=listBook.item(totalBooks-1);
	         //ADD elements in extrabook.xml to books.xml
	         for(int s=0; s<totalAddBooks ; s++){
	        	 Node m= listAddBook.item(s);
	        	 // APPEND the add node next to the last node of books.xml
	        	 Node imp = doc.importNode(m,true);
	        	 n.getParentNode().appendChild(imp); 
	         }	         
	         // SAVE to the xml file
	         TransformerFactory tf = TransformerFactory.newInstance();
	         Transformer t = tf.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult streamResult =  new StreamResult(new File(path_file+"/books.xml"));
	         t.transform(source, streamResult);
	         System.out.println();
	         System.out.println("Update the xml file succesfully!");
		 }
		 catch (SAXParseException err) {
		        System.err.println ("** Error on line: " 
			             + err.getLineNumber () + ", uri " + err.getSystemId ());
			    System.err.println(" " + err.getMessage ());
	        }catch (SAXException e) {
	        	Exception x = e.getException ();
	        	((x == null) ? e : x).printStackTrace ();
	        }catch (Throwable t) {
	        	t.printStackTrace ();
	        }
	 }
	 
	 /*************************************************************************************************************************
	  * Method: remove_books																	  							  *
	  *************************************************************************************************************************
	  * This method deletes a "book" from book.xml depending on the number provided as parameter.		  					  *														
	  *************************************************************************************************************************
	  * Web-Reference:http://stackoverflow.com/questions/7029427/java-xml-remove-item										  *
	  *************************************************************************************************************************/ 
	 public static void remove_books (int book_number){
		 try {
			 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	         //GET PATH OF books.xml
	         String path_file = new java.io.File(".").getCanonicalPath() + "/files";
	         Document doc = docBuilder.parse (new File(path_file+"/books.xml"));
	         doc.getDocumentElement ().normalize ();
	         NodeList listBook = doc.getElementsByTagName("book");
	         int totalBooks = listBook.getLength();
	         if(book_number>totalBooks){
	        	 System.out.println("There is no this book, please input a correct book number again!");
	        	 System.out.println();
	             System.exit(0);
	         }
	         else{
	        	// GET the remove book node
		         Element book = (Element)listBook.item(book_number-1);
		         book.getParentNode().removeChild(book);
		         System.out.println("Remove the " + Integer.toString(book_number) + "th book");
		         // SAVE to the xml file
		         TransformerFactory tf = TransformerFactory.newInstance();
		         Transformer t = tf.newTransformer();
		         DOMSource source = new DOMSource(doc);
		         StreamResult streamResult =  new StreamResult(new File(path_file+"/books.xml"));
		         t.transform(source, streamResult);
		         System.out.println();
		         System.out.println("Update the xml file succesfully!");
	         }	         
		 }catch (SAXParseException err) {
	        System.err.println ("** Error on line: " 
		             + err.getLineNumber () + ", uri " + err.getSystemId ());
		    System.err.println(" " + err.getMessage ());
        }catch (SAXException e) {
        	Exception x = e.getException ();
        	((x == null) ? e : x).printStackTrace ();
        }catch (Throwable t) {
        	t.printStackTrace ();
        }
	 }
	 
	 /*************************************************************************************************************************
	  * Method: list_books																		  							  *
	  *************************************************************************************************************************
	  * Displays all the "books" from books.xml file.													  					  *														
	  *************************************************************************************************************************
	  * Web-Reference:http://www.developerfusion.com/code/2064/a-simple-way-to-read-an-xml-file-in-java/ 					  *
	  *************************************************************************************************************************/ 
	 public static void list_books (){
		 try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            //GET PATH OF books.xml
            String path_file = new java.io.File(".").getCanonicalPath() + "/files";
            Document doc = docBuilder.parse (new File(path_file+"/books.xml"));
            doc.getDocumentElement ().normalize ();

            System.out.println("\n--------------------------LIST OF BOOKS-------------------------");
            NodeList listBook = doc.getElementsByTagName("book");
            int totalBooks = listBook.getLength();
            System.out.println("Total of Books : " + totalBooks);
            
            System.out.println("----------------------------------------------------------------");
            for(int s=0; s<totalBooks ; s++){
                Node book = listBook.item(s);
                Book actual_book=new Book();
                if(book.getNodeType() == Node.ELEMENT_NODE){
                	Element bookElement = (Element)book;

                	//--AUTHOR--
                    NodeList authorList = bookElement.getElementsByTagName("bks:author");
                    Element authorElement = (Element)authorList.item(0);

                    NodeList valueAuthorList = authorElement.getChildNodes();
                    actual_book.setAuthor(valueAuthorList.item(0).getNodeValue().toString());                 
               
                    //--TITLE--
                    NodeList titleList = bookElement.getElementsByTagName("bks:title");
                    Element titleElement = (Element)titleList.item(0);

                    NodeList valueTitleList = titleElement.getChildNodes();
                    actual_book.setTitle(valueTitleList.item(0).getNodeValue().toString());

                    //--GENRE--
                    NodeList genreList = bookElement.getElementsByTagName("bks:genre");
                    Element genreElement = (Element)genreList.item(0);

                    NodeList valueGenreList = genreElement.getChildNodes();
                    actual_book.setGenre(valueGenreList.item(0).getNodeValue().toString());
                    
                    //--NUMBER OF BOOKSHELF--
                    NodeList bookshelfList = bookElement.getElementsByTagName("bks:bookshelf");
                    Element bookshelfElement = (Element)bookshelfList.item(0);

                    NodeList valuebookshelfList = bookshelfElement.getChildNodes();
                    actual_book.setBookshelf(Integer.parseInt(valuebookshelfList.item(0).getNodeValue().toString()));

                    //--LANGUAGE--
                    NodeList languageList = bookElement.getElementsByTagName("bks:language");
                    Element languageElement = (Element)languageList.item(0);

                    NodeList valuelanguageList = languageElement.getChildNodes();
                    actual_book.setLanguage(valuelanguageList.item(0).getNodeValue().toString());

                    //--NUMBER OF PAGES--
                    NodeList npagesList = bookElement.getElementsByTagName("bks:npages");
                    Element npagesElement = (Element)npagesList.item(0);

                    NodeList valuenpagesList = npagesElement.getChildNodes();
                    actual_book.setNpages(Integer.parseInt(valuenpagesList.item(0).getNodeValue().toString()));

                    //--ADDITIONAL RESOURCE--
                    NodeList resourceList = bookElement.getElementsByTagName("bks:resource");
                    Element resourceElement = (Element)resourceList.item(0);

                    NodeList valueresourceList = resourceElement.getChildNodes();
                    actual_book.setResource(valueresourceList.item(0).getNodeValue().toString());
                    
                    //--REVIEW
                    NodeList reviewList = bookElement.getElementsByTagName("bks:review");
                    Element reviewElement = (Element)reviewList.item(0);

                    NodeList valuereviewList = reviewElement.getChildNodes();
                    actual_book.setReview(valuereviewList.item(0).getNodeValue().toString());
                    
                    
                    System.out.println(Integer.toString(s+1)+ "|"
                    			+ "Author:" +actual_book.getAuthor() + " \n " 
                    			+ " Title:" + actual_book.getTitle() + " \n "
                    			+ " Genre:" + actual_book.getGenre() + " \n "
         				   		+ " Bookshelf #: " + actual_book.getBookshelf() + "\n "
         				   		+ " Language: " + actual_book.getLanguage() + " \n "
         				   		+ " Number of pages: " + actual_book.getNpages() + "\n "
         				   		+ " Additional Resources: " + actual_book.getResource() + " \n "
         				   		+ " Review: " + actual_book.getReview());
                    System.out.println("-----------------------------------------------------------------");
                }
            }

	        }catch (SAXParseException err) {
	        System.err.println ("** Error on line: " 
	             + err.getLineNumber () + ", uri " + err.getSystemId ());
	        System.err.println(" " + err.getMessage ());
	
	        }catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();
	
	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }
	 }
	
	 /*********************************************************************************
	  * 					         GETTER AND SETTERS 							  * 									  
	  *********************************************************************************/
		public int getId_book() {
			return id_book;
		 }
	
		public void setId_book(int id_book) {
			this.id_book = id_book;
		}
	
		public String getAuthor() {
			return author;
		}
	
		public void setAuthor(String author) {
			this.author = author;
		}
	
		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}
	
		public String getGenre() {
			return genre;
		}
	
		public void setGenre(String genre) {
			this.genre = genre;
		}
	
		public int getBookshelf() {
			return bookshelf;
		}
	
		public void setBookshelf(int bookshelf) {
			this.bookshelf = bookshelf;
		}
	
		public String getLanguage() {
			return language;
		}
	
		public void setLanguage(String language) {
			this.language = language;
		}
	
		public int getNpages() {
			return npages;
		}
	
		public void setNpages(int npages) {
			this.npages = npages;
		}
	
		public String getResource() {
			return resource;
		}
	
		public void setResource(String resource) {
			this.resource = resource;
		}
	
		public String getReview() {
			return review;
		}
	
		public void setReview(String review) {
			this.review = review;
		}
	
}
