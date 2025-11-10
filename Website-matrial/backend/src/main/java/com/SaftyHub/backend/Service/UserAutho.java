package  main.java.com.SaftyHub.backend.Service;
import main.java.com.SaftyHub.backend.repository.Databasehandeling.*;

@Service
public class UserAutho {

public final Fetching se ;
public final Insert pusshing ;

    
public boolean Login(String user_name , String password) {
   se = new Fetching("users",new String [] {"user_name" , "password"}, 
   new String[] {"user_name" , "password"}, new String [] {user_name , password});

   if (se == null ) {
    System.out.println("Please check you'r user_name and password and try again");;
    return false ;
   }else {
    System.out.println("Information found !");
    return true ;
   }

    return true ;
} 


public void Register(String user_name , String password , 
String department  , String job_title ) {

    user_check= new Enhancment("users", new String [] {"user_name" },
    new String [] {"user_name" }
    , new String [] { user_name });

    if (user_check !=null) {
        System.out.println("Sorry but this username already exist please try another one ");
    }else {

        pu = new Insert("users", new String [] {user_name , password , department , job_title}, new  String [] {}) ;

        
    }

   



}

    
}
