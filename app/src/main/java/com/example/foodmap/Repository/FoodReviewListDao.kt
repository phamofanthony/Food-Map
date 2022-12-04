package  com.example.foodmap.Repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodReviewListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reviewItem: FoodReviewItem)

    @MapInfo(keyColumn = "postID")
    @Query("SELECT * FROM food_reviews_table order by postID ASC")
    fun getReviewItems(): Flow<Map<Int, FoodReviewItem>>

    @Query("SELECT * FROM food_reviews_table WHERE postID = :id")
    suspend fun getItem(id: Int): FoodReviewItem

    @Query("DELETE FROM food_reviews_table")
    suspend fun deleteAll()

    @Query("DELETE FROM food_reviews_table WHERE postID=:id")
    suspend fun deleteItem(id: Int)

    @Update
    suspend fun updateItem(reviewItem: FoodReviewItem)


}