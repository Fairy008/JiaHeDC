package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.constant.ConstantUtil;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.Enum.ArticleTypeEnum;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.mapping.IForumArticleMapper;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.forum.bbs.service.IForumLabelService;
import com.jh.forum.bbs.util.CommonUtils;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.cache.base.ForumIdTransformUtils;
import com.jh.forum.cache.service.ICacheService;
import com.jh.forum.cache.vo.ForumCacheVO;
import com.jh.util.CollectionUtil;
import com.jh.util.FileTransUtils;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jh.util.ceph.CephUtils.getCephUrl;
import static it.geosolutions.geoserver.rest.encoder.metadatalink.ResourceMetadataLinkInfo.content;
import static java.awt.SystemColor.text;

/**
 * @Description:    论坛文章相关方法
 * @Author:         mason
 * @CreateDate:     2019/3/4 15:53
 * @Version:        1.0
 */
@Service
@Transactional
public class ForumArticleServiceImpl extends BaseServiceImpl<ArticleVO,ArticleVO,Integer> implements IForumArticleService {

    @Autowired
    private IForumArticleMapper forumArticleMapper;

    @Autowired
    private IForumCommentService forumCommentService;

    @Autowired
    private IForumFollowService forumFollowService;

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IForumLabelService forumLabelService;

    private static Logger log= LoggerFactory.getLogger(ForumArticleServiceImpl.class);

    /**
     * @Description:    继承基础接口的增删改查功能
     * @Author:         mason
     * @CreateDate:     2019/3/4 16:05
     * @Version:        1.0
     */
    @Override
    protected IBaseMapper<ArticleVO, ArticleVO, Integer> getDao() {
        return forumArticleMapper;
    }


    @Override
    public PageInfo<ArticleVO> getListByCombination(ArticleVO forumArticleVo) {
        PageHelper.startPage(forumArticleVo.getPage(), forumArticleVo.getRows());
        List<ArticleVO> list = forumArticleMapper.getListByCombination(forumArticleVo);
        list = setImageUrl(list);
        ForumIdTransformUtils.idTransForList(list);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ArticleVO>(list);
    }

    @Override
    public ResultMessage getHotArticleList(Integer articleType, Integer creator) {
        List<ArticleVO> hotForumArticleVoList = forumArticleMapper.getHotArticleList(articleType, creator);
        if(CollectionUtils.isNotEmpty(hotForumArticleVoList) && hotForumArticleVoList.size()> ForumConstant.CHANGE_BATCHES_COUNT){
            hotForumArticleVoList = CommonUtils.getSubStringByRadom(hotForumArticleVoList,ForumConstant.CHANGE_BATCHES_COUNT);
        }
        return ResultMessage.success(hotForumArticleVoList);
    }



    @Override
    public ResultMessage getNewArticleList(Integer articleType, Integer creator) {
        List<ArticleVO> newForumArticleVoList = forumArticleMapper.getNewArticleList(articleType, creator);
        if(CollectionUtils.isNotEmpty(newForumArticleVoList) && newForumArticleVoList.size()>ForumConstant.CHANGE_BATCHES_COUNT){
            newForumArticleVoList = CommonUtils.getSubStringByRadom(newForumArticleVoList, ForumConstant.CHANGE_BATCHES_COUNT);
        }
        return ResultMessage.success(newForumArticleVoList);
    }

    /**
     * 查询报告/调研/问答/数据分享详情
     * @param articleId 报告/调研/问答/数据分享id
     * @param currentAccountId 当前登录人
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage findArticleInfo(Integer articleId, Integer currentAccountId) {
        //查询报告/调研/问答/数据分享详情,评论涉及分页，需要在页面上单独调用
        ArticleVO article = forumArticleMapper.getById(articleId);

        if (null == article) return ResultMessage.fail();

        if(currentAccountId != null){ //查询当前登录人是否关注了该帖子的作者

            Integer creator = article.getCreator(); //帖子作者

            //查询
            ResultMessage followResult = forumFollowService.findFolowByCurrentAccountId(creator, currentAccountId);

            if(followResult.isFlag() && followResult.getData() != null){
                ForumFollow follow = (ForumFollow)followResult.getData();
                article.setFollowId(follow.getFollowId());
            }
        }

        //从缓存中获取该帖子的评论数和点赞数
        ResultMessage result = cacheService.queryFollowCommentNumByArticleId(articleId);
        if (result.isFlag()){
            ForumCacheVO forumCacheVO = (ForumCacheVO) result.getData();
            Integer followNum = forumCacheVO.getFollowNum();//点赞数
            Integer commentNum = forumCacheVO.getCommentNum();//评论数
            article.setCommentCount(commentNum);
            article.setLikeCount(followNum);
            article = getWrapArticleVO(article);
        }
        return ResultMessage.success(article);
    }

    @Override
    public ResultMessage findArticleFollows(Integer articleId, Integer creator) {

        if(articleId == null || creator == null){ //特殊处理：不论有没有值，就当默认情况即可
            return ResultMessage.success(null,null);
        }

        List<Integer> follows = forumArticleMapper.findArticleFollows(articleId, creator);
        return ResultMessage.success(follows);
    }

    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage deleteArticle(ArticleVO articleVO) {
        try {
            //1、删除该帖子缓存中的点赞数、关注数、一级和二级评论数
            cacheService.queryFollowCommentNumByArticleId(articleVO.getArticleId());
            //2、删除报告/调研/问答/数据分享
            forumArticleMapper.delete(articleVO);
            //3、删除相关的评论
            ForumComment forumComment = new ForumComment();
            forumComment.setArticleId(articleVO.getArticleId());
            forumCommentService.deleteCommentByArticleId(articleVO.getArticleId());
            //4、删除相关的关注或点赞
            ForumFollow forumFollow = new ForumFollow()        ;
            forumFollow.setArticleId(articleVO.getArticleId());
            forumFollowService.delete(forumFollow);
            return ResultMessage.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }
    /**
     * 包装返回首页的结果集
     * @param articleVO
     * @return
     */
    private ArticleVO getWrapArticleVO(ArticleVO articleVO){
        if(articleVO == null){
            return articleVO;
        }
        //标签转换
        List<String> articleLabeList = CollectionUtil.strToList(articleVO.getArticleLabel(), ConstantUtil.COMMA_SEPARATOR);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (String name : articleLabeList) {
            Map<String, String> map = new HashMap<String, String>();
            List<ForumLabel> forumLabelList = forumLabelService.findLabelByName(name);
            String color = null;
            if (forumLabelList != null && forumLabelList.size() > 0 && forumLabelList.get(0) != null) {
                color = forumLabelList.get(0).getLabelColor();
            }
            map.put(name, color);
            listMap.add(map);
        }
        articleVO.setArticleLabelList(listMap);
        //获取点赞数和评论数
        return articleVO;
    }

    @Override
    public PageInfo<ArticleVO> findByPageCms(ArticleVO forumArticleVo) {
        PageHelper.startPage(forumArticleVo.getPage(), forumArticleVo.getRows());
        List<ArticleVO> list = forumArticleMapper.findByPageCms(forumArticleVo);
        ForumIdTransformUtils.idTransForList(list);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ArticleVO>(list);
    }

    /**
     * 报告：html转PDF并上传
     * @return pdf存储地址
     */
    private String html2pdf (Integer articleId){
        //系统前缀
        String reportStorage = CephUtils.getAbsolutePath("").replace("\\",File.separator);
        ArticleVO article = forumArticleMapper.getById(articleId);
        String htmlHead="<!DOCTYPE html><html><head><meta charset='utf-8'>" ;
        String htmStyle="<style>p{font-family: '宋体';font-size: 14px !important;}h1{text-align:center;}</style></head><body>";
        String content =article.getArticleContent();
        //解析本地图片
        if(content.contains(ForumConstant.FORUN_REPORT_DOWN_URL)){
            content=content.replace(ForumConstant.FORUN_REPORT_DOWN_URL,reportStorage+ForumConstant.FORUN_REPORT_URL);
        }
        String htmlFoot="</body></html>";
        String htmlTitle = "<h1>"+article.getArticleTitle()+"</h1>";
        String html=htmlHead+htmStyle+htmlTitle+content+htmlFoot;

        //报告目录
        String reportUrl=CephUtils.getCephUrl("FORUM_REPORT_STORAGE").replace("\\",File.separator)+File.separator;
        //不存在则创建
        File f = new File(reportStorage+reportUrl);
        if (!f.exists()) {
            f.mkdirs();
        }
        //解决%不能生成pdf的问题
        String articleTitle = article.getArticleTitle();
        //反斜杠会当成路径，替换成横杠
        articleTitle = articleTitle.replaceAll("/","-");
        articleTitle = articleTitle.replaceAll("\\?","");
        articleTitle = articleTitle.replaceAll("\\+","");
        articleTitle = articleTitle.replaceAll("#","");
        articleTitle = articleTitle.replaceAll("&","");
        articleTitle = articleTitle.replaceAll(" ","");
        //文件名：报告类型+报告主键
        String pdfUrl=reportUrl+articleTitle + "_" + articleId + ".pdf";//pdf存储数据库路径
        String reportName_html=reportStorage +reportUrl+articleTitle + "_" + articleId + ".html";//html文件
        String reportName_pdf=reportStorage +reportUrl+articleTitle + "_" + articleId + ".pdf";//pdf文件
        log.info("报告上传地址为：html:{},pdf:{}",new String []{reportName_html,reportName_pdf});
        //如果文件存在则删除
        File html_file = new File(reportName_html);
        if (html_file.exists()) {
            html_file.delete();
        }
        File pdf_file = new File(reportName_pdf);
        if (pdf_file.exists()) {
            pdf_file.delete();
        }

        try {
            FileWriter fw = new FileWriter(reportName_html, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(html);
            bw.close();
            fw.close();
        }catch(Exception e){

        }
        FileTransUtils.html2pdf(reportName_html,reportName_pdf);
        //修改报告路径
        ArticleVO vo =new ArticleVO();
        vo.setArticleId(articleId);
        vo.setReportUrl(pdfUrl);
        update(vo);
        return pdfUrl;
    }


    @Override
    public PageInfo<ArticleVO> getListByFollower(ArticleVO forumArticleVo) {
        PageHelper.startPage(forumArticleVo.getPage(), forumArticleVo.getRows());
        List<ArticleVO> list = forumArticleMapper.getListByFollower(forumArticleVo);
        list = setImageUrl(list);
        ForumIdTransformUtils.idTransForList(list);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ArticleVO>(list);
    }

    /**
     * 新增报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage saveArticle(ArticleVO articleVO){
        //保存
        forumArticleMapper.save(articleVO);
        //如果为报告，html转pdf
        if(ArticleTypeEnum.ARTICLE_TYPE_PAPER.getId().equals(articleVO.getArticleType())) {
            html2pdf(articleVO.getArticleId());
        }
        return ResultMessage.success();
    }

    /**
     * 编辑报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage updateArticle(ArticleVO articleVO){
        if (articleVO.getArticleId() == null ){ return ResultMessage.fail("帖子id为空");}
        forumArticleMapper.update(articleVO);
        //如果为报告，html转pdf
        if(ArticleTypeEnum.ARTICLE_TYPE_PAPER.getId().equals(articleVO.getArticleType())){
            html2pdf(articleVO.getArticleId());
        }
        return ResultMessage.success();
    }

    /**
     * 根据articleId查询是否是自己的帖子
     * @param articleId accountId
     * @return ResultMessage
     * @version <1> 2019/3/20 13:32 zhangshen:Created.
     */
    @Override
    public ResultMessage findArticleInfoByAccountId(Integer articleId, Integer accountId) {
        ArticleVO article = forumArticleMapper.findArticleInfoByAccountId(articleId, accountId);
        return ResultMessage.success(article);
    }

    /**
     * 将查询到的文章list，设置摘要图片
     * @param
     * @return
     * @version <1> 2019/3/25 mason:Created.
     */
    public List<ArticleVO> setImageUrl(List<ArticleVO> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(ArticleVO articleVO : list){
                articleVO = this.getWrapArticleVO(articleVO);

                if(StringUtils.isNotBlank(articleVO.getArticleContent()) && ArticleTypeEnum.ARTICLE_TYPE_PAPER.getId().intValue() == articleVO.getArticleType() ){
                    int startPos = articleVO.getArticleContent().indexOf("<img");

                    System.out.println(articleVO.getArticleContent());
                    if(startPos > -1){
                        String _tmpContent = articleVO.getArticleContent().substring(startPos);
                        String imgurl = _tmpContent.substring(_tmpContent.indexOf("src=")+5,_tmpContent.indexOf("/>"));

                        String[] imgArr = imgurl.split("\"");
                        articleVO.setImageUrl(imgArr[0]);
                    }

                    articleVO.setArticleContent(null);
                }

            }
        }
        return list;
    }

}
