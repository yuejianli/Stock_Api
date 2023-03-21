package top.yueshushu.learn.mode.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yuejianli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private String id;
    private String pid;
    private String label;
    private List<TreeNode> children;
}
