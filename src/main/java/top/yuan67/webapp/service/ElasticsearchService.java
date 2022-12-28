package top.yuan67.webapp.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.yuan67.webapp.entity.Book;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2022-12-29
 * @Desc: 描述信息
 **/
public interface ElasticsearchService extends ElasticsearchRepository<Book, Long> {
}
