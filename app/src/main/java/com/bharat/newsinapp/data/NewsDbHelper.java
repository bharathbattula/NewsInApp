package com.bharat.newsinapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bharat.newsinapp.data.NewsContract.*;
/**
 * Created by Bharat on 9/11/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";

    private static final int DATABASE_VERSION = 3;

    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQl_CREATE_NEWS_TABLE =
                "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +
                        NewsEntry._ID       +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NewsEntry.COLUMN_TITLE          +" TEXT NOT NULL," +
                        NewsEntry.COLUMN_DESCRIPTION    +" TEXT NOT NULL," +
                        NewsEntry.COLUMN_URL_TO_SOURCE  +" TEXT NOT NULL," +
                        NewsEntry.COLUMN_DATE           +" TEXT NOT NULL," +
                        NewsEntry.COLUMN_IMAGE          +" TEXT NOT NULL )" ;
        final String SQl_CREATE_ENTERTAINMENT_TABLE =
                "CREATE TABLE " + EntertainmentEntry.TABLE_NAME + " (" +
                        EntertainmentEntry._ID       +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        EntertainmentEntry.COLUMN_TITLE          +" TEXT NOT NULL," +
                        EntertainmentEntry.COLUMN_DESCRIPTION    +" TEXT NOT NULL," +
                        EntertainmentEntry.COLUMN_URL_TO_SOURCE  +" TEXT NOT NULL," +
                        EntertainmentEntry.COLUMN_DATE           +" TEXT NOT NULL," +
                        EntertainmentEntry.COLUMN_IMAGE          +" TEXT NOT NULL )" ;
        final String SQl_CREATE_SCIENCE_TABLE =
                "CREATE TABLE " + ScienceEntry.TABLE_NAME + " (" +
                        ScienceEntry._ID       +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ScienceEntry.COLUMN_TITLE          +" TEXT NOT NULL," +
                        ScienceEntry.COLUMN_DESCRIPTION    +" TEXT NOT NULL," +
                        ScienceEntry.COLUMN_URL_TO_SOURCE  +" TEXT NOT NULL," +
                        ScienceEntry.COLUMN_DATE           +" TEXT NOT NULL," +
                        ScienceEntry.COLUMN_IMAGE          +" TEXT NOT NULL )" ;
        final String SQl_CREATE_SPORTS_TABLE =
                "CREATE TABLE " + SportsEntry.TABLE_NAME + " (" +
                        SportsEntry._ID       +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SportsEntry.COLUMN_TITLE          +" TEXT NOT NULL," +
                        SportsEntry.COLUMN_DESCRIPTION    +" TEXT NOT NULL," +
                        SportsEntry.COLUMN_URL_TO_SOURCE  +" TEXT NOT NULL," +
                        SportsEntry.COLUMN_DATE           +" TEXT NOT NULL," +
                        SportsEntry.COLUMN_IMAGE          +" TEXT NOT NULL )" ;
        final String SQl_CREATE_TECHNOLOGY_TABLE =
                "CREATE TABLE " + TechnologyEntry.TABLE_NAME + " (" +
                        TechnologyEntry._ID       +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TechnologyEntry.COLUMN_TITLE          +" TEXT NOT NULL," +
                        TechnologyEntry.COLUMN_DESCRIPTION    +" TEXT NOT NULL," +
                        TechnologyEntry.COLUMN_URL_TO_SOURCE  +" TEXT NOT NULL," +
                        TechnologyEntry.COLUMN_DATE           +" TEXT NOT NULL," +
                        TechnologyEntry.COLUMN_IMAGE          +" TEXT NOT NULL )" ;
        db.execSQL(SQl_CREATE_NEWS_TABLE);
        db.execSQL(SQl_CREATE_ENTERTAINMENT_TABLE);
        db.execSQL(SQl_CREATE_SCIENCE_TABLE);
        db.execSQL(SQl_CREATE_SPORTS_TABLE);
        db.execSQL(SQl_CREATE_TECHNOLOGY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+EntertainmentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ScienceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SportsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TechnologyEntry.TABLE_NAME);
        onCreate(db);
    }
}
