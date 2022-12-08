package cf.spacetaste.backend.mapper;

import cf.spacetaste.backend.model.AverageStarModel;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MatzipMapper {

    @Select("SELECT * FROM matzip WHERE matzip_id = #{id} LIMIT 1")
    MatzipModel getFromId(int id);

    @Select("SELECT COUNT(*) FROM matzip WHERE matzip_id=#{id}")
    int checkIdExists(int id);

    @Select("SELECT DISTINCT a.* FROM matzip AS a INNER JOIN matzip_hashtag AS b ON a.matzip_id = b.matzip_id WHERE b.hashtag_id = #{hashtagId} LIMIT 100")
    List<MatzipModel> getFromHashtag(int hashtagId);

    @Select("""
<script>
  SELECT DISTINCT a.* FROM matzip AS a LEFT JOIN matzip_hashtag AS b ON a.matzip_id = b.matzip_id
  <where>
    <if test="requiredTags != null and requiredTags.size != 0">
      b.hashtag_id IN
      <foreach item="item" collection="requiredTags" open="(" separator="," close=")">
        #{item}
      </foreach>
    </if>
    <trim prefix="AND (" prefixOverrides="AND |OR " suffix=")">
      <if test="optionalTags != null and optionalTags.size != 0">
        b.hashtag_id IN
        <foreach item="item" collection="optionalTags" open="(" separator="," close=")">
          #{item}
        </foreach>
      </if>
      <if test="term != null and term != ''">
        OR a.address_base LIKE #{term} OR a.address_detail LIKE #{term} OR a.name LIKE #{term}
      </if>
    </trim>
  </where>
  ORDER BY a.matzip_id DESC LIMIT 100
</script>""")
    List<MatzipModel> search(List<Integer> requiredTags, List<Integer> optionalTags, String term);

    @Select("SELECT a.* FROM matzip AS a INNER JOIN review AS b ON a.matzip_id = b.matzip_id WHERE b.user_id = #{userId} ORDER BY b.review_id DESC LIMIT 50")
    List<MatzipModel> listByReviewedUser(int userId);

    @Insert("INSERT INTO matzip (name, address_code, address_base, address_detail, main_photo) VALUES (#{name}, #{addressCode}, #{addressBase}, #{addressDetail}, #{mainPhoto})")
    @Options(useGeneratedKeys = true, keyProperty = "matzipId")
    int create(MatzipModel matzip);

    @Update("UPDATE matzip SET average_score_taste=#{info.taste}, average_score_price=#{info.price}, average_score_kindness=#{info.kindness}, average_score_clean=#{info.clean} WHERE matzip_id=#{matzip.matzipId}")
    int updateStar(MatzipModel matzip, AverageStarModel info);

    //int update(MatzipModel matzip);

    @Delete("DELETE FROM matzip WHERE matzip_id=#{matzipId}")
    int delete(MatzipModel matzip);
}
