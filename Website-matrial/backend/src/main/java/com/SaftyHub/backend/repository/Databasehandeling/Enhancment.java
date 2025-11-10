package main.java.com.SaftyHub.backend.repository.Databasehandeling ;
public class Enhancment {


    public Enhancment(String tablename , String [] updated_columns ,
     String []  updated_columns_values , String [] conditions  , String [] columns_conditions_value){

     System.out.println(Querybuilder(tablename ,  updated_columns , updated_columns_values , conditions , columns_conditions_value )) ;


     }
public String Querybuilder(String tablename ,
String [] updated_columns , String [] updated_columns_values , String [] conditions , 
String [] columns_conditions_value
){
    StringBuilder Query = new StringBuilder("update ") ;
    boolean first_input = false ; 
    int index=0 ;

    for (String update : updated_columns) {
        if (first_input == false){
            Query.append(tablename+" "+update+"="+updated_columns_values[index]) ;
            ++index ;
            first_input = true ;
        }else {
             Query.append(","+" "+update+"="+updated_columns_values[index]) ;
             ++index ;
        }

    }
    first_input = false ; 
    index = 0 ;

    if ( conditions.length != 0 && columns_conditions_value.length != 0)  {
        for (String columns :conditions  )
        if (first_input != true ){
            Query.append("where "+columns+"="+columns_conditions_value[index]);
            ++index ;
            first_input = true ;
        }else {
             Query.append(","+columns+"="+columns_conditions_value[index]);
             ++index ;
        }
        
    }


   
    return Query.toString() ;
}

    public void Updateinformation(
String Query 
    ){

    }
    


}


