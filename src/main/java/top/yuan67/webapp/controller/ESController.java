package top.yuan67.webapp.controller;

import com.alibaba.fastjson2.JSON;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;
import top.yuan67.webapp.entity.Book;
import top.yuan67.webapp.vo.BookVO;
import top.yuan67.webapp.util.HTTPResponse;
import top.yuan67.webapp.service.ElasticsearchService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2022-12-14
 * @Desc: 描述信息
 **/
@RestController
@RequestMapping("/es")
public class ESController {
  public static final Logger log = LoggerFactory.getLogger(ESController.class);
  
  @Resource
  private ElasticsearchService elasticsearchService;
  
  @Resource
  private ElasticsearchRestTemplate template;
  
  /**
   * 输入一个值全文检索
   * @param name
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/searchAll")
  public SearchHits<Book> searchAll(String name, int pageNum, int pageSize) {
    BookVO book = new BookVO(name);
    Map<String, Object> map = JSON.parseObject(JSON.toJSONString(book), Map.class);
    
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    List<HighlightBuilder.Field> fieldList = new LinkedList<>();
    
    for (String key : map.keySet()) {
      if (!map.get(key).equals("")) {
        log.info(key);
        HighlightBuilder.Field field = new HighlightBuilder.Field(key)
            .preTags("<font color='red' size=4>").postTags("</font>");
        boolQuery.should(QueryBuilders.matchQuery(key, map.get(key)).analyzer("ik_max_word"));
        fieldList.add(field);
      }
    }
    
    log.info(boolQuery.toString());
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(boolQuery)
        .withHighlightFields(fieldList)
        .withSorts(SortBuilders.scoreSort())
        .withSorts(SortBuilders.fieldSort("id"))
        .withPageable(PageRequest.of(pageNum - 1, pageSize));
    
    IndexCoordinates index = IndexCoordinates.of("books");
    SearchHits<Book> result = template.search(queryBuilder.build(), Book.class, index);
    return result;
  }
  
  /**
   * 根据条件查询ES
   * ES中的key值好像不能驼峰命名，所以用下划线代替
   * 于是乎boovo的get set方法的方法名都是下划线代替驼峰
   * @param book
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/search")
  public SearchHits<Book> search(BookVO book, int pageNum, int pageSize) {
    
    Map<String, Object> map = JSON.parseObject(JSON.toJSONString(book), Map.class);
    
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    List<HighlightBuilder.Field> fieldList = new LinkedList<>();
    
    for (String key : map.keySet()) {
      if (!map.get(key).equals("")) {
        log.info(key);
        
        HighlightBuilder.Field field = new HighlightBuilder.Field(key)
            .preTags("<font color='red' size=4>").postTags("</font>");
        boolQuery.must(QueryBuilders.matchQuery(key, map.get(key))
            //ik_smart
            // ik_max_word
//            .analyzer("ik_max_word")
            .operator(Operator.AND));
        fieldList.add(field);
      }
    }
    log.info("\n{}", boolQuery);
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(boolQuery)
        .withHighlightFields(fieldList)
        .withSorts(SortBuilders.scoreSort())
        .withSorts(SortBuilders.fieldSort("id"))
        .withPageable(PageRequest.of(pageNum - 1, pageSize));
    // .withPageable(PageRequest.of(page.getPageNum(),page.getPageSize(), Sort.Direction.ASC,
    // "dateTime"));
    
    IndexCoordinates index = IndexCoordinates.of("books");
    SearchHits<Book> result = template.search(queryBuilder.build(), Book.class, index);
    return result;
  }
  
  /**
   * 批量写入es
   * @param bookList
   * @return
   */
  @PostMapping("/addBath")
  public HTTPResponse addBath(List<Book> bookList) {
    for (Book m : bookList) {
      m.setId(UUID.randomUUID().timestamp());
      m.setCreaterId(1l);
      m.setIp("127.0.0.1");
    }
    Iterable<Book> metadata = elasticsearchService.saveAll(bookList);
    return HTTPResponse.ok("添加成功", metadata);
  }
  
  /**
   * 单个写入ES
   * @param book
   * @return
   */
  @PostMapping("/add")
  public HTTPResponse add(Book book) {
    book.setId(UUID.randomUUID().timestamp());
    book.setCreaterId(1l);
    book.setIp("127.0.0.1");
    Book m = elasticsearchService.save(book);
    return HTTPResponse.ok("添加成功", m);
  }
  
  /**
   * 根据ID查询ES
   * @param id
   * @return
   */
  @GetMapping("/findById/{id}")
  public Optional<Book> findById(@PathVariable Long id){
    return elasticsearchService.findById(id);
  }
}