package top.yueshushu.learn.mode.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName:MenuVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 13:45
 * @Version 1.0
 **/
@Data
@ApiModel("菜单展示Vo")
public class MenuVo implements Serializable {
   @ApiModelProperty("菜单id编号")
    private Integer id;
    @ApiModelProperty("菜单的名称")
    private String name;
    @ApiModelProperty("上级菜单的id")
    private Integer pid;
    @ApiModelProperty("展示的位置信息")
    private String showIndex;
    @ApiModelProperty("菜单的访问地址")
    private String url;
    @ApiModelProperty("展示类型 1为web端 2为小程序端")
    private Integer showType;
    @ApiModelProperty("是否删除:1为正常0为删除")
    private Integer flag;
}
