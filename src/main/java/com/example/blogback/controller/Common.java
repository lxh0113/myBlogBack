package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.*;
import com.example.blogback.domain.*;
import com.example.blogback.domain.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
public class Common {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private BrowseDao browseDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private LoveDao loveDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private FansDao fansDao;

    @Autowired
    private ArticleLabelDao articleLabelDao;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private MessageDao messageDao;


    private static  Common common;

    @PostConstruct
    public void init(){
        common=this;

        common.userDao=this.userDao;
        common.articleDao=this.articleDao;
        common.browseDao=this.browseDao;
        common.labelDao=this.labelDao;
        common.loveDao=this.loveDao;
        common.collectDao=this.collectDao;
        common.columnDao=this.columnDao;
        common.fansDao=this.fansDao;
        common.articleLabelDao=this.articleLabelDao;
        common.collectionDao=this.collectionDao;
        common.messageDao=this.messageDao;

    }


    public static User getUserById(Integer id){
        User user=common.userDao.selectById(id);
        user.setPassword(null);

        return user;
    }

    public static Article getArticleById(Integer id){
        return common.articleDao.selectById(id);
    }

    public static ArticleInfo getArticleEditById(Integer id){

        ArticleInfo articleInfo=new ArticleInfo();

        Article article=common.articleDao.selectById(id);

        if(article==null)
        {
            return null;
        }

        articleInfo.setArticle(article);
//

        //        获取标签
        QueryWrapper<ArticleLabel> articleLabelQueryWrapper=new QueryWrapper<>();
        articleLabelQueryWrapper.eq("article_id",id);
        List<ArticleLabel> articleLabels = common.articleLabelDao.selectList(articleLabelQueryWrapper);

        ArrayList<Label> labels=new ArrayList<>();
        for (ArticleLabel articleLabel : articleLabels) {
            Label label = common.labelDao.selectById(articleLabel.getLabelId());
            labels.add((label));
        }

        articleInfo.setLabels(labels);

        return articleInfo;
    }

    public static UserInfo getUserInfoById(Integer id){

        UserInfo userInfo=new UserInfo();

//        文章数量
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("user_id",id)
                .eq("status",2);

        Integer articles = common.articleDao.selectCount(articleQueryWrapper);

        userInfo.setArticles(articles);

//        关注
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("fans_id",id);

        Integer follows = common.fansDao.selectCount(fansQueryWrapper);
        userInfo.setFollow(follows);

//        粉丝
        fansQueryWrapper.clear();
        fansQueryWrapper.eq(("user_id"),id);

        Integer fans=common.fansDao.selectCount(fansQueryWrapper);
        userInfo.setFans(fans);

        return userInfo;
    }

    public static UserDetails getUserDetails(Integer myId,Integer userId){
        User user=common.userDao.selectById(userId);

        UserInfo userInfo=getUserInfoById(userId);

        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("user_id",userId)
                .eq("fans_id",myId);

        Fans fans = common.fansDao.selectOne(fansQueryWrapper);

        if(fans==null) userInfo.setIsFollow(false);
        else userInfo.setIsFollow(true);

        UserDetails userDetails=new UserDetails(user,userInfo);

        return userDetails;
    }

    public static ArticleInfo getArticleInfo(Integer id,Integer userId){
        Article article=common.articleDao.selectById(id);

        if(article==null)
        {
            return null;
        }

        ArticleInfo articleInfo=new ArticleInfo();
        articleInfo.setArticle(article);

//        浏览数
        QueryWrapper<Browse> browseQueryWrapper=new QueryWrapper<Browse>();
        browseQueryWrapper
                .eq("article_id",id);

        Integer browse = common.browseDao.selectCount(browseQueryWrapper);
        articleInfo.setBrowse(browse);


//      点赞数量
        QueryWrapper<Love> loveQueryWrapper=new QueryWrapper<>();
        loveQueryWrapper
                .eq("article_id",article.getId());

        Integer love = common.loveDao.selectCount(loveQueryWrapper);
        articleInfo.setLove(love);

//      收藏数量
        QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
        collectQueryWrapper
                .eq("article_id",article.getId());

        Integer collect = common.collectDao.selectCount(collectQueryWrapper);
        articleInfo.setCollect(collect);

//      评论数量
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        commentQueryWrapper
                .eq("article_id",article.getId());

        Integer comments = common.commentDao.selectCount(commentQueryWrapper);
        articleInfo.setComments(comments);

        if(article.getColumnId()!=null)
        {
            Column column = common.columnDao.selectById(article.getColumnId());
            articleInfo.setColumn(column);
        }

//        是否点赞
        loveQueryWrapper.clear();
        loveQueryWrapper.eq("user_id",userId)
                .eq("article_id",article.getId());

        Love love1 = common.loveDao.selectOne(loveQueryWrapper);
        if(love1!=null)
        {
            articleInfo.setUserIsLove(true);
        }
        else articleInfo.setUserIsLove(false);

//        是否评论
        commentQueryWrapper.clear();
        commentQueryWrapper.eq("article_id",article.getId())
                .eq("user_id",userId);

        List<Comment> comments1 = common.commentDao.selectList(commentQueryWrapper);
        if(!comments1.isEmpty()) articleInfo.setUserIsComment(true);
        else articleInfo.setUserIsComment(false);

//        是否收藏
        collectQueryWrapper.clear();
        collectQueryWrapper.eq("user_id",userId)
                .eq("article_id",article.getId());

        Collect collect1 = common.collectDao.selectOne(collectQueryWrapper);
        if(collect1!=null) articleInfo.setUserIsCollect(true);
        else articleInfo.setUserIsCollect(false);

//        获取标签
        QueryWrapper<ArticleLabel> articleLabelQueryWrapper=new QueryWrapper<>();
        articleLabelQueryWrapper.eq("article_id",id);
        List<ArticleLabel> articleLabels = common.articleLabelDao.selectList(articleLabelQueryWrapper);

        ArrayList<Label> labels=new ArrayList<>();
        for (ArticleLabel articleLabel : articleLabels) {
            Label label = common.labelDao.selectById(articleLabel.getLabelId());
            labels.add((label));
        }

        articleInfo.setLabels(labels);

//        获取这个人
        User user = Common.getUserById(article.getUserId());

        articleInfo.setUser(user);

//        是否关注
//        common.fansDao
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("user_id",article.getUserId())
                .eq("fans_id",userId);

        Fans fans = common.fansDao.selectOne(fansQueryWrapper);

        if(fans==null) articleInfo.setUserIsFollow(false);
        else articleInfo.setUserIsFollow(true);

        return articleInfo;

    }

    public static ArrayList<CommentInfo> getAllComments(Integer articleId){
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
//        一级评论
        commentQueryWrapper.eq("article_id",articleId)
                .isNull("parent_id");

        List<Comment> comments = common.commentDao.selectList(commentQueryWrapper);
        System.out.println(comments.size());

        ArrayList<CommentInfo> commentInfos=new ArrayList<>();

        for (Comment comment : comments) {

            commentQueryWrapper.clear();
            commentQueryWrapper.eq("parent_id",comment.getId());
            User user=Common.getUserById(comment.getUserId());

            List<CommentInfo> sonCommentInfo=new ArrayList<>();

            List<Comment> sonComment = common.commentDao.selectList(commentQueryWrapper);
            for (Comment son : sonComment) {

                User sonUser=Common.getUserById(son.getUserId());

                User receiveUser=Common.getUserById(son.getReceiverId());

                sonCommentInfo.add(new CommentInfo(son,sonUser,receiveUser.getUsername(),null));
            }


            commentInfos.add(new CommentInfo(comment,user,null,sonCommentInfo));
        }

        return commentInfos;
    }

    public static ArrayList<ArticleInfo> getAllArticle(Integer userId){
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("status",2);

        List<Article> articles = common.articleDao.selectList(articleQueryWrapper);

        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();
        for (Article article : articles) {
            ArticleInfo articleInfo=getArticleInfo(article.getId(),userId);
            articleInfos.add(articleInfo);
        }

        return articleInfos;
    }

    public static ArrayList<ArticleInfo> getAllFollowArticles(Integer userId){
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("fans_id",userId);

        List<Fans> fans = common.fansDao.selectList(fansQueryWrapper);

        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        for (Fans fan : fans) {
            articleQueryWrapper.clear();
            articleQueryWrapper.eq("user_id",fan.getUserId());

            List<Article> articles = common.articleDao.selectList(articleQueryWrapper);

            for (Article article : articles) {
                articleInfos.add(getArticleInfo(article.getId(),userId));
            }
        }

        return articleInfos;

    }


    public static ArrayList<ArticleInfo> getHotArticles(Integer userId){

        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();

        QueryWrapper<Browse> browseQueryWrapper=new QueryWrapper<>();
        browseQueryWrapper.select("article_id, count(*) as view_count")
                .groupBy("article_id")
                .orderByDesc("view_count")
                .last("LIMIT 5"); // 限制结果为前5条

        List<Browse> browses = common.browseDao.selectList(browseQueryWrapper);

        for (Browse brows : browses) {
            articleInfos.add(getArticleInfo(brows.getArticleId(),userId));
        }

        return articleInfos;

    }

    public static ArrayList<ArticleCollect> getArticleCollect(Integer userId,Integer articleId){
        QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
        collectQueryWrapper.eq("article_id",articleId)
                .eq("user_id",userId);

        Collect collect = common.collectDao.selectOne(collectQueryWrapper);

//        选出这个人的所有collection

        QueryWrapper<Collection> collectionQueryWrapper=new QueryWrapper<>();
        collectionQueryWrapper.eq("user_id",userId);

        List<Collection> collections = common.collectionDao.selectList(collectionQueryWrapper);

        ArrayList<ArticleCollect> articleCollects=new ArrayList<>();
        for (Collection c : collections) {

            if(collect!=null && Objects.equals(c.getId(), collect.getCollectionId())){
                articleCollects.add(new ArticleCollect(c,true));
            }
            else articleCollects.add(new ArticleCollect(c,false));
        }

        return articleCollects;
    }

    public static ArrayList<CollectionContent> getContentCollections(Integer userId){
//        先找出收藏夹
        QueryWrapper<Collection> collectionQueryWrapper=new QueryWrapper<>();
        collectionQueryWrapper.eq("user_id",userId);

        List<Collection> collections = common.collectionDao.selectList(collectionQueryWrapper);
        ArrayList<CollectionContent> collectionContents=new ArrayList<>();

        for (Collection collection : collections) {
            QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
            collectQueryWrapper.eq("collection_id",collection.getId());

            List<Collect> collects = common.collectDao.selectList(collectQueryWrapper);

            collectionContents.add(new CollectionContent(collection,collects));
        }

        return collectionContents;
    }

    public static ArrayList<SearchColumn> searchColumn(String searchInput){
        ArrayList<SearchColumn> searchColumns=new ArrayList<>();

        QueryWrapper<Column> columnQueryWrapper=new QueryWrapper<>();
        columnQueryWrapper.like("name",searchInput);

        List<Column> columns = common.columnDao.selectList(columnQueryWrapper);

        for (Column column : columns) {
            QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
            articleQueryWrapper.eq("user_id",column.getUserId());
            searchColumns.add(new SearchColumn(common.userDao.selectById(column.getUserId()),column,common.articleDao.selectList(articleQueryWrapper)));
        }

        return searchColumns;

    }

    public static ArrayList<BrowseGroup> getBrowseHistory(Integer userId){
        QueryWrapper<Browse> browseQueryWrapper=new QueryWrapper<>();
        browseQueryWrapper.eq("user_id",userId)
                .groupBy("date","article_id","user_id","id")
                .orderBy(true,false,"date").last("limit 10");

        List<Browse> browses = common.browseDao.selectList(browseQueryWrapper);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        ArrayList<BrowseGroup> browseGroups=new ArrayList<>();
        for (Browse browse : browses) {

            if(browseGroups.isEmpty()){
                ArrayList<BrowseHistory> browseHistories=new ArrayList<>();
                Article article=common.articleDao.selectById(browse.getArticleId());
                browseHistories.add(new BrowseHistory(browse,article,common.userDao.selectById(article.getUserId())));

                browseGroups.add(new BrowseGroup(dateFormat.format(browse.getDate()),browseHistories));
            }
            else if(browseGroups.get(browseGroups.size()-1).getDate().equals(dateFormat.format(browse.getDate()))){
                Article article=common.articleDao.selectById(browse.getArticleId());
                browseGroups.get(browseGroups.size()-1).getBrowseHistory().add(new BrowseHistory(browse,common.articleDao.selectById(browse.getArticleId()),common.userDao.selectById(article.getUserId())));
            }else {
                ArrayList<BrowseHistory> browseHistories=new ArrayList<>();
                Article article=common.articleDao.selectById(browse.getArticleId());
                browseHistories.add(new BrowseHistory(browse,article,common.userDao.selectById(article.getUserId())));

                browseGroups.add(new BrowseGroup(dateFormat.format(browse.getDate()),browseHistories));
            }
        }

        return browseGroups;
    }
    
    public static ArrayList<ArticleInfo> adminGetArticleInfo(List<Article> articles){
        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();
        for (Article article : articles) {
            ArticleInfo articleInfo=new ArticleInfo();
            articleInfo.setArticle(article);
            articleInfo.setUser(common.userDao.selectById(article.getUserId()));

            articleInfos.add(articleInfo);
        }

        return articleInfos;
    }


    public static ArrayList<CommentInfo> adminGetComments(Integer articleId,String word){
        ArrayList<CommentInfo> commentInfos=new ArrayList<>();

        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        commentQueryWrapper.eq(articleId!=null&&articleId!=0,"article_id",articleId)
                .like(word!=null&& !word.isEmpty(),"content",word)
                .last("limit 10");

        List<Comment> comments = common.commentDao.selectList(commentQueryWrapper);

        for (Comment comment : comments) {
            commentInfos.add(new CommentInfo(comment,common.userDao.selectById(comment.getUserId()),null,null));
        }

        return commentInfos;
    }

    public static ArrayList<UserDetails> getFans(Integer userId){
        ArrayList<UserDetails> userDetails=new ArrayList<>();

         QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
         fansQueryWrapper.eq("user_id",userId);

        List<Fans> fans = common.fansDao.selectList(fansQueryWrapper);
        for (Fans fan : fans) {
            userDetails.add(new UserDetails(Common.getUserById(fan.getFansId()),Common.getUserInfoById(fan.getFansId())));
        }

        return userDetails;
    }

    public static ArrayList<MessageCommon> getLikes(Integer userId){
        ArrayList<MessageCommon> messageCommons=new ArrayList<>();
        List<Integer> usersWhoLikedArticle = common.messageDao.findUsersWhoLikedArticle(userId);

        for (Integer i : usersWhoLikedArticle) {
            Love love = common.loveDao.selectById(i);
            messageCommons.add(new MessageCommon(Common.getUserById(love.getUserId()),"点赞",null,common.articleDao.selectById(love.getArticleId())));
        }

        return messageCommons;
    }

    public static ArrayList<MessageCommon> getCollects(Integer userId){
        ArrayList<MessageCommon> messageCommons=new ArrayList<>();
        List<Integer> usersWhoCollectedArticle = common.messageDao.findUsersWhoCollectedArticle(userId);

        for (Integer i : usersWhoCollectedArticle) {
            Collect collect = common.collectDao.selectById(i);
            messageCommons.add(new MessageCommon(Common.getUserById(collect.getUserId()),"收藏",null,common.articleDao.selectById(collect.getArticleId())));
        }

        return messageCommons;
    }

    public static ArrayList<MessageCommon> getComments(Integer userId){
        ArrayList<MessageCommon> messageCommons=new ArrayList<>();
        List<Integer> article = common.messageDao.findUsersWhoCommentedArticle(userId);

        for (Integer i : article) {
            Comment comment = common.commentDao.selectById(i);
            messageCommons.add(new MessageCommon(Common.getUserById(comment.getUserId()),"评论",comment,common.articleDao.selectById(comment.getArticleId())));
        }

        return messageCommons;
    }

    public static ArrayList<MessageFriend> getMessages(Integer userId){

        ArrayList<MessageFriend> messageFriends=new ArrayList<>();

        List<Integer> allMessageFriends = common.messageDao.findAllMessageFriends(userId);

        for (Integer i : allMessageFriends) {
            System.out.println(i+"  "+userId);
            QueryWrapper<Message> messageQueryWrapper=new QueryWrapper<>();
            messageQueryWrapper.eq("sender_id",userId)
                    .eq("receiver_id",i)
                    .or()
                    .eq("sender_id",i)
                    .eq("receiver_id",userId)
                    .orderBy(true,true,"date");


            messageFriends.add(new MessageFriend(Common.getUserById(i),common.messageDao.selectList(messageQueryWrapper)));
        }

        return messageFriends;
    }

}
