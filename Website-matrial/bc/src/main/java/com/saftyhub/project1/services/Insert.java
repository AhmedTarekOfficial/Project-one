package com.saftyhub.project1.repository ;
public class Insert  {

public Insert(String tablename , String [] insertion_values , 
String [] column_conditions){

System.out.println(querybuilder(tablename, insertion_values, column_conditions));
}

public String querybuilder(String tablename , String [] values 
, String [] columnsconditions
){
    StringBuilder insertionquery = new StringBuilder("insert into " + tablename+" ");
    boolean first_input = false ;
    int index = 0 ; 

    if(columnsconditions.length !=0){
      for (String conditions : columnsconditions)  {
        if (first_input!=true){
            insertionquery.append("("+conditions);
            first_input = true ;
        }else{
            if (conditions.length() !=0  && conditions != null ){
                insertionquery.append(","+conditions);
            }
            
        }
      }
    }

    first_input = false ;

    for (String value : values){
        if (first_input != true ){
            insertionquery.append("values("+values);
            first_input = true ;
        }else {
            if (value !=null || value.length() !=0) {
                 insertionquery.append(","+values+"");

            }else {
                insertionquery.append(")");
            }
    

        }


    }

    


    


    return insertionquery.toString() ;
}

// @Overload 
// public validate_value(String value) {

//     return "" ;
// }


public void put_Data() {

}

public static void main(String[] args) {
    Insert ahmed =  new Insert("ahmed", 
    new  String[] {"ahmed" , "ahmed"}, new String[] {});
}    


}
