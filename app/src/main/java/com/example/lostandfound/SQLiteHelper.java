package com.example.lostandfound;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper {

    public List<String[]> retrieveTableData(String databaseName, String tableName) {
        List<String[]> tableData = new ArrayList<>();

        // Open the SQLite database
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databaseName, null, SQLiteDatabase.OPEN_READONLY);

        // Query the table to retrieve all data
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        // Retrieve column names
        String[] columnNames = cursor.getColumnNames();
        tableData.add(columnNames);

        if (cursor.moveToFirst()) {
            do {
                // Retrieve data for each row
                String[] rowData = new String[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = cursor.getString(i);
                }
                tableData.add(rowData);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return tableData;
    }
}
