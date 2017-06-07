package org.catroid.catrobat.newui.db.util;

import android.net.Uri;
import android.provider.BaseColumns;

import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.data.Sprite;

public class DataContract {
    public static final String CONTENT_AUTHORITY = "org.catroid.catrobat.newui";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_PROJECT = "projects";
    public static final String PATH_SCENE = "scenes";
    public static final String PATH_SPRITE = "sprite";
    public static final String PATH_LOOK = "looks";
    public static final String PATH_SOUND = "sound";

    public static class ProjectEntry implements BaseColumns {
        public static final Uri PROJECT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECT).build();

        public static final String TABLE_NAME = "projects";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_INFO_TEXT = "info_text";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_LAST_ACCESS = "last_access";

        public static Uri getProjectUri(Project project) {
            return PROJECT_URI.buildUpon().appendPath(String.valueOf(project.getId())).build();
        }

        public static Uri getProjectUri(long id) {
            return PROJECT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_INFO_TEXT,
                    COLUMN_DESCRIPTION,
                    COLUMN_FAVORITE,
                    COLUMN_LAST_ACCESS
            };
        }
    }

    public static class SceneEntry implements BaseColumns {
        public static final Uri SCENE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCENE).build();

        public static final String TABLE_NAME = "scenes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PROJECT_ID = "project_id";

        public static Uri getSceneUri(Scene scene) {
            return getSceneUri(scene.getId());
        }

        public static Uri getSceneUri(long id) {
            return SCENE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_PROJECT_ID
            };
        }
    }


    public static class SpriteEntry implements BaseColumns {
        public static final Uri SPRITE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPRITE).build();

        public static final String TABLE_NAME = "sprites";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCENE_ID = "scene_id";

        public static Uri getSpriteUri(Sprite sprite) {
            return getSpriteUri(sprite.getId());
        }

        public static Uri getSpriteUri(long id) {
            return SPRITE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_SCENE_ID
            };
        }
    }

    public static class LookEntry implements BaseColumns {
        public static final Uri LOOKS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOOK).build();

        public static final String TABLE_NAME = "looks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FILENAME = "filename";
        public static final String COLUMN_SPRITE_ID = "sprite_id";

        public static Uri getLookUri(Sprite sprite) {
            return getLookUri(sprite.getId());
        }

        public static Uri getLookUri(long id) {
            return LOOKS_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_FILENAME,
                    COLUMN_SPRITE_ID
            };
        }
    }

    public static class SoundEntry implements BaseColumns {
        public static final Uri SOUNDS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOUND).build();

        public static final String TABLE_NAME = "sounds";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FILENAME = "filename";
        public static final String COLUMN_SPRITE_ID = "sprite_id";

        public static Uri getSoundUri(Sprite sprite) {
            return getSoundUri(sprite.getId());
        }

        public static Uri getSoundUri(long id) {
            return SOUNDS_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_FILENAME,
                    COLUMN_SPRITE_ID
            };
        }
    }

}
