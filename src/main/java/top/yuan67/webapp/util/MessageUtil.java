package top.yuan67.webapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
  private static final Logger log = LoggerFactory.getLogger(MessageUtil.class);

	private static MessageSource messageSource;

	public MessageUtil(MessageSource messageSource) {
    MessageUtil.messageSource = messageSource;
	}

	/**
	 * 获取单个国际化翻译值
	 */
	public static String get(String msgKey) {
		try {
      return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
      log.warn("国际化异常，key未配置:{}", e.getMessage());
			return msgKey;
		}
	}
  
  
  public static String get(String msgKey, Object... args) {
    try {
			return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
    } catch (Exception e) {
//      log.warn("国际化异常，key未配置:{}", e.getMessage());
      return msgKey;
    }
  }

}