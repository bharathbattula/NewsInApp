package com.bharat.newsinapp.data;

/**
 * Created by Bharat on 9/13/2017.
 */
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class NewsProvider extends ContentProvider {


    public static final int NEWS_CODE = 101;
    public static final int NEWS_ITEM_CODE = 102;
    public static final int ENTERTAINMENT_CODE = 201;
    public static final int ENTERTAINMENT_ITEM_CODE = 202;
    public static final int SCIENCE_CODE = 301;
    public static final int SCIENCE_ITEM_CODE= 302;
    public static final int SPORTS_CODE = 401;
    public static final int SPORTS_ITEM_CODE = 402;
    public static final int TECHNOLOGY_CODE = 501;
    public static final int TECHNOLOGY_ITEM_CODE = 502;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static NewsDbHelper dbHelper;
    public static UriMatcher buildUriMatcher (){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NewsContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, NewsContract.PATH_NEWS,NEWS_CODE);
        uriMatcher.addURI(authority,NewsContract.PATH_NEWS+"/#",NEWS_ITEM_CODE);

        uriMatcher.addURI(authority,NewsContract.PATH_ENTERTAINMENT,ENTERTAINMENT_CODE);
        uriMatcher.addURI(authority,NewsContract.PATH_ENTERTAINMENT+"/#",ENTERTAINMENT_ITEM_CODE);

        uriMatcher.addURI(authority, NewsContract.PATH_SCIENCE,SCIENCE_CODE);
        uriMatcher.addURI(authority,NewsContract.PATH_SCIENCE+"/#",SCIENCE_ITEM_CODE);

        uriMatcher.addURI(authority, NewsContract.PATH_SPORTS,SPORTS_CODE);
        uriMatcher.addURI(authority,NewsContract.PATH_SPORTS+"/#",SPORTS_ITEM_CODE);

        uriMatcher.addURI(authority, NewsContract.PATH_TECHNOLOGY,TECHNOLOGY_CODE);
        uriMatcher.addURI(authority,NewsContract.PATH_TECHNOLOGY+"/#",TECHNOLOGY_ITEM_CODE);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new NewsDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case NEWS_CODE:
                db.beginTransaction();
                int rowsInserted = 0;
                try{
                    for (ContentValues value: values){
                        long _id = db.insert(NewsContract.NewsEntry.TABLE_NAME,null,value);
                        if (_id != -1){
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return rowsInserted;
            case ENTERTAINMENT_CODE:
                db.beginTransaction();
                int entertainmentRowsInserted = 0;
                try{
                    for (ContentValues value : values){
                        long _id = db.insert(NewsContract.EntertainmentEntry.TABLE_NAME,null,value);
                        if (_id != -1){
                            entertainmentRowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (entertainmentRowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return entertainmentRowsInserted;
            case SCIENCE_CODE:
                db.beginTransaction();
                int scienceRowsInserted = 0;
                try{
                    for (ContentValues value: values){
                        long _id = db.insert(NewsContract.ScienceEntry.TABLE_NAME,null,value);
                        if (_id != -1){
                            scienceRowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (scienceRowsInserted  > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return scienceRowsInserted ;
            case SPORTS_CODE:
                db.beginTransaction();
                int sportsRowsInserted = 0;
                try{
                    for (ContentValues value: values){
                        long _id = db.insert(NewsContract.SportsEntry.TABLE_NAME,null,value);
                        if (_id != -1){
                            sportsRowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (sportsRowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return sportsRowsInserted ;
            case TECHNOLOGY_CODE:
                db.beginTransaction();
                int techRowsInserted = 0;
                try{
                    for (ContentValues value: values){
                        long _id = db.insert(NewsContract.TechnologyEntry.TABLE_NAME,null,value);
                        if (_id != -1){
                            techRowsInserted ++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (techRowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return techRowsInserted ;
            default:
                return super.bulkInsert(uri,values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        String itemId = uri.getLastPathSegment();
        switch (sUriMatcher.match(uri)){
            case NEWS_CODE:
                cursor = queryAll(db, NewsContract.NewsEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case NEWS_ITEM_CODE:
                selection =  NewsContract.NewsEntry._ID+" = ?";
                selectionArgs = new String[]{itemId};
                cursor = queryAll(db, NewsContract.NewsEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case ENTERTAINMENT_CODE:
                cursor = queryAll(db, NewsContract.EntertainmentEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case ENTERTAINMENT_ITEM_CODE:
                selection =  NewsContract.EntertainmentEntry._ID+" = ?";
                selectionArgs = new String[]{itemId};
                cursor = queryAll(db, NewsContract.EntertainmentEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case SCIENCE_CODE:
                cursor = queryAll(db, NewsContract.ScienceEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case SCIENCE_ITEM_CODE:
                selection =  NewsContract.ScienceEntry._ID+" = ?";
                selectionArgs = new String[]{itemId};
                cursor = queryAll(db, NewsContract.ScienceEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case SPORTS_CODE:
                cursor = queryAll(db, NewsContract.SportsEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case SPORTS_ITEM_CODE:
                selection =  NewsContract.SportsEntry._ID+" = ?";
                selectionArgs = new String[]{itemId};
                cursor = queryAll(db, NewsContract.SportsEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case TECHNOLOGY_CODE:
                cursor = queryAll(db, NewsContract.TechnologyEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            case TECHNOLOGY_ITEM_CODE:
                selection =  NewsContract.TechnologyEntry._ID+" = ?";
                selectionArgs = new String[]{itemId};
                cursor = queryAll(db, NewsContract.TechnologyEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    private static Cursor queryAll(SQLiteDatabase db , String table, String[] projection,String selection, String[] selectionArgs,String sortOrder){
       Cursor cursor = db.query(table,
                projection,
                selection,
                selectionArgs,
                null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long item;
        switch (sUriMatcher.match(uri)){
            case NEWS_CODE:
                item =insert(NewsContract.NewsEntry.TABLE_NAME,values);
                return NewsContract.NewsEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(item)).build();
            case ENTERTAINMENT_CODE:
                item = insert(NewsContract.EntertainmentEntry.TABLE_NAME,values);
                return NewsContract.EntertainmentEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(item)).build();
            case SCIENCE_CODE:
                item =insert(NewsContract.ScienceEntry.TABLE_NAME,values);
                return NewsContract.ScienceEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(item)).build();
            case SPORTS_CODE:
                item =insert(NewsContract.SportsEntry.TABLE_NAME,values);
                return NewsContract.SportsEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(item)).build();
            case TECHNOLOGY_CODE:
                item =insert(NewsContract.TechnologyEntry.TABLE_NAME,values);
                return NewsContract.TechnologyEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(item)).build();
            default:
                throw new UnsupportedOperationException("Unknown uri : "+uri);
        }
    }
    private static long insert(String table,ContentValues values){
        long item = dbHelper.getWritableDatabase().insert(table,null,values);
        return item;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsDeleted;
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case NEWS_CODE:
                rowsDeleted = db.delete(NewsContract.NewsEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case ENTERTAINMENT_CODE:
                rowsDeleted = db.delete(NewsContract.EntertainmentEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case SCIENCE_CODE:
                rowsDeleted = db.delete(NewsContract.ScienceEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case SPORTS_CODE:
                rowsDeleted = db.delete(NewsContract.SportsEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case TECHNOLOGY_CODE:
                rowsDeleted = db.delete(NewsContract.TechnologyEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
