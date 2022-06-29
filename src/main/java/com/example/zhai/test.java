package com.example.zhai;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.example.zhai.dto.GuResp;
import com.example.zhai.dto.ZhaiResp;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class test {

    static String detailCodeUrl = "https://datacenter-web.eastmoney.com/api/data/v1/get?reportName=RPT_F10_CORETHEME_CONTENT&columns=MAINPOINT_CONTENT&filter=";

    public static void main(String[] args) throws HttpRequestException, UnsupportedEncodingException{
        String resp = HttpRequest.get("https://datacenter-web.eastmoney.com/api/data/v1/get?sortColumns=PUBLIC_START_DATE&sortTypes=-1&pageSize=1&pageNumber=1&reportName=RPT_BOND_CB_LIST&columns=ALL&quoteColumns=f2~01~CONVERT_STOCK_CODE~CONVERT_STOCK_PRICE%2Cf235~10~SECURITY_CODE~TRANSFER_PRICE%2Cf236~10~SECURITY_CODE~TRANSFER_VALUE%2Cf2~10~SECURITY_CODE~CURRENT_BOND_PRICE%2Cf237~10~SECURITY_CODE~TRANSFER_PREMIUM_RATIO%2Cf239~10~SECURITY_CODE~RESALE_TRIG_PRICE%2Cf240~10~SECURITY_CODE~REDEEM_TRIG_PRICE%2Cf23~01~CONVERT_STOCK_CODE~PBV_RATIO&quoteType=0&source=WEB&client=WEB").body();
        // System.out.println(resp);
        

        ZhaiResp zhaiResp = JSON.parseObject(resp, ZhaiResp.class);
        // System.out.println(zhaiResp);
        String queryCode = zhaiResp.getResult().getData()[0].getConvertStockCode();
        String queryCodeUrl = "(SECURITY_CODE=\""+queryCode+"\"";
        String responseGu = HttpRequest.get(detailCodeUrl+URLEncoder.encode(queryCodeUrl, "utf-8")+")").body();
        GuResp guResp = JSON.parseObject(responseGu, GuResp.class);
        System.out.println(guResp.getResult().getData()[0].toString());
        GuResp.Result.Datum[] guInfos = guResp.getResult().getData();
        for (GuResp.Result.Datum guItem : guInfos) {
        
            if (guItem.getKeyWord().equals("所属板块")){
                System.out.println(guItem.getMainpointContent());
            }
        }

    }
}

