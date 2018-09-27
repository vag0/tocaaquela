package com.vagner.tocaaquela.model;

public class MusicCount {

    private int mTotalMusic;
    private int mTotalChosen;
    private int mTotalUnchosen;

    public MusicCount(int mTotalMusic, int mTotalChosen, int mTotalUnchosen) {
        this.mTotalMusic = mTotalMusic;
        this.mTotalChosen = mTotalChosen;
        this.mTotalUnchosen = mTotalUnchosen;
    }

    public int getTotalMusic() {
        return mTotalMusic;
    }

    public void setTotalMusic(int mTotalMusic) {
        this.mTotalMusic = mTotalMusic;
    }

    public int getTotalChosen() {
        return mTotalChosen;
    }

    public void setTotalChosen(int mTotalChosen) {
        this.mTotalChosen = mTotalChosen;
    }

    public int getTotalUnchosen() {
        return mTotalUnchosen;
    }

    public void setTotalUnchosen(int mTotalUnchosen) {
        this.mTotalUnchosen = mTotalUnchosen;
    }
}