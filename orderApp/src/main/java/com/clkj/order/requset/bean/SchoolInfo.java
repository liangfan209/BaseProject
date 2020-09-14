package com.clkj.order.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class SchoolInfo implements Serializable {
    private String content;
    private String id;
    private String icons;
    private String name;
    private List<ProfessionList> production_list;
    private List<AgentInfo> agent_list;

    public List<AgentInfo> getAgent_list() {
        return agent_list;
    }

    public void setAgent_list(List<AgentInfo> agent_list) {
        this.agent_list = agent_list;
    }

    public List<ProfessionList> getProduction_list() {
        return production_list;
    }

    public void setProduction_list(List<ProfessionList> production_list) {
        this.production_list = production_list;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
