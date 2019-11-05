package test;

import java.util.List;

public class Info {
    private List<Flower> small;
    private List<Flower> big;
    private double infoAd;
    private int index;

    public Info(List<Flower> small, List<Flower> big, double infoAd, int index) {
        this.small = small;
        this.big = big;
        this.infoAd = infoAd;
        this.index = index;
    }

    public List<Flower> getSmall() {
        return small;
    }

    public void setSmall(List<Flower> small) {
        this.small = small;
    }

    public List<Flower> getBig() {
        return big;
    }

    public void setBig(List<Flower> big) {
        this.big = big;
    }

    public double getInfoAd() {
        return infoAd;
    }

    public void setInfoAd(double infoAd) {
        this.infoAd = infoAd;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
