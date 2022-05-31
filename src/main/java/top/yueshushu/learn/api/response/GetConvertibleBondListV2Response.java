package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 查询可申购新债列表响应
 */
@Data
public class GetConvertibleBondListV2Response {

    private int ExStatus;
    private boolean ExIsToday;
    private String BONDCODE;
    private String BONDNAME;
    private String BUYREDEMCODE;
    private String BUYREDEMNAME;
    private String BUYREDEMPRIC;
    private String CREDITRATING;
    private String Cybbz;
    private String FLOORBUYVOL;
    private String ISSUESDATE;
    private String ISSUEVOL;
    private String LIMITBUYVOL;
    private String LWRANDATE;
    private String Market;
    private String PARVALUE;
    private String PLACINGCODE;
    private String PLACINGNAME;
    private String PLACINGRIGHT;
    private String PURCHASEDATE;
    private String RATING;
    private String SFZCZ;
    private String SUBCODE;
    private String SUBNAME;
    private String SWAPPRICE;
    private String SWAPSCODE;
    private String SWAPSNAME;
    private String SWAPVALUE;
    private String BONDTYPECODE;
}
