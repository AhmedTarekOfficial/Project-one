import java.sql.*;

import main.java.com.SaftyHub.backend.Databasehandeling.Link;

import java.io.*;


public class automation {

    public static void Getdata(){
        final String OUTPUT_DIR = "src/main/java/com/example/models/";
        Link database = new Link();
        try (Connection conn = database.Connection()) {
            DatabaseMetaData meta = conn.getMetaData();

            ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                generateModelClass(meta, tableName);
            }

            System.out.println(" Model classes generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
       
    

    private static void generateModelClass(DatabaseMetaData meta, String tableName) throws Exception {
        ResultSet columns = meta.getColumns(null, null, tableName, null);

        StringBuilder classContent = new StringBuilder();
        String className = toCamelCase(tableName, true);

        classContent.append("package com.example.models;\n\n");
        classContent.append("import jakarta.persistence.*;\n\n");
        classContent.append("@Entity\n@Table(name = \"" + tableName + "\")\n");
        classContent.append("public class " + className + " {\n\n");

        while (columns.next()) {
            String colName = columns.getString("COLUMN_NAME");
            String colType = mapSQLType(columns.getInt("DATA_TYPE"));
            classContent.append("    private " + colType + " " + toCamelCase(colName, false) + ";\n");
        }

        classContent.append("}\n");

        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        FileWriter fw = new FileWriter(OUTPUT_DIR + className + ".java");
        fw.write(classContent.toString());
        fw.close();
    }

    private static String mapSQLType(int sqlType) {
        return switch (sqlType) {
            case Types.INTEGER, Types.SMALLINT, Types.TINYINT -> "Integer";
            case Types.BIGINT -> "Long";
            case Types.FLOAT, Types.REAL, Types.DOUBLE -> "Double";
            case Types.DECIMAL, Types.NUMERIC -> "java.math.BigDecimal";
            case Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR -> "String";
            case Types.DATE -> "java.sql.Date";
            case Types.TIMESTAMP -> "java.sql.Timestamp";
            case Types.BOOLEAN, Types.BIT -> "Boolean";
            default -> "String"; 
        };
    }

    private static String toCamelCase(String text, boolean capitalizeFirst) {
        StringBuilder result = new StringBuilder();
        boolean capitalize = capitalizeFirst;
        for (char c : text.toCharArray()) {
            if (c == '_' || c == ' ') {
                capitalize = true;
            } else if (capitalize) {
                result.append(Character.toUpperCase(c));
                capitalize = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
