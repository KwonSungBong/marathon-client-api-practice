package com.example.demo.component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import mesosphere.marathon.client.Marathon;
import mesosphere.marathon.client.model.v2.App;
import mesosphere.marathon.client.model.v2.GetAppResponse;
import mesosphere.marathon.client.model.v2.Task;
import mesosphere.marathon.client.utils.MarathonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by jcyang on 12/31/15.
 */
@Slf4j
@Component
public class MarathonComponent {

    @Value("${marathon.endpoint}")
    private String endpoint;

    @Value("${marathon.username}")
    private String marathonUserName;

    @Value("${marathon.password}")
    private String marathonPassword;

    private Marathon marathon;
    private Map<String, String> urls = Maps.newHashMap();

    @PostConstruct
    public void init() {
        marathon = MarathonAuthClient.getInstance(endpoint, marathonUserName, marathonPassword);
    }

    public String getUrl(String appName) {
        String url = urls.get(appName);
        if (!Strings.isNullOrEmpty(url)) return url;
        return searchUrl(appName);
    }

    private String searchUrl(String appName) {
        String url = null;
        StringBuilder sb = new StringBuilder("http://");
        try {
            GetAppResponse appResponse = marathon.getApp(appName);
            App app = appResponse.getApp();
            for (Task task : app.getTasks()) {
                if (!task.getHost().isEmpty() && !task.getPorts().isEmpty()) {
                    url = sb.append(task.getHost())
                            .append(":")
                            .append(Lists.newArrayList(task.getPorts()).get(0)).toString();
                    break;
                }
            }
        } catch (MarathonException e) {
            return null;
        } finally {
            if (url != null) {
                urls.put(appName, url);
            }
        }
        return url;
    }

}
