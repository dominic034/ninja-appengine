/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.List;
import java.util.Map;

import models.Article;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import conf.OfyService;
import dao.ArticleDao;
import ninja.appengine.AppEngineEnvironment;

@Singleton
// Just a test to make sure @AppEngineEnvironment works.
// Usually @FilterWith(AppEngineFilter.class is much better.
@AppEngineEnvironment 
public class ApplicationController {

    @Inject
    ArticleDao articleDao;
    
    public ApplicationController() {

    }
    
    /**
     * Method to put initial data in the db...
     * @return
     */
    public Result setup() {
        
        OfyService.setup();
        
        return Results.ok();
        
    }

    public Result index() {

        Article frontPost = articleDao.getFirstArticleForFrontPage();

        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();

        Map<String, Object> map = Maps.newHashMap();
        map.put("frontArticle", frontPost);
        map.put("olderArticles", olderPosts);

        return Results
                .html()
                .render("frontArticle", frontPost)
                .render("olderArticles", olderPosts);

    }

}
