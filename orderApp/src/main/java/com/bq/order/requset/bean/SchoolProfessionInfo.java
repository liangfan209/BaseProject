package com.bq.order.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/7
 * 版权：
 */
public class SchoolProfessionInfo implements Serializable {
    private List<AgentInfo> agent_list;
    private String id;
    private String name;
    private String content;
    private String icons;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public List<AgentInfo> getAgent_list() {
        return agent_list;
    }

    public void setAgent_list(List<AgentInfo> agent_list) {
        this.agent_list = agent_list;
    }

}
