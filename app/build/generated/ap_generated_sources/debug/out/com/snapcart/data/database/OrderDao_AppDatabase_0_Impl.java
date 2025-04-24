package com.snapcart.data.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_AppDatabase_0_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderEntity> __insertionAdapterOfOrderEntity;

  public OrderDao_AppDatabase_0_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderEntity = new EntityInsertionAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `orders` (`id`,`itemsSummary`,`totalAmount`,`address`,`paymentMethod`,`date`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final OrderEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.itemsSummary == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.itemsSummary);
        }
        statement.bindDouble(3, entity.totalAmount);
        if (entity.address == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.address);
        }
        if (entity.paymentMethod == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.paymentMethod);
        }
        final Long _tmp = DateConverter.dateToTimestamp(entity.date);
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
      }
    };
  }

  @Override
  public void insertOrder(final OrderEntity order) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfOrderEntity.insert(order);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public OrderEntity getLatestOrder() {
    final String _sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfItemsSummary = CursorUtil.getColumnIndexOrThrow(_cursor, "itemsSummary");
      final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final OrderEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new OrderEntity();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfItemsSummary)) {
          _result.itemsSummary = null;
        } else {
          _result.itemsSummary = _cursor.getString(_cursorIndexOfItemsSummary);
        }
        _result.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _result.address = null;
        } else {
          _result.address = _cursor.getString(_cursorIndexOfAddress);
        }
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _result.paymentMethod = null;
        } else {
          _result.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _result.date = DateConverter.fromTimestamp(_tmp);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<OrderEntity> getAllOrders() {
    final String _sql = "SELECT * FROM orders ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfItemsSummary = CursorUtil.getColumnIndexOrThrow(_cursor, "itemsSummary");
      final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final OrderEntity _item;
        _item = new OrderEntity();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfItemsSummary)) {
          _item.itemsSummary = null;
        } else {
          _item.itemsSummary = _cursor.getString(_cursorIndexOfItemsSummary);
        }
        _item.totalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _item.address = null;
        } else {
          _item.address = _cursor.getString(_cursorIndexOfAddress);
        }
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _item.paymentMethod = null;
        } else {
          _item.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _item.date = DateConverter.fromTimestamp(_tmp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
