package com.bharat.newsinapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Bharat on 9/11/2017.
 */

public class NewsContract {
    /*
   * The "Content authority" is a name for the entire content provider, similar to the
   * relationship between a domain name and its website. A convenient string to use for the
   * content authority is the package name for the app, which is guaranteed to be unique on the
   * Play Store.
   */
    public static final String CONTENT_AUTHORITY = "com.bharat.newsinapp";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider for NewsInApp.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
     /*
     * Possible paths that can be appended to BASE_CONTENT_URI to form valid URI's that NewsInApp
     * can handle. For instance,
     *
     *     content://com.bharat.newsinapp/news/
     *     [           BASE_CONTENT_URI         ][ PATH_NEWS ]
     *
     * is a valid path for looking at news data.
     */
     public static final String PATH_NEWS = "news";

    public static final String PATH_ENTERTAINMENT = "entertainment";

    public static final String PATH_SCIENCE = "science";

    public static final String PATH_SPORTS = "sports";

    public static final String PATH_TECHNOLOGY = "technology";

    public static final class NewsEntry extends CommonEntry{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NEWS).build();

        public static final String TABLE_NAME = "news";

    }
    public static class CommonEntry implements BaseColumns{
        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_URL_TO_SOURCE = "urltosource";

        public static final String COLUMN_DATE = "date";

        public static final String COLUMN_IMAGE = "image";
    }

    public static class EntertainmentEntry extends CommonEntry {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ENTERTAINMENT).build();

        public static final String TABLE_NAME = PATH_ENTERTAINMENT;
    }

    public static class ScienceEntry extends CommonEntry{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SCIENCE).build();

        public static final String TABLE_NAME = PATH_SCIENCE;
    }
    public static class SportsEntry extends CommonEntry{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SPORTS).build();

        public static final String TABLE_NAME = PATH_SPORTS;
    }
    public static class TechnologyEntry extends CommonEntry{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TECHNOLOGY).build();

        public static final String TABLE_NAME = PATH_TECHNOLOGY;
    }
}
