package com.ll.auth.global.rsdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public RsData(String resultCode, String msg){
        this(resultCode,msg,null);
    }

    @JsonIgnore
    public int getStatusCode() {
        return Integer.parseInt(this.resultCode.split("-")[0]);
    }
}
