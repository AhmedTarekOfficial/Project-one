public class Searching {

    public Searching(String tablename , String [] Searching_columns ,
     String [] Searching_columncondition , String [] searchingcolumnconditionvalues){
        System.out.println(query_builder(tablename ,Searching_columns ,Searching_columncondition, searchingcolumnconditionvalues));
    }

    public String query_builder(String tablename , String [] Searching_columns ,
            String [] Searching_columncondition , String [] searchingcolumnconditionvalues){
                StringBuilder searching_query = new StringBuilder("select ");
                boolean first_input  = false ;
                int index = 0 ;

                for (String columns : Searching_columns){
                    if (first_input !=true){
                        searching_query.append(columns);
                        first_input = true ;
                    }else {
                        if (columns.length() !=0 || columns !=null){
                             searching_query.append(","+columns);
                        }else {
                         searching_query.append(" from " + tablename);
                        }
                    }
                }

                first_input = false ;
                if (Searching_columncondition.length !=0 || searchingcolumnconditionvalues.length!=0){
                    for (String conditioncolumns : Searching_columncondition){
                        if (first_input !=true){
                            searching_query.append(" where "+conditioncolumns +"="+searchingcolumnconditionvalues[index]);
                            ++index ;
                            first_input = true ;
                        }else {
                            searching_query.append(","+conditioncolumns +"="+searchingcolumnconditionvalues[index]);
                            ++index ;
                        }
                    }
                }

         return searching_query.toString();
    }

    public static void main(String[] args) {
            Searching ff = new Searching("ahmed", new String[] {"ahmed" , "mohamed"}, new String[] {}, new String[] {} );
    }
}