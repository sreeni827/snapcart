package com.snapcart.data.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CartDao_AppDatabase_0_Impl implements CartDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CartEntity> __insertionAdapterOfCartEntity;

  private final EntityDeletionOrUpdateAdapter<CartEntity> __deletionAdapterOfCartEntity;

  private final EntityDeletionOrUpdateAdapter<CartEntity> __updateAdapterOfCartEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearCart;

  public CartDao_AppDatabase_0_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartEntity = new EntityInsertionAdapter<CartEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cart_items` (`id`,`productName`,`title`,`price`,`quantity`,`imageResId`,`category`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CartEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.productName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.productName);
        }
        if (entity.title == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.title);
        }
        statement.bindDouble(4, entity.price);
        statement.bindLong(5, entity.quantity);
        statement.bindLong(6, entity.imageResId);
        if (entity.category == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.category);
        }
      }
    };
    this.__deletionAdapterOfCartEntity = new EntityDeletionOrUpdateAdapter<CartEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cart_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CartEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfCartEntity = new EntityDeletionOrUpdateAdapter<CartEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cart_items` SET `id` = ?,`productName` = ?,`title` = ?,`price` = ?,`quantity` = ?,`imageResId` = ?,`category` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CartEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.productName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.productName);
        }
        if (entity.title == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.title);
        }
        statement.bindDouble(4, entity.price);
        statement.bindLong(5, entity.quantity);
        statement.bindLong(6, entity.imageResId);
        if (entity.category == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.category);
        }
        statement.bindLong(8, entity.id);
      }
    };
    this.__preparedStmtOfClearCart = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cart_items";
        return _query;
      }
    };
  }

  @Override
  public void insertCart(final CartEntity cartEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCartEntity.insert(cartEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCart(final CartEntity cartEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCartEntity.handle(cartEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCart(final CartEntity cartEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCartEntity.handle(cartEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearCart() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearCart.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClearCart.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CartEntity>> getAllCartItems() {
    final String _sql = "SELECT * FROM cart_items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cart_items"}, false, new Callable<List<CartEntity>>() {
      @Override
      @Nullable
      public List<CartEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfImageResId = CursorUtil.getColumnIndexOrThrow(_cursor, "imageResId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<CartEntity> _result = new ArrayList<CartEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CartEntity _item;
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final int _tmpImageResId;
            _tmpImageResId = _cursor.getInt(_cursorIndexOfImageResId);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item = new CartEntity(_tmpProductName,_tmpPrice,_tmpQuantity,_tmpImageResId,_tmpCategory);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
