package com.jh.forum.cache.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:id转name：
 *
 * @version <1> 2019-02-16 14:54 lijie: Created.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForumIdTransform {
    public String type();
    public String propName();
    public String transType();
}
