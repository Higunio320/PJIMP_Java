//package pl.jimp.pathfinder;


public class InfoLabel extends Label {

    private String text;
    private boolean isError;

    public InfoLabel(String text, InfoLabelSource infoLabelSource, boolean isError) {
        super(text);

        setLayoutX(75);
        setLayoutY(infoLabelSource.getLayoutY());
        this.isError = isError;
    }

    public InfoLabel(String text, int x, int y, boolean isError){
        super(text);

        setLayoutX(x);
        setLayoutY(y);
    }

    public void showInfoLabel(){

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(3), this);

        fadeInTransition.setFromValue(1);
        fadeInTransition.setToValue(0);

        fadeInTransition.play();
    }

    public boolean isError() {
        return isError;
    }
}
