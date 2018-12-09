package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ConfigOption {

    @PrimaryKey
    @NonNull
    public String option;

    @ColumnInfo
    public String value;

    public ConfigOption(String option, String value) {
        this.option = option;
        this.value = value;
    }
}
