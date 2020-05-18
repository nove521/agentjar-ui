package com.cx.server.soa;

import com.cx.server.service.HotUpdateService;
import org.yx.annotation.Bean;
import org.yx.annotation.Inject;
import org.yx.annotation.http.Web;

/**
 * 代码热部署
 */
@Bean
public class HotUpdate {

    @Inject
    private HotUpdateService HotUpdateService;

    @Web("/hotupdate/updateClassByString")
    public String updateClassByString(String className, String javaCode) {
        return HotUpdateService.updateClassByString(className,javaCode);
    }
}
