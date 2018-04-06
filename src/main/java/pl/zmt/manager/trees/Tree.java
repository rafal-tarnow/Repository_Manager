package pl.zmt.manager.trees;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private String name = null;
    private String description = null;
    private List<Tree> children = new ArrayList<>();
    private Tree parent = null;
    private TreeType type;

    public Tree() {
    }

    public Tree(String name) {
        this.name = name;
    }

    public Tree(String name, String description, TreeType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Tree addChild(Tree child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }
}

