package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.yueshushu.learn.business.PriceImageBusiness;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-03-17
 */
@Slf4j
public class PriceImageBusinessTest extends BaseTest {
    @Resource
    private PriceImageBusiness priceImageBusiness;

    @Test
    public void saveImageTest() {
        priceImageBusiness.batchSavePriceImage(Collections.singletonList("001318"), false);
    }

    @Test
    public void pointImageTest() {
        priceImageBusiness.batchSavePriceImageByFullCode(Arrays.asList(
                "sh000001", "sh000300", "sz399001", "sz399006"
        ), false);
    }
}
