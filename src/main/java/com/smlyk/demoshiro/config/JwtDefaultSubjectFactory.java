package com.smlyk.demoshiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 由于需要实现无状态的web，所以使用不到Shiro的Session功能
 * @Author: always
 * @Date: 2020/11/6 4:39 下午
 */
public class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建 session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }

}
