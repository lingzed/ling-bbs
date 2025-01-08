package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.constant.Constant;
import com.ling.entity.dto.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.Article;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.entity.vo.Result;
import com.ling.enums.ArticleOrderEnum;
import com.ling.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @GetMapping
    @AccessControl
    public Result<PageBean<ArticleVo>> loadArticles(HttpSession session, String title,
                                                    @Validation(required = false) Integer boardId,
                                                    @Validation(required = false) Integer pBoardId,
                                                    @RequestParam(defaultValue = "0") @Validation(max = 2, required = false) Integer orderType,
                                                    @Validation(required = false, max = 2) Integer status,
                                                    @RequestParam(defaultValue = "1") @Validation(min = 1, required = false) Integer page) {
        ArticleQueryDto articleQueryDto = new ArticleQueryDto();
        articleQueryDto.setTitle(title);
        articleQueryDto.setBoardId(Objects.equals(boardId, 0) ? null : boardId); // 板块id为0时，表示查询所有专栏的文章
        articleQueryDto.setpBoardId(pBoardId);
        // 根据前端传来的orderType，获取对应的排序规则
        ArticleOrderEnum articleOrderEnum = ArticleOrderEnum.getArticleOrderEnum(orderType);
        articleQueryDto.setOrderBy(articleOrderEnum.getOrderBySql());
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        articleQueryDto.setUserId(userinfo == null ? null : userinfo.getUserId());
        articleQueryDto.setAdmin(userinfo != null && userinfo.getIsAdmin());
        articleQueryDto.setStatus(status);// 查询文章的场景中，状态单独作为条件基本没用上，但是我还是留着
        articleQueryDto.setPage(page);
        articleQueryDto.setPageSize(Constant.NUM_10);
        PageBean<ArticleVo> articleVoPageBean = articleService.findVoByCondition(articleQueryDto);
        return Result.success(articleVoPageBean);
    }

    @GetMapping("/{articleId}")
    @AccessControl
    public Result<ArticleVo> getArticleDetails(HttpSession session, @PathVariable @Validation String articleId) {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        ArticleVo articleVo = articleService.articleDetails(userInfo, articleId);
        return Result.success(articleVo);
    }
}
