package pl.zmt.manager;

import java.util.ArrayList;
import java.util.List;


public class Node {

    private String name = null;
    private String path = null;
    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private Integer depth = null;
    private NodeType type;

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Node addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
