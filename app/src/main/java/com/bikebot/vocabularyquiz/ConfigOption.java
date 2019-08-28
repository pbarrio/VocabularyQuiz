package com.bikebot.vocabularyquiz;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
