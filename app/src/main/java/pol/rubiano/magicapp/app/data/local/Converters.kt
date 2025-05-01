package pol.rubiano.magicapp.app.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pol.rubiano.magicapp.features.collections.data.local.CardInCollectionEntity
import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection
import pol.rubiano.magicapp.features.decks.data.local.CardInDeckEntity
import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromCardInCollectionList(value: String?): List<CardInCollection> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<CardInCollection>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toCardInCollectionList(list: List<CardInCollection>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromCardInCollectionEntityList(value: String?): List<CardInCollectionEntity> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<CardInCollectionEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toCardInCollectionEntityList(list: List<CardInCollectionEntity>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromCardInDeckList(value: String?): List<CardInDeck> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<CardInDeck>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toCardInDeckList(list: List<CardInDeck>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromCardInDeckEntityList(value: String?): List<CardInDeckEntity> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<CardInDeckEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toCardInDeckEntityList(list: List<CardInDeckEntity>?): String? {
        return Gson().toJson(list)
    }
}