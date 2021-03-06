package org.schabi.newpipe.database.history.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.schabi.newpipe.database.history.model.SearchHistoryEntry;

import java.util.List;

import io.reactivex.Flowable;

import static org.schabi.newpipe.database.history.model.SearchHistoryEntry.CREATION_DATE;
import static org.schabi.newpipe.database.history.model.SearchHistoryEntry.ID;
import static org.schabi.newpipe.database.history.model.SearchHistoryEntry.SERVICE_ID;
import static org.schabi.newpipe.database.history.model.SearchHistoryEntry.TABLE_NAME;

@Dao
public interface SearchHistoryDAO extends HistoryDAO<SearchHistoryEntry> {

    String ORDER_BY_CREATION_DATE = " ORDER BY " + CREATION_DATE + " DESC";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = (SELECT MAX(" + ID + ") FROM " + TABLE_NAME + ")")
    @Override
    SearchHistoryEntry getLatestEntry();

    @Query("DELETE FROM " + TABLE_NAME)
    @Override
    int deleteAll();

    @Query("SELECT * FROM " + TABLE_NAME + ORDER_BY_CREATION_DATE)
    @Override
    Flowable<List<SearchHistoryEntry>> findAll();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE " + SERVICE_ID + " = :serviceId" + ORDER_BY_CREATION_DATE)
    @Override
    Flowable<List<SearchHistoryEntry>> listByService(int serviceId);
}
