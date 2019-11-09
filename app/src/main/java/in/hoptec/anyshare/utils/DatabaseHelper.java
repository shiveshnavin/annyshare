package in.hoptec.anyshare.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import in.hoptec.anyshare.model.ChatMessage;
import in.hoptec.anyshare.utl;

import static in.hoptec.anyshare.Constants.C2C_DELETE;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Chats.db";
    public static final String TABLE_NAME = "chats";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String [] fieldsString= {"groupName","groupId","senderName","senderId","message","icon","attachmentUrl","atachmentType"};

       String query="create table " + TABLE_NAME   +" (id INTEGER PRIMARY KEY AUTOINCREMENT , dateTime INTEGER";

        for(String field:fieldsString)
        {
            query+=","+field+" TEXT";
        }
        query+=",read INTEGER);";

        utl.e(DATABASE_NAME,""+query);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(ChatMessage cm) {

        if(cm.getMessage().contains(C2C_DELETE))
        {
            String id=cm.getMessage().replace(C2C_DELETE,"");
            utl.e("Chats","Delete : "+id);
            deleteData(id);
            return true;
        }


        if (getMessageById(cm.getId()).getCount()>0)
        {
            utl.e("dbHelper","Message Already Inserted");
           return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",cm.getId());
        contentValues.put("dateTime",cm.getDateTime());
        contentValues.put("groupName",cm.getGroupName());
        contentValues.put("groupId",cm.getGroupId());
        contentValues.put("senderName",cm.getSenderName());
        contentValues.put("senderId",cm.getSenderId());
        contentValues.put("message",cm.getMessage());
        contentValues.put("icon",cm.getIcon());
        contentValues.put("attachmentUrl",cm.getAttachmentUrl());
        contentValues.put("atachmentType",cm.getAtachmentType());
        contentValues.put("read",cm.getRead());

        long result = 0;
        try {
            result = db.insert(TABLE_NAME,null ,contentValues);
        } catch (Exception e) {

        }
        if(result == -1)
            return false;
        else
            return true;
    }



    public ArrayList<ChatMessage> convertCursorToArray(Cursor res)
    {
        ArrayList<ChatMessage> cms=new ArrayList<>() ;


        while (res.moveToNext())
        {
            ChatMessage cm=new ChatMessage(
                    res.getString(res.getColumnIndex("id")),
                    res.getLong(res.getColumnIndex("dateTime")),
                    res.getString(res.getColumnIndex("groupName")),
                    res.getString(res.getColumnIndex("groupId")),
                    res.getString(res.getColumnIndex("senderName")),
                    res.getString(res.getColumnIndex("senderId")),
                    res.getString(res.getColumnIndex("message")),
                    res.getString(res.getColumnIndex("icon")),
                    res.getString(res.getColumnIndex("attachmentUrl")),
                    res.getString(res.getColumnIndex("atachmentType")),
                    res.getInt(res.getColumnIndex("read"))==1

            );
            //-- utl.e(DATABASE_NAME,"Chat Latest : "+cm.getMessage());

            cms.add(cm);
        }

        return cms;

    }

    public Cursor getMessageById(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="select * from "+TABLE_NAME+" WHERE id="+id;

        Cursor res = db.rawQuery(q,null);

        return res;
    }

    public ChatMessage getLatestMessage()
    {
        return getLatestMessage("");

    }

    public ChatMessage getLatestMessage(String groupId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="select * from "+TABLE_NAME+" ORDER BY dateTime DESC LIMIT 1";

        if(groupId.length()>1)
            q="select * from "+TABLE_NAME+" WHERE groupId='"+groupId+"' ORDER BY dateTime DESC LIMIT 1";

        Cursor res = db.rawQuery(q,null);
        ChatMessage cm=null;

        while (res.moveToNext())
        {
            cm=new ChatMessage(
                    res.getString(res.getColumnIndex("id")),
                    res.getLong(res.getColumnIndex("dateTime")),
                    res.getString(res.getColumnIndex("groupName")),
                    res.getString(res.getColumnIndex("groupId")),
                    res.getString(res.getColumnIndex("senderName")),
                    res.getString(res.getColumnIndex("senderId")),
                    res.getString(res.getColumnIndex("message")),
                    res.getString(res.getColumnIndex("icon")),
                    res.getString(res.getColumnIndex("attachmentUrl")),
                    res.getString(res.getColumnIndex("atachmentType")),
                    res.getInt(res.getColumnIndex("read"))==1

            );
            //-- utl.e(DATABASE_NAME,"Chat Latest : "+cm.getMessage());
        }
        return cm;
    }

    public ArrayList<ChatMessage> getAllMessagesForGroup(String groupId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="select * from "+TABLE_NAME+" ORDER BY dateTime ASC";

        if(groupId.length()>1)
            q="select * from "+TABLE_NAME+" WHERE groupId='"+groupId+"' ORDER BY dateTime DESC";

        Cursor res = db.rawQuery(q,null);

        return convertCursorToArray(res);
    }


    public int getUnreadCountForGroup(String groupId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="select * from "+TABLE_NAME+" WHERE groupId='"+groupId+"' AND read=0 ";

        Cursor res = db.rawQuery(q,null);

        return res.getCount();
    }

    public ArrayList<ChatMessage> getAllMessages()
    {
        return getAllMessagesForGroup("");
    }

    public ArrayList<ChatMessage> getLatestMessagesOfAllGroups()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="select * from "+TABLE_NAME+" GROUP BY groupId ORDER BY dateTime DESC";

        Cursor res = db.rawQuery(q,null);

        return convertCursorToArray(res);
    }

    public boolean makeSeen(String groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("read",1);

        db.update(TABLE_NAME, contentValues, "groupId = ?",new String[] {groupId});
        return true;
    }

    public boolean updateData(ChatMessage cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id",cm.getId());

        db.update(TABLE_NAME, contentValues, "id = ?",new String[] { cm.getId() });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?",new String[] {id});
    }


    public Integer deleteGroup (String groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "groupId = ?",new String[] {groupId});
    }

    public Integer deleteAllData ( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "1",null);
    }
}

