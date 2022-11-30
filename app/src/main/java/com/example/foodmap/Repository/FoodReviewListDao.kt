package  com.example.foodmap.Repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodReviewListDao {

    @MapInfo(keyColumn = "id")
    @Query("SELECT * FROM food_reviews_table order by id ASC")
    fun getReviewItems(): Flow<Map<Int, FoodReviewItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reviewItem: FoodReviewItem)

    @Query("DELETE FROM food_reviews_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM food_reviews_table WHERE id = :id")
    suspend fun getItem(id: Int): FoodReviewItem

    @Query("UPDATE food_reviews_table SET completed=:completed WHERE id=:reviewID")
    suspend fun updateCompleted(reviewID: Int, completed: Int)

    @Query("DELETE FROM food_reviews_table WHERE id=:id")
    suspend fun deleteItem(id: Int)

    @Update
    suspend fun updateItem(reviewItem: FoodReviewItem)

}