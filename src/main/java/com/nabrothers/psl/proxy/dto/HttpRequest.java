package com.nabrothers.psl.proxy.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class HttpRequest {
    private String url;
    private JSONObject body;
    private JSONObject header;
}
