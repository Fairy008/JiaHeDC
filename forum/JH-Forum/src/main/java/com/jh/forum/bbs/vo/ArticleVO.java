package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumArticle;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.cache.base.ForumIdTransform;
import com.jh.forum.cache.vo.ForumCacheVO;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
* @Description:    论坛报告/问答/数据分享/约调研
* @Author:         mason
* @CreateDate:     2019/3/5 14:57
* @Version:        1.0
*/
public class ArticleVO extends ForumArticle {

    //搜索关键词
    private String keyWords ;

    //评论列表
    private List<ForumComment> commentList;

    //关注与点赞列表
    private List<ForumFollow> followList;

    private String imageUrl;

    //后台管理页面新增帖子标志
    private String adminFlag;

    //评论数
    @ForumIdTransform(type = CacheUtil.CACHE_FOLLOW_FIRSTCOMMENT, propName = "articleId", transType = CacheUtil.CACHE_FORUM_TRANS_COMMENT)
    private Integer commentCount;

    //点赞数
    @ForumIdTransform(type = CacheUtil.CACHE_FOLLOW_FIRSTCOMMENT, propName = "articleId", transType = CacheUtil.CACHE_FORUM_TRANS_LIKE)
    private Integer likeCount;

    //发布人名称
    @IdTransform(type= CacheUtil.CACHE_ACCOUNT_TYPE,propName = "publisher")
    private String publisherName;

    //审核人名称
    @IdTransform(type= CacheUtil.CACHE_ACCOUNT_TYPE,propName = "auditor")
    private String auditName;

    //审核状态
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "articleStatus")
    private String articleStatusName;

    //发布开始时间
    private String publishStartTime;

    //发布结束时间
    private String publishEndTime;

    //审核开始时间
    private String auditStartTime;

    //审核结束时间
    private String auditEndTime;

    //帖子类型
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "articleType")
    private String articleTypeName;

    //帖子状态list，查询多种状态的帖子
    private List<Integer> articleStatusList;

    //点赞、收藏ID
    private Integer followId;
    //
    private List<Integer> articleIds;

    private ForumCacheVO forumCacheVO;

    private Integer followerId;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public List<ForumComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ForumComment> commentList) {
        this.commentList = commentList;
    }

    public List<ForumFollow> getFollowList() {
        return followList;
    }

    public void setFollowList(List<ForumFollow> followList) {
        this.followList = followList;
    }

    //论文标签列表<key:标签名, value:颜色>
    private List<Map<String, String>> articleLabelList;

    public List<Map<String, String>> getArticleLabelList() {
        return articleLabelList;
    }

    public void setArticleLabelList(List<Map<String, String>> articleLabelList) {
        this.articleLabelList = articleLabelList;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public ForumCacheVO getForumCacheVO() {
        return forumCacheVO;
    }

    public void setForumCacheVO(ForumCacheVO forumCacheVO) {
        this.forumCacheVO = forumCacheVO;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getArticleStatusName() {
        return articleStatusName;
    }

    public void setArticleStatusName(String articleStatusName) {
        this.articleStatusName = articleStatusName;
    }

    public String getPublishStartTime() {
        return publishStartTime;
    }

    public void setPublishStartTime(String publishStartTime) {
        this.publishStartTime = publishStartTime;
    }

    public String getPublishEndTime() {
        return publishEndTime;
    }

    public void setPublishEndTime(String publishEndTime) {
        this.publishEndTime = publishEndTime;
    }

    public String getAuditStartTime() {
        return auditStartTime;
    }

    public void setAuditStartTime(String auditStartTime) {
        this.auditStartTime = auditStartTime;
    }

    public String getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(String auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public List<Integer> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<Integer> articleIds) {
        this.articleIds = articleIds;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(String adminFlag) {
        this.adminFlag = adminFlag;
    }

    /**
     * 校验新增帖子的参数
     * @param articleVO 帖子VO对象
     * @return ResultMessage
     * @version <1> 2019/3/8 mason:Created.
     */
    public static ResultMessage checkAddArticleParam(ArticleVO articleVO){
        if (null == articleVO.getCreator() || StringUtils.isEmpty(articleVO.getCreatorName())){
            return ResultMessage.fail("创建人为空");
        }

        if (null == articleVO.getCreateTime()){
            return ResultMessage.fail("创建时间为空");
        }

        if (null == articleVO.getArticleType()){
            return ResultMessage.fail("帖子类型为空");
        }

        if (StringUtils.isEmpty(articleVO.getArticleTitle())){
            return ResultMessage.fail("帖子标题为空");
        }

        if (StringUtils.isEmpty(articleVO.getArticleContent())){
            return ResultMessage.fail("帖子内容为空");
        }
        return ResultMessage.success();
    }

    public List<Integer> getArticleStatusList() {
        return articleStatusList;
    }

    public void setArticleStatusList(List<Integer> articleStatusList) {
        this.articleStatusList = articleStatusList;
    }
}
