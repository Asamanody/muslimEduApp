package com.el3asas.eduapp.ui.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "azkar")
public class AzkarEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String category;
    private String count;
    private String description;
    private String reference;
    private String zekr;
    private boolean status;

    public AzkarEntity(int id, String category, String count, String description, String reference, String zekr, boolean status) {
        this.id = id;
        this.category = category;
        this.count = count;
        this.description = description;
        this.reference = reference;
        this.zekr = zekr;
        this.status = status;
    }

    protected AzkarEntity(Parcel in) {
        id = in.readInt();
        category = in.readString();
        count = in.readString();
        description = in.readString();
        reference = in.readString();
        zekr = in.readString();
        status = in.readByte() != 0;
    }

    public static final Creator<AzkarEntity> CREATOR = new Creator<AzkarEntity>() {
        @Override
        public AzkarEntity createFromParcel(Parcel in) {
            return new AzkarEntity(in);
        }

        @Override
        public AzkarEntity[] newArray(int size) {
            return new AzkarEntity[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getZekr() {
        return zekr;
    }

    public void setZekr(String zekr) {
        this.zekr = zekr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(category);
        parcel.writeString(count);
        parcel.writeString(description);
        parcel.writeString(reference);
        parcel.writeString(zekr);
        parcel.writeByte((byte) (status ? 1 : 0));
    }
}
