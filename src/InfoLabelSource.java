//package pl.jimp.pathfinder;

public enum InfoLabelSource {
    GENERATE(370),
    LOAD(530),
    SAVE(685),
    SPLIT(905);

    private int layoutY;

    InfoLabelSource(int layoutY) {
        this.layoutY = layoutY;
    }

    public int getLayoutY() {
        return layoutY;
    }
}
